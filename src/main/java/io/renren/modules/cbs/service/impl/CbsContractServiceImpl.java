package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.ExcelUtil.ExcelSheet;
import io.renren.common.ExcelUtil.ExcelUtil;
import io.renren.common.ExcelUtil.Model;
import io.renren.common.enumeration.ContractEnum;
import io.renren.common.enumeration.DocumentControlEnum;
import io.renren.common.enumeration.MoneyEnum;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.bind.dao.BindCbsContractCtbProcessingTradeDao;
import io.renren.modules.bind.entity.BindCbsContractCtbProcessingTradeEntity;
import io.renren.modules.cbs.dao.*;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.cbs.service.CbsContractService;
import io.renren.modules.cbs.vo.ContractVo;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import io.renren.modules.cst.entity.CstNptsEmlHeaderEntity;
import io.renren.modules.cst.service.CstNptsEmlHeaderService;
import io.renren.modules.ctb.dao.CtbOrderProcessingTradeDao;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeEntity;
import io.renren.modules.ctb.vo.PriceItemVo;
import io.renren.modules.oss.cloud.OSSFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service("cbsContractService")
public class CbsContractServiceImpl extends ServiceImpl<CbsContractDao, CbsContractEntity> implements CbsContractService {

    private final String RE_LINE = "^^^";

    private CbsContractDao cbsContractDao;
    private CbsContractMemberDao memberDao;
    private CbsContractGoodsDao contractGoodsDao;
    private CbsGoodsPartNoDao goodsPartNoDao;
    private CbsContractStatusStreamDao cbsContractStatusStreamDao;
    private CbsImgContractDao imgContractDao;
    private CbsMoneyInExpectedDao cbsMoneyInExpectedDao;
    private CbsMoneyOutExpectedDao cbsMoneyOutExpectedDao;

    private CstNptsEmlHeaderService emlHeaderService;
    private CommonFunction commonFunction;

    private CbsImportDao cbsImportDao;
    private CbsExportDao cbsExportDao;
    private CbsProduceDao cbsProduceDao;
    private CbsStoreGoodsDao storeGoodsDao;
    private CbsStoreGoodsInDao storeGoodsInDao;
    private CbsStoreGoodsOutDao storeGoodsOutDao;
    private CbsMoneyInDao moneyInDao;
    private BindCbsContractCtbProcessingTradeDao bindCbsContractCtbProcessingTradeDao;
    private CtbOrderProcessingTradeDao ctbOrderProcessingTradeDao;


    public CbsContractServiceImpl(
            CbsContractDao cbsContractDao,
            CbsContractMemberDao memberDao,
            CbsContractGoodsDao contractGoodsDao,
            CbsGoodsPartNoDao goodsPartNoDao,
            CbsContractStatusStreamDao cbsContractStatusStreamDao,
            CbsImgContractDao imgContractDao,
            CbsMoneyInExpectedDao cbsMoneyInExpectedDao,
            CbsMoneyOutExpectedDao cbsMoneyOutExpectedDao,
            CstNptsEmlHeaderService emlHeaderService,
            CommonFunction commonFunction,
            CbsImportDao cbsImportDao,
            CbsExportDao cbsExportDao,
            CbsProduceDao cbsProduceDao,
            CbsStoreGoodsDao storeGoodsDao,
            CbsStoreGoodsInDao storeGoodsInDao,
            CbsStoreGoodsOutDao storeGoodsOutDao,
            CbsMoneyInDao moneyInDao,
            BindCbsContractCtbProcessingTradeDao bindCbsContractCtbProcessingTradeDao,
            CtbOrderProcessingTradeDao ctbOrderProcessingTradeDao) {
        this.cbsContractDao = cbsContractDao;
        this.memberDao = memberDao;
        this.contractGoodsDao = contractGoodsDao;
        this.goodsPartNoDao = goodsPartNoDao;
        this.cbsContractStatusStreamDao = cbsContractStatusStreamDao;
        this.imgContractDao = imgContractDao;
        this.cbsMoneyInExpectedDao = cbsMoneyInExpectedDao;
        this.cbsMoneyOutExpectedDao = cbsMoneyOutExpectedDao;
        this.emlHeaderService = emlHeaderService;
        this.commonFunction = commonFunction;
        this.cbsImportDao = cbsImportDao;
        this.cbsExportDao = cbsExportDao;
        this.cbsProduceDao = cbsProduceDao;
        this.storeGoodsDao = storeGoodsDao;
        this.storeGoodsInDao = storeGoodsInDao;
        this.storeGoodsOutDao = storeGoodsOutDao;
        this.moneyInDao = moneyInDao;
        this.bindCbsContractCtbProcessingTradeDao = bindCbsContractCtbProcessingTradeDao;
        this.ctbOrderProcessingTradeDao = ctbOrderProcessingTradeDao;
    }

    @Override
    public PageUtils queryIndex(CbsContractEntity entity) {
        IPage<CbsContractEntity> page = cbsContractDao.queryIndex(new QueryPage<CbsContractEntity>().getPage(entity), entity);
        page.setRecords(returnOfSubcontract(page));
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public Long saveReturnId(CbsContractEntity entity) {
        Long id = saveContract(entity, ContractEnum.CONTRACT_STATUS_1.getCode());
        // 根据不同type创建子合同并存入合作伙伴
        for (CbsContractEntity child : entity.getChildren()) {
            child.setParentId(id);
            saveContract(child, ContractEnum.CONTRACT_STATUS_LOSE_1.getCode());
        }
        return id;
    }

    private Long saveContract(CbsContractEntity entity, Integer status) {
        entity.setStatus(status);
        cbsContractDao.insert(entity);
        Long id = entity.getId();
        // 存入相关合作伙伴信息
        for (CbsContractMemberEntity memberEntity : entity.getMemberEntityList()) {
            memberEntity.setFkContractId(id);
            memberDao.insert(memberEntity);
        }
        // 为合同创建第一个状态流
        CbsContractStatusStreamEntity statusStreamEntity = new CbsContractStatusStreamEntity();
        statusStreamEntity.setFkContractId(id);
        statusStreamEntity.setRemark("创建");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(status);
        statusStreamEntity.setOperator(entity.getOperator());
        cbsContractStatusStreamDao.insert(statusStreamEntity);
        return id;
    }

    @Override
    public CbsContractEntity detail(Long id) {
        return cbsContractDao.detail(id);
    }


    /**
     * @Description: 处理附件
     * @Author: HuangHe
     * @Date: 14:59 2019/10/17
     */
    @Transactional
    public Integer updateAllByIdPublic(CbsContractEntity entity) {
        cbsContractDao.updateById(entity);
        // 更新合同附件
        if (entity.getImgContractList() != null) {
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_CONTRACT.getCode())
                    .contractId(entity.getId())
                    .field("fk_contract_id")
                    .fieldValue(entity.getId())
                    .imgList(entity.getImgContractList())
                    .build());
        }
        // 删除料号
        List<CbsContractGoodsEntity> contractGoodsEntityList = contractGoodsDao.selectList(new QueryWrapper<CbsContractGoodsEntity>().eq("fk_contract_id", entity.getId()));
        if (contractGoodsEntityList != null) {
            for (CbsContractGoodsEntity contractGoodsEntity : contractGoodsEntityList) {
                goodsPartNoDao.delete(new UpdateWrapper<CbsGoodsPartNoEntity>().eq("goods_part_no", contractGoodsEntity.getId()));
            }
        }
        // 删除合同商品条目
        contractGoodsDao.delete(new QueryWrapper<CbsContractGoodsEntity>().eq("fk_contract_id", entity.getId()));
        // 重新插入合同商品、料号实体
        if (entity.getContractGoodsList() != null && !entity.getContractGoodsList().isEmpty()) {
            BigDecimal contractMoney = BigDecimal.ZERO;
            int inGoodsIndex = 0, outGoodsIndex = 0;
            for (CbsContractGoodsEntity contractGoodsEntity : entity.getContractGoodsList()) {
                contractGoodsEntity.setIndexInContract(contractGoodsEntity.getType() == 1 ? ++inGoodsIndex : ++outGoodsIndex);
                contractGoodsEntity.setFkContractId(entity.getId());
                contractGoodsDao.insert(contractGoodsEntity);
                contractGoodsEntity.getPartNoEntity().setGoodsPartNo(contractGoodsEntity.getId());
                goodsPartNoDao.insert(contractGoodsEntity.getPartNoEntity());
                // 计算价格
                BigDecimal unitPrice = contractGoodsEntity.getPartNoEntity().getUnitPrice();
                BigDecimal contractCount = contractGoodsEntity.getPartNoEntity().getContractCount();
                contractMoney = contractMoney.add(unitPrice.multiply(contractCount));
            }
            // 更新合同价格
            entity.setMoney(contractMoney);
            cbsContractDao.updateById(entity);
        } else {
            // 没有商品，且如果不是加工合同，需要额外计算金额，加工合同由子合同决定金额
            if (entity.getType() != ContractEnum.CONTRACT_TYPE_1.getCode()
                    && entity.getType() != ContractEnum.CONTRACT_TYPE_2.getCode()) {
                entity.setMoney(BigDecimal.ZERO);
                cbsContractDao.updateById(entity);
            }
        }
        // 更新
        // 如果是子合同，重新计算父合同价格
        if (entity.getType() == ContractEnum.CONTRACT_TYPE_11.getCode()
                || entity.getType() == ContractEnum.CONTRACT_TYPE_12.getCode()
                || entity.getType() == ContractEnum.CONTRACT_TYPE_13.getCode()) {
            CbsContractEntity parentEntity = cbsContractDao.detail(entity.getParentId());
            BigDecimal parentContractMoney = BigDecimal.ZERO;
            for (CbsContractEntity childEntity : parentEntity.getChildren()) {
                if (childEntity.getType() == ContractEnum.CONTRACT_TYPE_12.getCode()) {
                    parentContractMoney = parentContractMoney.add(childEntity.getMoney());
                } else {
                    parentContractMoney = parentContractMoney.subtract(childEntity.getMoney());
                }
            }
            parentEntity.setMoney(parentContractMoney);
            cbsContractDao.updateById(parentEntity);
        }
        // 海关备案（无用）
        if (entity.getImgContractProcessingRecordList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgContractProcessingRecordEntity imgContractProcessingRecordEntity : entity.getImgContractProcessingRecordList()) {
                imgList.add(imgContractProcessingRecordEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_CONTRACT_PROCESSING_RECORD.getCode())
                    .contractId(entity.getId())
                    .field("fk_contract_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        return 1;
    }

    @Override
    @Transactional
    public Integer updateAllById(CbsContractEntity entity) {
        return updateAllByIdPublic(entity);
    }

    @Override
    @Transactional
    public void updateEmlCode(CbsContractEntity entity) {
        update(new UpdateWrapper<CbsContractEntity>()
                .eq("id", entity.getId())
                .set("seq_no", entity.getSeqNo())
                .set("eml_no", entity.getEmlNo()));

        CstNptsEmlHeaderEntity emlHeaderEntity = emlHeaderService.selectByContractId(entity.getId());
        if (emlHeaderEntity != null) {
            emlHeaderEntity.setSeqNo(entity.getSeqNo());
            emlHeaderEntity.setEmlNo(entity.getEmlNo());
            emlHeaderService.updateById(emlHeaderEntity);
        }
    }

    /**
     * @Description: 删除
     * 可以提审的状态：准备中，不通过
     * @Author: ChenNing
     * @Date: 11:12 2019/10/17
     */
    @Override
    public Integer deleteOne(CbsContractEntity entity) {
        CbsContractEntity c = cbsContractDao.selectById(entity.getId());
        if (c.getStatus() != ContractEnum.CONTRACT_STATUS_2.getCode()) {
            c.setStatus(entity.getStatus());
            cbsContractDao.updateById(c);
            return 1;
        }
        return 0;
    }

    /**
     * @Description: 提审
     * 可以提审的状态：1、编辑中，3、不通过
     */
    @Override
    @Transactional
    public void submit(CbsContractStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsContractEntity entity = cbsContractDao.detail(statusStreamEntity.getFkContractId());
        String errMsg = checkContractStart(entity);
        if (errMsg != null) {
            throw new RuntimeException(errMsg);
        }
        CbsContractStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkContractId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == ContractEnum.CONTRACT_STATUS_1.getCode() || lastStatusStreamEntity.getStatus() == ContractEnum.CONTRACT_STATUS_3.getCode()) {
                entity.setStatus(ContractEnum.CONTRACT_STATUS_2.getCode());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(ContractEnum.CONTRACT_STATUS_2.getCode());

                cbsContractDao.updateById(entity);
                cbsContractStatusStreamDao.insert(statusStreamEntity);
                // 同时变更子合同状态
                updateChildStatusWhenParentChange(entity, statusStreamEntity);
            } else {
                throw new RuntimeException("合同状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个合同,请联系管理员");
        }
    }

    /**
     * @Description: 撤审
     * 可以撤审的状态：2、已提审
     */
    @Override
    @Transactional
    public Integer submitBack(CbsContractStatusStreamEntity statusStreamEntity) {
        CbsContractEntity entity = cbsContractDao.selectById(statusStreamEntity.getFkContractId());
        CbsContractStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkContractId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == ContractEnum.CONTRACT_STATUS_2.getCode()) {
                try {
                    entity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                    cbsContractDao.updateById(entity);
                    cbsContractStatusStreamDao.insert(statusStreamEntity);
                    // 同时变更子合同状态
                    updateChildStatusWhenParentChange(entity, statusStreamEntity);
                    return 1;
                } catch (Exception e) {
                    return -2;
                }
            } else {
                return -1;
            }
        } else {
            return -3;
        }
    }

    /**
     * @Description: 审核
     * 可以审核的状态：2、提审
     */
    @Override
    @Transactional
    public void check(CbsContractStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsContractEntity entity = cbsContractDao.detail(statusStreamEntity.getFkContractId());
        CbsContractStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkContractId());
        if (lastStatusStreamEntity != null) {
            if (ContractEnum.CONTRACT_STATUS_2.getCode() == entity.getStatus()) {
                try {
                    entity.setStatus(statusStreamEntity.getStatus());
                    statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                    cbsContractDao.updateById(entity);
                    cbsContractStatusStreamDao.insert(statusStreamEntity);
                    // 同时变更子合同状态
                    updateChildStatusWhenParentChange(entity, statusStreamEntity);
                    // 如果最终审核结果通过，插入预收支流水
                    if (ContractEnum.CONTRACT_STATUS_4.getCode() == statusStreamEntity.getStatus()) {
                        insertExpectedIO(entity, statusStreamEntity.getOperator());
                        // 查询 bind_cbs_contract_ctb_processing_trade 如果报关行绑定了更新 ctb_order_processing_trade status 5
                        BindCbsContractCtbProcessingTradeEntity bindCbsContractCtbProcessingTradeEntity = entity.getBindEntity();
                        // 如果是已绑定的合同，更新Ctb对应加贸单状态5
                        if (bindCbsContractCtbProcessingTradeEntity != null) {
                            CtbOrderProcessingTradeEntity ctbOrderProcessingTradeEntity = bindCbsContractCtbProcessingTradeEntity.getCtbOrderProcessingTradeEntity();
                            ctbOrderProcessingTradeEntity.setStatus(5);
                            ctbOrderProcessingTradeDao.updateByIdWithoutTenant(ctbOrderProcessingTradeEntity);
                            // 确定后priceList到moneyIn和moneyOut
                            processCtbPriceItemList(ctbOrderProcessingTradeEntity);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("未知异常，请联系管理员");
                }
            } else {
                throw new RuntimeException("合同状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个合同,请联系管理员");
        }
    }

    /**
     * @Description: 完成提审
     * 可以提审的状态：4、已审核，12、完成退审
     */
    @Override
    @Transactional
    public HashMap<String, Object> submitComplete(CbsContractStatusStreamEntity statusStreamEntity) throws RuntimeException {
        HashMap<String, Object> resultMap = new HashMap<>();
        CbsContractEntity entity = cbsContractDao.detail(statusStreamEntity.getFkContractId());
        resultMap = checkContractCompleteError(entity);
        if (resultMap.containsKey("code")) {
            return resultMap;
        } else if (statusStreamEntity.getForceToChange() != null && !statusStreamEntity.getForceToChange()) {
            resultMap = checkContractCompleteWarning(entity);
            if (resultMap.containsKey("code")) {
                return resultMap;
            }
        }
        CbsContractStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkContractId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == ContractEnum.CONTRACT_STATUS_4.getCode() || lastStatusStreamEntity.getStatus() == ContractEnum.CONTRACT_STATUS_12.getCode()) {
                entity.setStatus(ContractEnum.CONTRACT_STATUS_11.getCode());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(ContractEnum.CONTRACT_STATUS_11.getCode());
                if (StringUtils.isEmpty(statusStreamEntity.getRemark())) {
                    statusStreamEntity.setRemark("完成提审");
                }
                cbsContractDao.updateById(entity);
                cbsContractStatusStreamDao.insert(statusStreamEntity);
                // 同时变更子合同状态
                updateChildStatusWhenParentChange(entity, statusStreamEntity);
                resultMap.put("code", 200);
                return resultMap;
            } else {
                resultMap.put("code", 500);
                resultMap.put("msg", "合同状态不合法，已开始或完成退审状态才能提交审核");
                return resultMap;
            }
        } else {
            resultMap.put("code", 500);
            resultMap.put("msg", "没有找到这个合同,请联系管理员");
            return resultMap;
        }
    }

    /**
     * @Description: 完成撤审
     * 可以完成撤审的状态：11、完成提审
     */
    @Override
    @Transactional
    public Integer submitCompleteBack(CbsContractStatusStreamEntity statusStreamEntity) {
        CbsContractEntity entity = cbsContractDao.detail(statusStreamEntity.getFkContractId());
        CbsContractStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkContractId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == ContractEnum.CONTRACT_STATUS_11.getCode()) {
                try {
                    entity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                    cbsContractDao.updateById(entity);
                    cbsContractStatusStreamDao.insert(statusStreamEntity);
                    // 同时变更子合同状态
                    updateChildStatusWhenParentChange(entity, statusStreamEntity);
                    return 1;
                } catch (Exception e) {
                    return -2;
                }
            } else {
                return -1;
            }
        } else {
            return -3;
        }
    }

    /**
     * @Description: 完成审核
     * 可以完成审核的状态：11、完成提审
     */
    @Override
    @Transactional
    public HashMap<String, Object> checkComplete(CbsContractStatusStreamEntity statusStreamEntity) throws RuntimeException {
        HashMap<String, Object> resultMap = new HashMap<>();
        CbsContractEntity entity = cbsContractDao.detail(statusStreamEntity.getFkContractId());
        CbsContractStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkContractId());
        if (lastStatusStreamEntity != null) {
            if (ContractEnum.CONTRACT_STATUS_11.getCode() == entity.getStatus()) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                cbsContractDao.updateById(entity);
                cbsContractStatusStreamDao.insert(statusStreamEntity);
                // 同时变更子合同状态
                updateChildStatusWhenParentChange(entity, statusStreamEntity);
                // 如果最终审核结果通过，检查合同物料平衡，错误返回错误信息，二次确定
                if (ContractEnum.CONTRACT_STATUS_13.getCode() == statusStreamEntity.getStatus()) {
                    resultMap = checkContractCompleteError(entity);
                    if (resultMap.containsKey("code")) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return resultMap;
                    } else if (statusStreamEntity.getForceToChange() != null && !statusStreamEntity.getForceToChange()) {
                        resultMap = checkContractCompleteWarning(entity);
                        if (resultMap.containsKey("code")) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return resultMap;
                        }
                    }
                    changeGoodsPartNoWhenContractComplete(entity);
                }
                resultMap.put("code", 200);
                return resultMap;
            } else {
                resultMap.put("code", 500);
                resultMap.put("msg", "合同状态不合法，请先提交审核");
                return resultMap;
            }
        } else {
            resultMap.put("code", 500);
            resultMap.put("msg", "没有找到这个合同,请联系管理员");
            return resultMap;
        }
    }

    /**
     * @Description: 根据合同的进口出口，插入预收支流水
     * @Param:
     * @Return:
     * @Author: chenning
     * @Date: 2020/2/18
     */
    @Transactional
    public void insertExpectedIO(CbsContractEntity entity, Long operator) {
        if (ContractEnum.CONTRACT_TYPE_1.getCode() == entity.getType() || ContractEnum.CONTRACT_TYPE_2.getCode() == entity.getType()) {
            //查询子合同，根据子合同的进口出口做预计收支流水
            CbsContractEntity subcontractImport = cbsContractDao.selectOne(new QueryWrapper<CbsContractEntity>().eq("parent_id", entity.getId()).eq("type", ContractEnum.CONTRACT_TYPE_11.getCode()));
            cbsMoneyOutExpectedDao.insert(CbsMoneyOutExpectedEntity.builder()
                    .title("子合同进口单号 - " + subcontractImport.getId() + " 进口货值")
                    .fkContractId(subcontractImport.getId())
                    .currencyCode(subcontractImport.getCurrencyCode())
                    .type(MoneyEnum.MONEY_TYPE_101.getCode())
                    .operator(operator)
                    .money(subcontractImport.getMoney())
                    .build());
            CbsContractEntity subcontractExport = cbsContractDao.selectOne(new QueryWrapper<CbsContractEntity>().eq("parent_id", entity.getId()).eq("type", ContractEnum.CONTRACT_TYPE_12.getCode()));
            cbsMoneyInExpectedDao.insert(CbsMoneyInExpectedEntity.builder()
                    .title("子合同出口单号 - " + subcontractExport.getId() + " 出口货值")
                    .fkContractId(subcontractExport.getId())
                    .currencyCode(subcontractExport.getCurrencyCode())
                    .type(MoneyEnum.MONEY_TYPE_102.getCode())
                    .operator(operator)
                    .money(subcontractExport.getMoney())
                    .build());
        } else if (ContractEnum.CONTRACT_TYPE_3.getCode() == entity.getType()) {
            //做预计支出流水
            cbsMoneyOutExpectedDao.insert(CbsMoneyOutExpectedEntity.builder()
                    .title("进口单号 - " + entity.getId() + " 进口货值")
                    .fkContractId(entity.getId())
                    .currencyCode(entity.getCurrencyCode())
                    .type(MoneyEnum.MONEY_TYPE_101.getCode())
                    .operator(operator)
                    .money(entity.getMoney())
                    .build());
        } else if (ContractEnum.CONTRACT_TYPE_4.getCode() == entity.getType()) {
            //做预计收入流水
            cbsMoneyInExpectedDao.insert(CbsMoneyInExpectedEntity.builder()
                    .title("出口单号 - " + entity.getId() + " 出口货值")
                    .fkContractId(entity.getId())
                    .currencyCode(entity.getCurrencyCode())
                    .type(MoneyEnum.MONEY_TYPE_102.getCode())
                    .operator(operator)
                    .money(entity.getMoney())
                    .build());
        } else if (ContractEnum.CONTRACT_TYPE_13.getCode() == entity.getType()) {
            //做预计支出流水
            cbsMoneyOutExpectedDao.insert(CbsMoneyOutExpectedEntity.builder()
                    .title("国内采购单号 - " + entity.getId() + " 采购货值")
                    .fkContractId(entity.getId())
                    .currencyCode(entity.getCurrencyCode())
                    .type(MoneyEnum.MONEY_TYPE_101.getCode())
                    .operator(operator)
                    .money(entity.getMoney())
                    .build());
        }
    }

    /**
     * 获取上一个合同状态以便修改
     */
    private CbsContractStatusStreamEntity getLastStatusStreamEntity(Long contractId) {
        List<CbsContractStatusStreamEntity> statusStreamEntityList = cbsContractStatusStreamDao.selectList(new QueryWrapper<CbsContractStatusStreamEntity>().eq("fk_contract_id", contractId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    private void processCtbPriceItemList(CtbOrderProcessingTradeEntity entity) {
        commonFunction.processCtbPriceItemListWithoutTenant(PriceItemVo.builder()
                .tableName("ctb_order_processing_trade_price_item")
                .parameterField("fk_order_processing_trade_id")
                .parameterId(entity.getId())
                .serviceCompanyId(entity.getFkServiceCompanyId())
                .orderType(1)
                .name(entity.getTitle())
                .operator(-1L)
                .build(), entity.getCtbTenantId());
    }

    /**
     * 修改加工合同状态同时修改其进口和出口子合同状态
     */
    private void updateChildStatusWhenParentChange(CbsContractEntity parentContract, CbsContractStatusStreamEntity parentStatusStream) {
        List<CbsContractEntity> children = parentContract.getChildren();
        if (children != null && children.size() > 0) {
            for (CbsContractEntity child : children) {
                if (child.getType() == ContractEnum.CONTRACT_TYPE_11.getCode() || child.getType() == ContractEnum.CONTRACT_TYPE_12.getCode()) {
                    CbsContractStatusStreamEntity childStatusStream = new CbsContractStatusStreamEntity();
                    childStatusStream.setFkContractId(child.getId());
                    childStatusStream.setOperator(parentStatusStream.getOperator());
                    childStatusStream.setStatus(parentStatusStream.getStatus());
                    childStatusStream.setCreateTime(new Date());
                    childStatusStream.setRemark("父合同状态变更");
                    CbsContractStatusStreamEntity lastStatusStream = getLastStatusStreamEntity(child.getId());
                    if (lastStatusStream != null) {
                        childStatusStream.setLastStatus(lastStatusStream.getStatus());
                    }
                    cbsContractStatusStreamDao.insert(childStatusStream);
                    // 修改子合同状态
                    child.setStatus(parentStatusStream.getStatus());
                    cbsContractDao.updateById(child);
                }
            }
        }
    }

    /**
     * 当合同结案审核通过，检查料号剩余，新建解绑料号，用于再出口或销售
     */
    private void changeGoodsPartNoWhenContractComplete(CbsContractEntity parentContract) {
        List<CbsContractEntity> children = parentContract.getChildren();
        if (children != null && children.size() > 0) {
            for (CbsContractEntity child : children) {
                for (CbsContractGoodsEntity contractGoodsEntity : child.getContractGoodsList()) {
                    CbsGoodsPartNoEntity oldGoodsPartNoEntity = contractGoodsEntity.getPartNoEntity();
                    if (oldGoodsPartNoEntity.getStoreCount().compareTo(BigDecimal.ZERO) <= 0) {
                        continue;
                    }
                    // 生成新料号，库存量为原料号库存
                    CbsGoodsPartNoEntity newGoodsPartNoEntity = new CbsGoodsPartNoEntity();
                    newGoodsPartNoEntity.setGoodsPartNo(0 - contractGoodsEntity.getId());
                    newGoodsPartNoEntity.setStoreCount(oldGoodsPartNoEntity.getStoreCount());
                    newGoodsPartNoEntity.setUnitCode(oldGoodsPartNoEntity.getUnitCode());
                    newGoodsPartNoEntity.setFkGoodsId(oldGoodsPartNoEntity.getFkGoodsId());
                    goodsPartNoDao.insert(newGoodsPartNoEntity);
                    // 更新原料号，库存量0
                    oldGoodsPartNoEntity.setStoreCount(BigDecimal.ZERO);
                    goodsPartNoDao.updateById(oldGoodsPartNoEntity);

                    List<CbsStoreGoodsEntity> oldStoreGoodsEntityList = storeGoodsDao.listByGoodsPartNo(contractGoodsEntity.getId());
                    for (CbsStoreGoodsEntity oldStoreGoodsEntity : oldStoreGoodsEntityList) {
                        if (oldStoreGoodsEntity.getGoodsStoreCount().compareTo(BigDecimal.ZERO) <= 0) {
                            continue;
                        }
                        // 创建新库存，库存量为原料号库存
                        CbsStoreGoodsEntity newStoreGoodsEntity = new CbsStoreGoodsEntity();
                        newStoreGoodsEntity.setGoodsPartNo(newGoodsPartNoEntity.getGoodsPartNo());
                        newStoreGoodsEntity.setGoodsStoreCount(oldStoreGoodsEntity.getGoodsStoreCount());
                        newStoreGoodsEntity.setFkStoreId(oldStoreGoodsEntity.getFkStoreId());
                        newStoreGoodsEntity.setUpdateTime(new Date());
                        storeGoodsDao.insert(newStoreGoodsEntity);
                        // 原库存量清零
                        oldStoreGoodsEntity.setGoodsStoreCount(BigDecimal.ZERO);
                        storeGoodsDao.updateById(oldStoreGoodsEntity);
                    }
                }
            }
        }
    }

    @Override
    public PageUtils listForType(CbsContractEntity entity) {
        QueryWrapper<CbsContractEntity> qw = new QueryWrapper<>();
        if (entity.getTypeList() != null) {
            qw.in("type", entity.getTypeList());
        }
        IPage<CbsContractEntity> page = this.page(new QueryPage<CbsContractEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    @Override
    public List<CbsContractEntity> listForCreateImport() {
        return cbsContractDao.listForCreateImport();
    }

    @Override
    public List<CbsContractEntity> listForCreateExport() {
        return cbsContractDao.listForCreateExport();
    }

    @Override
    public List<CbsContractEntity> listForCreatePurchase() {
        return cbsContractDao.listForCreatePurchase();
    }

    @Override
    public List<CbsContractEntity> listForCreateSell() {
        return cbsContractDao.listForCreateSell();
    }

    @Override
    public List<CbsContractEntity> listForCreateProduce() {
        return cbsContractDao.listForCreateProduce();
    }

    /**
     * @description 进口报关单号查询合同
     * @author chenning
     * @date 2020/2/22 10:20
     */
    @Override
    public PageUtils selectContractByImportBillCode(CbsContractEntity entity) {
        IPage<CbsContractEntity> page = cbsContractDao.selectContractByImportBillCode(new QueryPage<CbsContractEntity>().getPage(entity), entity);
        page.setRecords(returnOfSubcontract(page));
        return new PageUtils(page);
    }

    /**
     * @description 出口报关单号查询合同
     * @author chenning
     * @date 2020/2/22 10:20
     */
    @Override
    public PageUtils selectContractByExportBillCode(CbsContractEntity entity) {
        IPage<CbsContractEntity> page = cbsContractDao.selectContractByExportBillCode(new QueryPage<CbsContractEntity>().getPage(entity), entity);
        page.setRecords(returnOfSubcontract(page));
        return new PageUtils(page);
    }

    /**
     * @description 商品码查询合同
     * @author chenning
     * @date 2020/2/22 10:20
     */
    @Override
    public PageUtils selectContractByGoodsCode(CbsContractEntity entity) {
        IPage<CbsContractEntity> page = cbsContractDao.selectContractByGoodsCode(new QueryPage<CbsContractEntity>().getPage(entity), entity);
        page.setRecords(returnOfSubcontract(page));
        return new PageUtils(page);
    }

    /**
     * @description HS码查询合同
     * @author chenning
     * @date 2020/2/22 10:20
     */
    @Override
    public PageUtils selectContractByHsCode(CbsContractEntity entity) {
        IPage<CbsContractEntity> page = cbsContractDao.selectContractByHsCode(new QueryPage<CbsContractEntity>().getPage(entity), entity);
        page.setRecords(returnOfSubcontract(page));
        return new PageUtils(page);
    }

    /**
     * @description 子合同
     * @author chenning
     * @date 2020/3/11 18:17
     */
    @Override
    public PageUtils subcontractList(CbsContractEntity entity) {
        IPage<CbsContractEntity> page = cbsContractDao.queryIndex(new QueryPage<CbsContractEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public HashMap<String, Object> deleteByIds(Long[] ids) {
        HashMap<String, Object> resultMap = new HashMap<>();
        for (Long id : ids) {
            resultMap = deleteById(id);
            if (resultMap.containsKey("code")) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultMap;
            }
        }
        resultMap.put("code", 200);
        return resultMap;
    }

    private HashMap<String, Object> deleteById(Long id) {
        HashMap<String, Object> resultMap = new HashMap<>();

        CbsContractEntity contractEntity = cbsContractDao.detail(id);
        String errorMsg = null;
        for (CbsContractEntity child : contractEntity.getChildren()) {
            if (child.getType() == 13) {
                if (child.getStatus() == 2 || child.getStatus() >= 4) {
                    errorMsg = !StringUtils.isEmpty(errorMsg) ? errorMsg : "";
                    errorMsg += "【" + child.getType() + "】当前状态为【" + Objects.requireNonNull(ContractEnum.findByCode(child.getStatus())).getMsg() + "】,";
                }
            }
        }
        if (!StringUtils.isEmpty(errorMsg)) {
            errorMsg = "进料加工合同【" + contractEntity.getTitle() + "】" +
                    "当前状态为【" + Objects.requireNonNull(ContractEnum.findByCode(contractEntity.getStatus())).getMsg() + "】，" +
                    "其国内采购子合同" + errorMsg +
                    "不符合删除条件，请继续完成当前进料加工合同及其子合同。";
            resultMap.put("code", 400);
            resultMap.put("msg", errorMsg);
            return resultMap;
        }

        // 迭代删除子合同
        for (CbsContractEntity child : contractEntity.getChildren()) {
            resultMap = deleteById(child.getId());
            if (resultMap.containsKey("code")) {
                return resultMap;
            }
        }
        // 删除合同下商品和料号
        for (CbsContractGoodsEntity contractGoodsEntity : contractEntity.getContractGoodsList()) {
            goodsPartNoDao.deleteById(contractGoodsEntity.getPartNoEntity().getId());
            contractGoodsDao.deleteById(contractGoodsEntity.getId());
        }
        // 删除合同下附件
        for (CbsImgContractEntity imgContractEntity : contractEntity.getImgContractEntityList()) {
            imgContractDao.deleteById(imgContractEntity.getId());
        }
        // 删除合同
        cbsContractDao.deleteById(id);
        return resultMap;
    }

    /**
     * @description 含有子合同的返回
     * @author chenning
     * @date 2020/3/13 16:07
     */
    public List<CbsContractEntity> returnOfSubcontract(IPage<CbsContractEntity> page) {
        List<CbsContractEntity> list = page.getRecords().stream().map(item -> {
            if (item.getParentId() == null) {
                List<Object> ids = cbsContractDao.selectObjs(new QueryWrapper<CbsContractEntity>().eq("parent_id", item.getId()));
                item.setSubcontractIds(ids);
                if (ids.size() > 0) {
                    item.setHasSubcontract(1);
                } else {
                    item.setHasSubcontract(0);
                }
                return item;
            } else {
                // 如果是子合同--去查询它的父合同
                CbsContractEntity cbsContractEntity = cbsContractDao.simpleDetail(item.getParentId());
                if (cbsContractEntity != null) {
                    List<Object> ids = cbsContractDao.selectObjs(new QueryWrapper<CbsContractEntity>().eq("parent_id", cbsContractEntity.getId()));
                    cbsContractEntity.setHasSubcontract(1);
                    cbsContractEntity.setSubcontractIds(ids);
                    return cbsContractEntity;
                }
                return CbsContractEntity.builder().build();
            }
        }).collect(Collectors.toList());
        return list;
    }


    /**
     * 判断合同完整性
     */
    private String checkContractStart(CbsContractEntity entity) {
        switch (entity.getType()) {
            case 1:
            case 2:
                // 首先判断子合同是否完整
                for (CbsContractEntity child : entity.getChildren()) {
                    if (child.getType() == 11 || child.getType() == 12) {
                        String errMsg = checkContractStart(child);
                        if (errMsg != null) return errMsg;
                    }
                }
                break;
            case 3:
            case 4:
            case 11:
            case 12:
            case 13:
                if (entity.getContractGoodsList().size() <= 0) {
                    return "【" + entity.getTitle() + "】必须包含合同商品";
                } else {
                    for (CbsContractGoodsEntity contractGoods : entity.getContractGoodsList()) {
                        if (contractGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || contractGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                            return "【" + entity.getTitle() + "】中合同商品数量和价格必须大于0";
                        }
                    }
                }
                break;
        }
        switch (entity.getType()) {
            case 1:
                if (entity.getMoney().compareTo(BigDecimal.ZERO) <= 0) return "成品价值不能小于料件价值";
                break;
            case 2:
                if (entity.getMoney().compareTo(BigDecimal.ZERO) <= 0) return "加工费不能小于0";
                break;
            case 20:
                if (entity.getMoney().compareTo(BigDecimal.ZERO) <= 0) return "国内委托加工费不能小于0";
                break;
        }
        return null;
    }

    /**
     * 判断合同结案完整性，必须达成的部分
     */
    private HashMap<String, Object> checkContractCompleteError(CbsContractEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        // 有进口单已信息审核（状态>=4）未完关（状态<14）
        // 有出口单已信息审核（状态>=4）未完关（状态<14）
        // 有国内收货单已审核未入库（状态4）
        // 有国内发货单已审核未出库（状态4）
        // 有生产单已开始（状态>=4）未完成（状态<7）
        // 400 【有已启动未完成的进口单（出口单、国内收货单、生产单）】
        // 有进口单信息在审（状态2）
        // 有出口单信息在审（状态2）
        // 有国内收货单在审（状态2）
        // 有国内发货单在审（状态2）
        // 有生产单启动在审（状态2）
        // 400 【有待审核的进口单（出口单、国内收货单、生产单）】
        // 有进口单信息编辑中（状态1或3）
        // 有出口单信息编辑中（状态1或3）
        // 有国内收货单编辑中（状态1或3）
        // 有国内发货单编辑中（状态1或3）
        // 有生产单编辑中（状态1或3）
        // 400 【有编辑中还未启动的进口单（出口单、国内收货单、生产单），请删除后再提交完结】
        // 返回参数【toWhere：import、export、purchase、sell、produce】‘取消’ ‘去查看’：跳转对应模块并已合同编号作为查询条件进行一次查询
        switch (entity.getType()) {
            case 3:
            case 11:
                for (CbsImportEntity importEntity : entity.getImportEntityList()) {
                    resultMap.put("code", 400);
                    resultMap.put("toWhere", "import");
                    if (importEntity.getStatus() >= 4 && importEntity.getStatus() < 14) {
                        resultMap.put("msg", "该合同有已信息审核通过且未完关的进口单，请完成后再次提交合同结案。");
                        return resultMap;
                    } else if (importEntity.getStatus() == 2) {
                        resultMap.put("msg", "该合同有信息待审的进口单，请撤回信息审核或完成该进口单后再次提交合同结案。");
                        return resultMap;
                    } else if (importEntity.getStatus() == 1 || importEntity.getStatus() == 3) {
                        resultMap.put("msg", "该合同有信息編輯中的进口单，请刪除或完成该进口单后再次提交合同结案。");
                        return resultMap;
                    }
                    resultMap.clear();
                }
                break;
            case 4:
            case 12:
                for (CbsExportEntity exportEntity : entity.getExportEntityList()) {
                    resultMap.put("code", 400);
                    resultMap.put("toWhere", "export");
                    if (exportEntity.getStatus() >= 4 && exportEntity.getStatus() < 14) {
                        resultMap.put("msg", "该合同有已信息审核通过且未完关的出口单，请完成后再次提交合同结案。");
                        return resultMap;
                    } else if (exportEntity.getStatus() == 2) {
                        resultMap.put("msg", "该合同有信息待审的出口单，请撤回信息审核或完成该出口单后再次提交合同结案。");
                        return resultMap;
                    } else if (exportEntity.getStatus() == 1 || exportEntity.getStatus() == 3) {
                        resultMap.put("msg", "该合同有信息編輯中的进口单，请刪除或完成该进口单后再次提交合同结案。");
                        return resultMap;
                    }
                    resultMap.clear();
                }
                break;
            case 5:
            case 13:
                for (CbsPurchaseEntity purchaseEntity : entity.getPurchaseEntityList()) {
                    resultMap.put("code", 400);
                    resultMap.put("toWhere", "purchase");
                    if (purchaseEntity.getStatus() == 4) {
                        resultMap.put("msg", "该合同有未收货入库的国内收货单，请完成后再次提交合同结案。");
                        return resultMap;
                    } else if (purchaseEntity.getStatus() == 2) {
                        resultMap.put("msg", "该合同有待审的国内收货单，请撤回信息审核或完成该收货单后再次提交合同结案。");
                        return resultMap;
                    } else if (purchaseEntity.getStatus() == 1 || purchaseEntity.getStatus() == 3) {
                        resultMap.put("msg", "该合同有編輯中的国内收货单，请刪除或完成该收貨单后再次提交合同结案。");
                        return resultMap;
                    }
                    resultMap.clear();
                }
                break;
            case 6:
                for (CbsSellEntity sellEntity : entity.getSellEntityList()) {
                    resultMap.put("code", 400);
                    resultMap.put("toWhere", "sell");
                    if (sellEntity.getStatus() == 4) {
                        resultMap.put("msg", "该合同有未发货出库的国内收货单，请完成后再次提交合同结案。");
                        return resultMap;
                    } else if (sellEntity.getStatus() == 2) {
                        resultMap.put("msg", "该合同有待审的国内发货单，请撤回信息审核或完成该发货单后再次提交合同结案。");
                        return resultMap;
                    } else if (sellEntity.getStatus() == 1 || sellEntity.getStatus() == 3) {
                        resultMap.put("msg", "该合同有編輯中的国内发货单，请刪除或完成该发货单后再次提交合同结案。");
                        return resultMap;
                    }
                    resultMap.clear();
                }
                break;
            case 1:
            case 2:
                for (CbsProduceEntity produceEntity : entity.getProduceEntity()) {
                    resultMap.put("code", 400);
                    resultMap.put("toWhere", "produce");
                    if (produceEntity.getStatus() >= 4 && produceEntity.getStatus() < 7) {
                        resultMap.put("msg", "该合同有进行中的生产计划，请完成后再次提交合同结案。");
                        return resultMap;
                    } else if (produceEntity.getStatus() == 2) {
                        resultMap.put("msg", "该合同有待审的生产计划，请撤回审核或完成该生产计划后再次提交合同结案。");
                        return resultMap;
                    } else if (produceEntity.getStatus() == 1 || produceEntity.getStatus() == 3) {
                        resultMap.put("msg", "该合同有編輯中的生产计划，请刪除或完成该生产计划后再次提交合同结案。");
                        return resultMap;
                    }
                    resultMap.clear();
                }
                break;
        }
        // 有原料库存未使用
        // 有产品库存未出口
        // 有应进原料为进口
        // 有应出原料未出口
        // 300 【有未使用的原料库存（未出口的产品库存），如确认合同完结，该库存将解除与加工合同的绑定关系，可作为独立进出口或国内销售，请确认以上信息进出上进行操作。】 ‘取消’ ‘确定’：减对应料号库存，新建料号对应负数新料号，对应库存加
        // 300 【有应进原料（出）未进（出），请确认合同是否结案】
        switch (entity.getType()) {
            case 1:
            case 2:
                // 首先判断子合同是否完整
                for (CbsContractEntity child : entity.getChildren()) {
                    if (child.getType() == 11 || child.getType() == 12) {
                        HashMap<String, Object> childResultMap = checkContractCompleteError(child);
                        if (childResultMap.containsKey("code")) return childResultMap;
                    }
                }
                break;
        }
        return resultMap;
    }

    /**
     * 判断合同结案完整性，建议达成的部分
     */
    private HashMap<String, Object> checkContractCompleteWarning(CbsContractEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        // 有原料库存未使用
        // 有产品库存未出口
        // 有应进原料为进口
        // 有应出原料未出口
        // 300 【有未使用的原料库存（未出口的产品库存），如确认合同完结，该库存将解除与加工合同的绑定关系，可作为独立进出口或国内销售，请确认以上信息进出上进行操作。】 ‘取消’ ‘确定’：减对应料号库存，新建料号对应负数新料号，对应库存加
        String unUseRawMaterialWarningMsg = null, unExportProductWaringMsg = null;
        for (CbsContractGoodsEntity contractGoods : entity.getContractGoodsList()) {
            CbsGoodsPartNoEntity goodsPartNoEntity = contractGoods.getPartNoEntity();
            if (goodsPartNoEntity.getStoreCount().compareTo(BigDecimal.ZERO) > 0) {
                String goodsTypeStr = contractGoods.getType() == 1 ? "原料" : "产品";
                String warningMsg = goodsTypeStr + "【" + goodsPartNoEntity.getGoodsEntity().getNickname() + "】共计" +
                        goodsPartNoEntity.getStoreCount().stripTrailingZeros().toPlainString() + "/" + goodsPartNoEntity.getGoodsEntity().getUnit();
                if (contractGoods.getType() == 1) {
                    unUseRawMaterialWarningMsg = !StringUtils.isEmpty(unUseRawMaterialWarningMsg) ? unUseRawMaterialWarningMsg + "；" : "";
                    unUseRawMaterialWarningMsg += warningMsg;
                } else {
                    unExportProductWaringMsg = !StringUtils.isEmpty(unExportProductWaringMsg) ? unExportProductWaringMsg + "；" : "";
                    unExportProductWaringMsg += warningMsg;
                }
            }
        }
        // 300 【有应进原料（出）未进（出），请确认合同是否结案】
        String needButImportWarningMsg = null, needButExportWarningMsg = null;
        for (CbsContractGoodsEntity contractGoods : entity.getContractGoodsList()) {
            CbsGoodsPartNoEntity goodsPartNoEntity = contractGoods.getPartNoEntity();
            if (goodsPartNoEntity.getContractCount().compareTo(goodsPartNoEntity.getImportCount()) > 0) {
                String orderTypeStr = null;
                switch (entity.getType()) {
                    case 3:
                    case 11:
                        orderTypeStr = "需进口";
                        break;
                    case 4:
                    case 12:
                        orderTypeStr = "需出口";
                        break;
                    case 5:
                    case 13:
                        orderTypeStr = "需收货";
                        break;
                    case 6:
                        orderTypeStr = "需发货";
                        break;
                }
                String warningMsg = orderTypeStr + "【" + goodsPartNoEntity.getGoodsEntity().getNickname() + "】共计" +
                        goodsPartNoEntity.getContractCount().stripTrailingZeros().toPlainString() + "/" + goodsPartNoEntity.getGoodsEntity().getUnit() +
                        "，现已产生的" + orderTypeStr + "数量计" +
                        goodsPartNoEntity.getImportCount().stripTrailingZeros().toPlainString() + "/" + goodsPartNoEntity.getGoodsEntity().getUnit() +
                        "，还未产生的" + orderTypeStr + "数量计" +
                        goodsPartNoEntity.getContractCount().subtract(goodsPartNoEntity.getImportCount()).stripTrailingZeros().toPlainString() + "/" + goodsPartNoEntity.getGoodsEntity().getUnit();
                if (contractGoods.getType() == 1) {
                    needButImportWarningMsg = !StringUtils.isEmpty(needButImportWarningMsg) ? needButImportWarningMsg + "；" : "";
                    needButImportWarningMsg += warningMsg;
                } else {
                    needButExportWarningMsg = !StringUtils.isEmpty(needButExportWarningMsg) ? needButExportWarningMsg + "；" : "";
                    needButExportWarningMsg += warningMsg;
                }
            }
        }
        switch (entity.getType()) {
            case 1:
            case 2:
                // 首先判断子合同是否完整
                String unUseRawMaterialWarningMsgWhole = null;
                String unExportProductWaringMsgWhole = null;
                String needButImportWarningMsgWhole = null;
                String needButExportWarningMsgWhole = null;
                for (CbsContractEntity child : entity.getChildren()) {
                    HashMap<String, Object> childResultMap = checkContractCompleteWarning(child);
                    if (!StringUtils.isEmpty((String) childResultMap.get("unUseRawMaterialWarningMsg"))) {
                        unUseRawMaterialWarningMsgWhole = !StringUtils.isEmpty(unUseRawMaterialWarningMsgWhole) ? unUseRawMaterialWarningMsgWhole + "；" : "";
                        unUseRawMaterialWarningMsgWhole += childResultMap.get("unUseRawMaterialWarningMsg");
                    }
                    if (!StringUtils.isEmpty((String) childResultMap.get("unExportProductWaringMsg"))) {
                        unExportProductWaringMsgWhole = !StringUtils.isEmpty(unExportProductWaringMsgWhole) ? unExportProductWaringMsgWhole + "；" : "";
                        unExportProductWaringMsgWhole += childResultMap.get("unExportProductWaringMsg");
                    }
                    if (!StringUtils.isEmpty((String) childResultMap.get("needButImportWarningMsg"))) {
                        needButImportWarningMsgWhole = !StringUtils.isEmpty(needButImportWarningMsgWhole) ? needButImportWarningMsgWhole + "；" : "";
                        needButImportWarningMsgWhole += childResultMap.get("needButImportWarningMsg");
                    }
                    if (!StringUtils.isEmpty((String) childResultMap.get("needButExportWarningMsg"))) {
                        needButExportWarningMsgWhole = !StringUtils.isEmpty(needButExportWarningMsgWhole) ? needButExportWarningMsgWhole + "；" : "";
                        needButExportWarningMsgWhole += childResultMap.get("needButExportWarningMsg");
                    }
                }
                String warningStrWhole = null;
                if (!StringUtils.isEmpty(unUseRawMaterialWarningMsgWhole)) {
                    warningStrWhole = !StringUtils.isEmpty(warningStrWhole) ? warningStrWhole + "；" + RE_LINE + "有未使用原料结余，其中：" + RE_LINE : "该合同有有未使用原料结余，其中：" + RE_LINE;
                    warningStrWhole += unUseRawMaterialWarningMsgWhole;
                }
                if (!StringUtils.isEmpty(unExportProductWaringMsgWhole)) {
                    warningStrWhole = !StringUtils.isEmpty(warningStrWhole) ? warningStrWhole + "；" + RE_LINE + "有未出口产品结余，其中：" + RE_LINE : "有未出口产品结余，其中：" + RE_LINE;
                    warningStrWhole += unExportProductWaringMsgWhole;
                }
                if (!StringUtils.isEmpty(needButImportWarningMsgWhole)) {
                    warningStrWhole = !StringUtils.isEmpty(warningStrWhole) ? warningStrWhole + "；" + RE_LINE + "有应进未进商品，其中：" + RE_LINE : "该合同有应进未进商品，其中：" + RE_LINE;
                    warningStrWhole += needButImportWarningMsgWhole;
                }
                if (!StringUtils.isEmpty(needButExportWarningMsgWhole)) {
                    warningStrWhole = !StringUtils.isEmpty(warningStrWhole) ? warningStrWhole + "；" + RE_LINE + "有应出未出商品，其中：" + RE_LINE : "该合同有应出未出商品，其中：" + RE_LINE;
                    warningStrWhole += needButExportWarningMsgWhole;
                }
                if (!StringUtils.isEmpty(warningStrWhole)) {
                    warningStrWhole += "。" + RE_LINE + "完结审核后，所有相关原料及产品结余将与加工合同接触绑定以便于进行内销或再出口操作，请确认以上情况后进行后续操作。";
                    resultMap.put("code", 300);
                    resultMap.put("msg", warningStrWhole);
                    return resultMap;
                }
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                // String unUseRawMaterialWarningMsg = null, unExportProductWaringMsg = null;
                // String needButImportWarningMsg = null, needButExportWarningMsg = null;
                String warningStr = null;
                if (!StringUtils.isEmpty(needButImportWarningMsg)) {
                    warningStr = !StringUtils.isEmpty(warningStr) ? warningStr + "；" + RE_LINE + "有应进未进商品，其中：" : "该合同有应进未进商品，其中：";
                    warningStr += needButImportWarningMsg;
                }
                if (!StringUtils.isEmpty(needButExportWarningMsg)) {
                    warningStr = !StringUtils.isEmpty(warningStr) ? warningStr + "；" + RE_LINE + "有应出未出商品，其中：" : "该合同有应出未出商品，其中：";
                    warningStr += needButExportWarningMsg;
                }
                if (!StringUtils.isEmpty(warningStr)) {
                    warningStr += "。\n请确认以上情况后进行后续操作。";
                    resultMap.put("code", 300);
                    resultMap.put("msg", warningStr);
                    return resultMap;
                }
                break;
            case 11:
            case 12:
            case 13:
                resultMap.put("unUseRawMaterialWarningMsg", unUseRawMaterialWarningMsg);
                resultMap.put("unExportProductWaringMsg", unExportProductWaringMsg);
                resultMap.put("needButImportWarningMsg", needButImportWarningMsg);
                resultMap.put("needButExportWarningMsg", needButExportWarningMsg);
                return resultMap;

        }
        return resultMap;
    }

    /**
     * @description aeo导出excel
     * @author chenning
     * @date 2020/4/2 10:00
     */
    @Override
    public String AEOExportExcel(HttpServletResponse response, CbsContractEntity entity) {
        List<ExcelSheet<T>> list = new ArrayList<>();
        // 进口收货
        list.add(importSheet(entity));
        // 出口发货
        list.add(exportSheet(entity));
        // 生产计划
        list.add(productionPlanSheet(entity));
        // 入库信息
        list.add(storageInSheet(entity));
        // 出库信息
        list.add(storageOutSheet(entity));
        // 子合同
        list.add(subcontractSheet(entity));
        // 财务
        list.add(financeSheet(entity));
        String fileName = "excel_" + entity.getSubcontractIds().get(entity.getSubcontractIds().size() - 1) + ".xls";
        File file = new File(fileName);
        OutputStream out = null;
        FileInputStream input = null;
        try {
            out = new FileOutputStream(file);
            ExcelUtil.exportExcel(list, out);
            input = new FileInputStream(file);
            String url = OSSFactory.build().uploadSuffix(input, fileName);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.delete();
                if (out != null) {
                    out.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public void synToCtb(Long id) {
        CbsContractEntity entity = cbsContractDao.detail(id);

    }


    /**
     * @description 组装 财务 sheet
     * @author chenning
     * @date 2020/4/2 9:55
     */
    private ExcelSheet financeSheet(CbsContractEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("a", "标题");
        map.put("b", "类型");
        map.put("c", "发生金额");
        map.put("d", "币制");
        map.put("e", "人民币金额");
        map.put("f", "状态");
        map.put("g", "创建时间");
        map.put("h", "操作人");
        Collection<Object> dataset = new ArrayList<>();
        CbsMoneyInEntity cbsMoneyInEntity = CbsMoneyInEntity.builder().fkContractIds(entity.getSubcontractIds()).searchForm(ContractVo.builder().build()).build();
        IPage<CbsMoneyInEntity> page = moneyInDao.financeList(new QueryPage<CbsMoneyInEntity>().getPage(cbsMoneyInEntity), cbsMoneyInEntity);
        for (CbsMoneyInEntity item : page.getRecords()) {
            dataset.add(new Model(
                    item.getTitle(),
                    item.getIsBuildOneself() == null ? item.getTypeEntity().getName() : item.getSelfEditingType().getName(),
                    item.getMoney().stripTrailingZeros().toPlainString(),
                    item.getCurrencyEntity() == null ? null : item.getCurrencyEntity().getNameCn(),
                    ((item.getMoney().multiply(item.getCurrencyEntity().getExchangeRate())).divide(new BigDecimal("100"))).stripTrailingZeros().toPlainString(),
                    financeStatusFilter(item.getStatus().toString()),
                    item.getCreateTime() == null ? null : sdf.format(item.getCreateTime()),
                    item.getOperatorEntity() == null ? null : item.getOperatorEntity().getUsername(),
                    null
            ));
        }
        ExcelSheet sheet = new ExcelSheet();
        sheet.setSheetName("财务");
        sheet.setHeaders(map);
        sheet.setDataset(dataset);
        return sheet;
    }

    private String financeStatusFilter(String key) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "未核");
        map.put("2", "已核");
        map.put("3", "附件未核");
        map.put("4", "附件已核");
        map.put("99", "已废除");
        return map.get(key);
    }


    /**
     * @description 组装 子合同 sheet
     * @author chenning
     * @date 2020/4/2 9:55
     */
    private ExcelSheet subcontractSheet(CbsContractEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("a", "标题");
        map.put("b", "甲方");
        map.put("c", "乙方");
        map.put("d", "出口额");
        map.put("e", "结算币制");
        map.put("f", "合同状态");
        Collection<Object> dataset = new ArrayList<>();
        CbsContractEntity cbsContractEntity = CbsContractEntity.builder().parentId(entity.getParentId()).searchForm(ContractVo.builder().build()).build();
        IPage<CbsContractEntity> page = cbsContractDao.queryIndex(new QueryPage<CbsContractEntity>().getPage(cbsContractEntity), cbsContractEntity);
        for (CbsContractEntity item : page.getRecords()) {
            CbsPartnerEntity A = null;
            CbsPartnerEntity B = null;
            for (CbsContractMemberEntity cbsContractMemberEntity : item.getMemberEntityList()) {
                if (cbsContractMemberEntity.getType() == 1) {
                    A = cbsContractMemberEntity.getPartnerEntity();
                }
                if (cbsContractMemberEntity.getType() == 3) {
                    B = cbsContractMemberEntity.getPartnerEntity();
                }
            }
            dataset.add(new Model(
                    item.getTitle(),
                    A == null ? null : A.getName(),
                    B == null ? null : B.getName(),
                    item.getMoney().stripTrailingZeros().toPlainString(),
                    item.getCurrencyEntity() == null ? null : item.getCurrencyEntity().getNameCn(),
                    contractStatusFilter(item.getStatus().toString()),
                    null,
                    null,
                    null
            ));
        }
        ExcelSheet sheet = new ExcelSheet();
        sheet.setSheetName("子合同");
        sheet.setHeaders(map);
        sheet.setDataset(dataset);
        return sheet;
    }


    /**
     * @description 组装 入库信息 sheet
     * @author chenning
     * @date 2020/4/2 9:55
     */
    private ExcelSheet storageInSheet(CbsContractEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("a", "对应合同");
        map.put("b", "进口单");
        map.put("c", "生产单");
        map.put("d", "国内单");
        map.put("e", "对应类型");
        map.put("f", "入库仓库");
        map.put("g", "运输公司");
        map.put("h", "入库时间");
        map.put("i", "库单状态");
        Collection<Object> dataset = new ArrayList<>();
        CbsStoreGoodsInEntity cbsStoreGoodsInEntity = CbsStoreGoodsInEntity.builder().fkContractIds(entity.getSubcontractIds()).searchForm(ContractVo.builder().build()).build();
        IPage<CbsStoreGoodsInEntity> page = storeGoodsInDao.queryIndex(new QueryPage<CbsStoreGoodsInEntity>().getPage(cbsStoreGoodsInEntity), cbsStoreGoodsInEntity);
        for (CbsStoreGoodsInEntity item : page.getRecords()) {
            CbsContractEntity contractEntity = null;
            if (item.getImportEntity() != null) {
                contractEntity = item.getImportEntity().getContractEntity();
            }
            if (item.getProduceEntity() != null) {
                contractEntity = item.getProduceEntity().getContractEntity();
            }
            if (item.getPurchaseEntity() != null) {
                contractEntity = item.getPurchaseEntity().getContractEntity();
            }
            dataset.add(new Model(
                    contractEntity.getTitle(),
                    item.getImportEntity() == null ? null : item.getImportEntity().getId().toString(),
                    item.getProduceEntity() == null ? null : item.getProduceEntity().getId().toString(),
                    item.getPurchaseEntity() == null ? null : item.getPurchaseEntity().getId().toString(),
                    storageTypeFilter(item.getType().toString()),
                    item.getStoreEntity() == null ? null : item.getStoreEntity().getName(),
                    item.getTransitCompanyEntity() == null ? null : item.getTransitCompanyEntity().getName(),
                    item.getArrivalTime() == null ? null : sdf.format(item.getArrivalTime()),
                    storageStatusFilter(item.getStatus().toString())
            ));
        }
        ExcelSheet sheet = new ExcelSheet();
        sheet.setSheetName("入库信息");
        sheet.setHeaders(map);
        sheet.setDataset(dataset);
        return sheet;
    }

    /**
     * @description 组装 出库信息 sheet
     * @author chenning
     * @date 2020/4/2 9:55
     */
    private ExcelSheet storageOutSheet(CbsContractEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("a", "对应合同");
        map.put("b", "出口单");
        map.put("c", "生产单");
        map.put("d", "国内单");
        map.put("e", "对应类型");
        map.put("f", "出库仓库");
        map.put("g", "运输公司");
        map.put("h", "出库时间");
        map.put("i", "库单状态");
        Collection<Object> dataset = new ArrayList<>();
        CbsStoreGoodsOutEntity cbsStoreGoodsOutEntity = CbsStoreGoodsOutEntity.builder().fkContractIds(entity.getSubcontractIds()).searchForm(ContractVo.builder().build()).build();
        IPage<CbsStoreGoodsOutEntity> page = storeGoodsOutDao.queryIndex(new QueryPage<CbsStoreGoodsOutEntity>().getPage(cbsStoreGoodsOutEntity), cbsStoreGoodsOutEntity);
        for (CbsStoreGoodsOutEntity item : page.getRecords()) {
            CbsContractEntity contractEntity = null;
            if (item.getExportEntity() != null) {
                contractEntity = item.getExportEntity().getContractEntity();
            }
            if (item.getProduceEntity() != null) {
                contractEntity = item.getProduceEntity().getContractEntity();
            }
            if (item.getSellEntity() != null) {
                contractEntity = item.getSellEntity().getContractEntity();
            }
            dataset.add(new Model(
                    contractEntity.getTitle(),
                    item.getExportEntity() == null ? null : item.getExportEntity().getId().toString(),
                    item.getProduceEntity() == null ? null : item.getProduceEntity().getId().toString(),
                    item.getSellEntity() == null ? null : item.getSellEntity().getId().toString(),
                    storageTypeFilter(item.getType().toString()),
                    item.getStoreEntity() == null ? null : item.getStoreEntity().getName(),
                    item.getTransitCompanyEntity() == null ? null : item.getTransitCompanyEntity().getName(),
                    item.getArrivalTime() == null ? null : sdf.format(item.getArrivalTime()),
                    storageStatusFilter(item.getStatus().toString())
            ));
        }
        ExcelSheet sheet = new ExcelSheet();
        sheet.setSheetName("出库信息");
        sheet.setHeaders(map);
        sheet.setDataset(dataset);
        return sheet;
    }

    private String storageTypeFilter(String key) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "进口入库");
        map.put("2", "生产入库");
        map.put("5", "编辑国内采购入库");
        return map.get(key);
    }

    private String storageStatusFilter(String key) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "编辑中");
        map.put("2", "待审核");
        map.put("3", "审核不过");
        map.put("4", "审核通过");
        map.put("99", "已废除");
        return map.get(key);
    }


    /**
     * @description 组装 生产计划 sheet
     * @author chenning
     * @date 2020/4/2 9:55
     */
    private ExcelSheet productionPlanSheet(CbsContractEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("a", "从属进口单");
        map.put("b", "生产公司");
        map.put("c", "计划开始时间");
        map.put("d", "计划结束时间");
        map.put("e", "状态");
        Collection<Object> dataset = new ArrayList<>();
        CbsProduceEntity cbsProduceEntity = CbsProduceEntity.builder().fkContractIds(entity.getSubcontractIds()).searchForm(ContractVo.builder().build()).build();
        IPage<CbsProduceEntity> page = cbsProduceDao.queryIndex(new QueryPage<CbsProduceEntity>().getPage(cbsProduceEntity), cbsProduceEntity);
        for (CbsProduceEntity item : page.getRecords()) {
            dataset.add(new Model(
                    item.getContractEntity().getTitle(),
                    item.getProduceCompanyEntity().getName(),
                    item.getStartTime() == null ? null : sdf.format(item.getStartTime()),
                    item.getEndTime() == null ? null : sdf.format(item.getEndTime()),
                    produceStatusFilter(item.getStatus().toString()),
                    null,
                    null,
                    null,
                    null
            ));
        }
        ExcelSheet sheet = new ExcelSheet();
        sheet.setSheetName("生产计划");
        sheet.setHeaders(map);
        sheet.setDataset(dataset);
        return sheet;
    }

    private String produceStatusFilter(String key) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "未启动");
        map.put("2", "启动提审");
        map.put("3", "启动退审");
        map.put("4", "未启动");
        map.put("5", "进行中");
        map.put("6", "'完成提审'");
        map.put("7", "'已完成'");
        map.put("99", "已中止");
        return map.get(key);
    }

    /**
     * @description 组装进口sheet
     * @author chenning
     * @date 2020/4/2 9:55
     */
    private ExcelSheet importSheet(CbsContractEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("a", "合同编号");
        map.put("b", "供货方");
        map.put("c", "经营单位");
        map.put("d", "进口金额");
        map.put("e", "进口币制");
        map.put("f", "出发时间");
        map.put("g", "预计抵达");
        map.put("h", "实际抵达");
        map.put("i", "状态");
        Collection<Object> dataset = new ArrayList<>();
        CbsImportEntity cbsImportEntity = CbsImportEntity.builder().fkContractIds(entity.getSubcontractIds()).searchForm(ContractVo.builder().build()).build();
        IPage<CbsImportEntity> page = cbsImportDao.queryIndex(new QueryPage<CbsImportEntity>().getPage(cbsImportEntity), cbsImportEntity);
        for (CbsImportEntity item : page.getRecords()) {
            BigDecimal money = BigDecimal.ZERO;
            for (CbsImportGoodsEntity c : item.getImportGoodsList()) {
                money = money.add(c.getPartNoEntity().getUnitPrice().multiply(c.getCount()));
            }
            dataset.add(new Model(
                    item.getContractEntity().getContractCode(),
                    item.getContractEntity().getMemberEntityList().get(0).getPartnerEntity().getName(),
                    item.getContractEntity().getMemberEntityList().get(1).getPartnerEntity().getName(),
                    money.stripTrailingZeros().toPlainString(),
                    item.getContractEntity().getCurrencyEntity().getNameCn(),
                    item.getDepartTime() == null ? null : sdf.format(item.getDepartTime()),
                    item.getExpectedArrivalTime() == null ? null : sdf.format(item.getExpectedArrivalTime()),
                    item.getArrivalTime() == null ? null : sdf.format(item.getArrivalTime()),
                    contractStatusFilter(item.getStatus().toString())));
        }
        ExcelSheet sheet = new ExcelSheet();
        sheet.setSheetName("进口收货");
        sheet.setHeaders(map);
        sheet.setDataset(dataset);
        return sheet;
    }

    /**
     * @description 组装出口sheet
     * @author chenning
     * @date 2020/4/2 9:55
     */
    private ExcelSheet exportSheet(CbsContractEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("a", "合同编号");
        map.put("b", "销售方");
        map.put("c", "经营单位");
        map.put("d", "进口金额");
        map.put("e", "进口币制");
        map.put("f", "出发时间");
        map.put("g", "预计抵达");
        map.put("h", "实际抵达");
        map.put("i", "状态");
        Collection<Object> dataset = new ArrayList<>();
        CbsExportEntity CbsexportEntity = CbsExportEntity.builder().fkContractIds(entity.getSubcontractIds()).searchForm(ContractVo.builder().build()).build();
        IPage<CbsExportEntity> page = cbsExportDao.queryIndex(new QueryPage<CbsExportEntity>().getPage(CbsexportEntity), CbsexportEntity);
        for (CbsExportEntity item : page.getRecords()) {
            BigDecimal money = BigDecimal.ZERO;
            for (CbsExportGoodsEntity c : item.getExportGoodsList()) {
                money = money.add(c.getPartNoEntity().getUnitPrice().multiply(c.getCount()));
            }
            dataset.add(new Model(
                    item.getContractEntity().getContractCode(),
                    item.getContractEntity().getMemberEntityList().get(0).getPartnerEntity().getName(),
                    item.getContractEntity().getMemberEntityList().get(1).getPartnerEntity().getName(),
                    money.stripTrailingZeros().toPlainString(),
                    item.getContractEntity().getCurrencyEntity().getNameCn(),
                    item.getDepartTime() == null ? null : sdf.format(item.getDepartTime()),
                    item.getExpectedArrivalTime() == null ? null : sdf.format(item.getExpectedArrivalTime()),
                    item.getArrivalTime() == null ? null : sdf.format(item.getArrivalTime()),
                    contractStatusFilter(item.getStatus().toString())));
        }
        ExcelSheet sheet = new ExcelSheet();
        sheet.setSheetName("出口发货");
        sheet.setHeaders(map);
        sheet.setDataset(dataset);
        return sheet;
    }

    private String contractStatusFilter(String key) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "信息编辑");
        map.put("2", "信息在审");
        map.put("3", "信息退审");
        map.put("4", "关务编辑");
        map.put("11", "关务在审");
        map.put("12", "关务退审");
        map.put("13", "关务审结");
        map.put("14", "完关转结");
        map.put("99", "已废除");
        return map.get(key);
    }

}