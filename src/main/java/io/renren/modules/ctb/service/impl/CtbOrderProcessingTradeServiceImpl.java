package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.ExcelUtil.ExcelSheet;
import io.renren.common.ExcelUtil.ExcelUtil;
import io.renren.common.enumeration.ContractEnum;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.bind.dao.BindCbsContractCtbProcessingTradeDao;
import io.renren.modules.bind.entity.BindCbsContractCtbProcessingTradeEntity;
import io.renren.modules.cst.entity.CstNptsEmlHeaderEntity;
import io.renren.modules.cst.service.CstNptsEmlHeaderService;
import io.renren.modules.ctb.dao.*;
import io.renren.modules.ctb.entity.*;
import io.renren.modules.ctb.service.CtbOrderProcessingTradeService;
import io.renren.modules.ctb.vo.PriceItemVo;
import io.renren.modules.oss.cloud.OSSFactory;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service("ctbOrderProcessingTradeService")
public class CtbOrderProcessingTradeServiceImpl extends ServiceImpl<CtbOrderProcessingTradeDao, CtbOrderProcessingTradeEntity> implements CtbOrderProcessingTradeService {

    private final String RE_LINE = "^^^";

    private BindCbsContractCtbProcessingTradeDao bindCbsContractCtbProcessingTradeDao;
    private CtbOrderProcessingTradeDao ctbOrderProcessingTradeDao;
    private CtbOrderProcessingTradeMemberDao memberDao;
    private CtbOrderProcessingTradeGoodsDao orderProcessingTradeGoodsDao;
    private CtbGoodsPartNoDao goodsPartNoDao;
    private CtbOrderProcessingTradeStatusStreamDao ctbOrderProcessingTradeStatusStreamDao;
    private CtbOrderProcessingTradeManagerChangeStreamDao orderProcessingTradeManagerChangeStreamDao;
    private CtbImgOrderProcessingTradeDao imgOrderProcessingTradeDao;
    private CtbDocumentControlDao documentControlDao;

    private CstNptsEmlHeaderService emlHeaderService;
    private CommonFunction commonFunction;

    public CtbOrderProcessingTradeServiceImpl(
            BindCbsContractCtbProcessingTradeDao bindCbsContractCtbProcessingTradeDao,
            CtbOrderProcessingTradeDao ctbOrderProcessingTradeDao,
            CtbOrderProcessingTradeMemberDao memberDao,
            CtbOrderProcessingTradeGoodsDao orderProcessingTradeGoodsDao,
            CtbGoodsPartNoDao goodsPartNoDao,
            CtbOrderProcessingTradeStatusStreamDao ctbOrderProcessingTradeStatusStreamDao,
            CtbOrderProcessingTradeManagerChangeStreamDao orderProcessingTradeManagerChangeStreamDao,
            CtbImgOrderProcessingTradeDao imgOrderProcessingTradeDao,
            CstNptsEmlHeaderService emlHeaderService,
            CommonFunction commonFunction,
            CtbDocumentControlDao documentControlDao
    ) {
        this.bindCbsContractCtbProcessingTradeDao = bindCbsContractCtbProcessingTradeDao;
        this.ctbOrderProcessingTradeDao = ctbOrderProcessingTradeDao;
        this.memberDao = memberDao;
        this.orderProcessingTradeGoodsDao = orderProcessingTradeGoodsDao;
        this.goodsPartNoDao = goodsPartNoDao;
        this.ctbOrderProcessingTradeStatusStreamDao = ctbOrderProcessingTradeStatusStreamDao;
        this.orderProcessingTradeManagerChangeStreamDao = orderProcessingTradeManagerChangeStreamDao;
        this.imgOrderProcessingTradeDao = imgOrderProcessingTradeDao;
        this.emlHeaderService = emlHeaderService;
        this.commonFunction = commonFunction;
        this.documentControlDao = documentControlDao;
    }

    @Override
    public PageUtils queryIndex(CtbOrderProcessingTradeEntity entity) {
        IPage<CtbOrderProcessingTradeEntity> page = ctbOrderProcessingTradeDao.queryIndex(new QueryPage<CtbOrderProcessingTradeEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public Long saveReturnId(CtbOrderProcessingTradeEntity entity) {
        return saveContract(entity, ContractEnum.CONTRACT_STATUS_1.getCode());
    }

    private Long saveContract(CtbOrderProcessingTradeEntity entity, Integer status) {
        entity.setStatus(status);
        ctbOrderProcessingTradeDao.insert(entity);
        Long id = entity.getId();
        // 存入相关合作伙伴信息
        for (CtbOrderProcessingTradeMemberEntity memberEntity : entity.getMemberEntityList()) {
            memberEntity.setFkOrderProcessingTradeId(id);
            memberDao.insert(memberEntity);
        }
        // 为合同创建第一个状态流
        CtbOrderProcessingTradeStatusStreamEntity statusStreamEntity = new CtbOrderProcessingTradeStatusStreamEntity();
        statusStreamEntity.setFkOrderProcessingTradeId(id);
        statusStreamEntity.setRemark("创建");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(status);
        statusStreamEntity.setOperator(entity.getOperator());
        ctbOrderProcessingTradeStatusStreamDao.insert(statusStreamEntity);
        // 初始化负责人
        distributionManager(id, entity.getOperator(), entity.getOperator());
        return id;
    }

    @Override
    public CtbOrderProcessingTradeEntity detail(Long id) {
        return ctbOrderProcessingTradeDao.detail(id);
    }


    /**
     * @Description: 处理附件
     * @Author: HuangHe
     * @Date: 14:59 2019/10/17
     */
    @Transactional
    public Integer updateAllByIdPublic(CtbOrderProcessingTradeEntity entity) {
        ctbOrderProcessingTradeDao.updateById(entity);
        // 删除合同附件
        imgOrderProcessingTradeDao.delete(new QueryWrapper<CtbImgOrderProcessingTradeEntity>().
                eq("fk_order_processing_trade_id", entity.getId()));
        documentControlDao.delete(new QueryWrapper<CtbDocumentControlEntity>().
                eq("business_table", "ctb_img_order_processing_trade").
                eq("business_id", entity.getId()));
        // 更新合同附件
        if (entity.getImgOrderProcessingTradeEntityList() != null) {
            for (CtbImgOrderProcessingTradeEntity imgOrderProcessingTradeEntity : entity.getImgOrderProcessingTradeEntityList()) {
                imgOrderProcessingTradeEntity.setFkOrderProcessingTradeId(entity.getId());
                imgOrderProcessingTradeDao.insert(imgOrderProcessingTradeEntity);
                documentControlDao.insert(CtbDocumentControlEntity.builder().
                        businessTable("ctb_img_order_processing_trade").
                        businessId(entity.getId()).
                        businessFile(imgOrderProcessingTradeEntity.getImgUrl()).
                        build());
            }
        }
        BindCbsContractCtbProcessingTradeEntity bindCbsContractCtbProcessingTradeEntity = bindCbsContractCtbProcessingTradeDao.detailWithCbs(entity.getId());
        // 更新合同商品
        if (bindCbsContractCtbProcessingTradeEntity == null) {
            // 如果不是同步来的加贸单，按照普通编辑走
            // 删除料号
            List<CtbOrderProcessingTradeGoodsEntity> orderProcessingTradeGoodsEntityList = orderProcessingTradeGoodsDao.selectList(new QueryWrapper<CtbOrderProcessingTradeGoodsEntity>().eq("fk_order_processing_trade_id", entity.getId()));
            if (orderProcessingTradeGoodsEntityList != null) {
                for (CtbOrderProcessingTradeGoodsEntity orderProcessingTradeGoodsEntity : orderProcessingTradeGoodsEntityList) {
                    goodsPartNoDao.delete(new UpdateWrapper<CtbGoodsPartNoEntity>().eq("goods_part_no", orderProcessingTradeGoodsEntity.getId()));
                }
            }
            // 删除合同商品条目
            orderProcessingTradeGoodsDao.delete(new QueryWrapper<CtbOrderProcessingTradeGoodsEntity>().eq("fk_order_processing_trade_id", entity.getId()));
            // 重新插入合同商品、料号实体
            if (entity.getOrderProcessingTradeGoodsList() != null && !entity.getOrderProcessingTradeGoodsList().isEmpty()) {
                BigDecimal contractMoney = BigDecimal.ZERO;
                int inGoodsIndex = 0, outGoodsIndex = 0;
                for (CtbOrderProcessingTradeGoodsEntity orderProcessingTradeGoodsEntity : entity.getOrderProcessingTradeGoodsList()) {
                    orderProcessingTradeGoodsEntity.setIndexInOrderProcessingTrade(orderProcessingTradeGoodsEntity.getType() == 1 || orderProcessingTradeGoodsEntity.getType() == 3 ? ++inGoodsIndex : ++outGoodsIndex);
                    orderProcessingTradeGoodsEntity.setFkOrderProcessingTradeId(entity.getId());
                    orderProcessingTradeGoodsDao.insert(orderProcessingTradeGoodsEntity);
                    orderProcessingTradeGoodsEntity.getPartNoEntity().setGoodsPartNo(orderProcessingTradeGoodsEntity.getId());
                    goodsPartNoDao.insert(orderProcessingTradeGoodsEntity.getPartNoEntity());
                    // 计算价格
                    BigDecimal unitPrice = orderProcessingTradeGoodsEntity.getPartNoEntity().getUnitPrice();
                    BigDecimal contractCount = orderProcessingTradeGoodsEntity.getPartNoEntity().getContractCount();
                    contractMoney = contractMoney.add(unitPrice.multiply(contractCount));
                }
                // 更新合同价格
                ctbOrderProcessingTradeDao.updateById(entity);
            }
        } else {
            // 如果是同步来的加贸单，只允许编辑更新商品名录，直接更新即可
            for (CtbOrderProcessingTradeGoodsEntity orderProcessingTradeGoodsEntity : entity.getOrderProcessingTradeGoodsList()) {
                goodsPartNoDao.updateById(orderProcessingTradeGoodsEntity.getPartNoEntity());
            }
        }
        return 1;
    }

    @Override
    @Transactional
    public Integer updateAllById(CtbOrderProcessingTradeEntity entity) {
        return updateAllByIdPublic(entity);
    }

    @Override
    @Transactional
    public void updateEmlCode(CtbOrderProcessingTradeEntity entity) {
        update(new UpdateWrapper<CtbOrderProcessingTradeEntity>()
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
    public Integer deleteOne(CtbOrderProcessingTradeEntity entity) {
        CtbOrderProcessingTradeEntity c = ctbOrderProcessingTradeDao.selectById(entity.getId());
        if (c.getStatus() != ContractEnum.CONTRACT_STATUS_2.getCode()) {
            c.setStatus(entity.getStatus());
            ctbOrderProcessingTradeDao.updateById(c);
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
    public void submit(CtbOrderProcessingTradeStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbOrderProcessingTradeEntity entity = ctbOrderProcessingTradeDao.detail(statusStreamEntity.getFkOrderProcessingTradeId());
        String errMsg = checkContractStart(entity);
        if (errMsg != null) {
            throw new RuntimeException(errMsg);
        }
        CtbOrderProcessingTradeStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkOrderProcessingTradeId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == ContractEnum.CONTRACT_STATUS_1.getCode() || lastStatusStreamEntity.getStatus() == ContractEnum.CONTRACT_STATUS_3.getCode()) {
                entity.setStatus(ContractEnum.CONTRACT_STATUS_2.getCode());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(ContractEnum.CONTRACT_STATUS_2.getCode());

                ctbOrderProcessingTradeDao.updateById(entity);
                ctbOrderProcessingTradeStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("合同状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            entity.setStatus(ContractEnum.CONTRACT_STATUS_2.getCode());
            statusStreamEntity.setLastStatus(-1);
            statusStreamEntity.setStatus(ContractEnum.CONTRACT_STATUS_2.getCode());

            ctbOrderProcessingTradeDao.updateById(entity);
            ctbOrderProcessingTradeStatusStreamDao.insert(statusStreamEntity);
        }
    }

    /**
     * @Description: 撤审
     * 可以撤审的状态：2、已提审
     */
    @Override
    @Transactional
    public Integer submitBack(CtbOrderProcessingTradeStatusStreamEntity statusStreamEntity) {
        CtbOrderProcessingTradeEntity entity = ctbOrderProcessingTradeDao.selectById(statusStreamEntity.getFkOrderProcessingTradeId());
        CtbOrderProcessingTradeStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkOrderProcessingTradeId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == ContractEnum.CONTRACT_STATUS_2.getCode()) {
                try {
                    entity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                    ctbOrderProcessingTradeDao.updateById(entity);
                    ctbOrderProcessingTradeStatusStreamDao.insert(statusStreamEntity);
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
    public void check(CtbOrderProcessingTradeStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CtbOrderProcessingTradeEntity entity = ctbOrderProcessingTradeDao.detail(statusStreamEntity.getFkOrderProcessingTradeId());
        CtbOrderProcessingTradeStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkOrderProcessingTradeId());
        if (lastStatusStreamEntity != null) {
            if (ContractEnum.CONTRACT_STATUS_2.getCode() == entity.getStatus()) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                ctbOrderProcessingTradeDao.updateById(entity);
                ctbOrderProcessingTradeStatusStreamDao.insert(statusStreamEntity);
                // 如果最终审核结果通过，插入收支流水
                if (ContractEnum.CONTRACT_STATUS_4.getCode() == statusStreamEntity.getStatus()) {
                    // 如果非同步来的加贸单，最终审核结果通过，插入收支流水
                    if (entity.getBindEntity() == null) {
                        processPriceItemList(entity, statusStreamEntity.getOperator());
                    }
                }
            } else {
                throw new RuntimeException("合同状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个合同,请联系管理员");
        }
    }

    @Override
    @Transactional
    public void distributionManager(Long orderProcessingTradeId, Long nowManager, Long operator) {
        // 更新import表
        CtbOrderProcessingTradeEntity orderProcessingTradeEntity = ctbOrderProcessingTradeDao.selectById(orderProcessingTradeId);
        orderProcessingTradeEntity.setOperator(operator);
        orderProcessingTradeEntity.setManager(nowManager);
        ctbOrderProcessingTradeDao.updateById(orderProcessingTradeEntity);
        // 插入负责人变更
        CtbOrderProcessingTradeManagerChangeStreamEntity lastManagerChangeStreamEntity = getLastManagerChangeStreamEntity(orderProcessingTradeId);
        CtbOrderProcessingTradeManagerChangeStreamEntity managerChangeStreamEntity = CtbOrderProcessingTradeManagerChangeStreamEntity.builder().
                fkOrderProcessingTradeId(orderProcessingTradeId).
                manager(nowManager).
                operator(operator).
                createTime(new Date()).
                build();
        if (lastManagerChangeStreamEntity != null) {
            managerChangeStreamEntity.setLastManager(lastManagerChangeStreamEntity.getManager());
        }
        orderProcessingTradeManagerChangeStreamDao.insert(managerChangeStreamEntity);
    }

    /**
     * 获取上一个合同状态以便修改
     */
    private CtbOrderProcessingTradeStatusStreamEntity getLastStatusStreamEntity(Long contractId) {
        List<CtbOrderProcessingTradeStatusStreamEntity> statusStreamEntityList = ctbOrderProcessingTradeStatusStreamDao.selectList(new QueryWrapper<CtbOrderProcessingTradeStatusStreamEntity>().eq("fk_order_processing_trade_id", contractId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    private void processPriceItemList(CtbOrderProcessingTradeEntity entity, Long operator) {
        commonFunction.processCtbPriceItemList(PriceItemVo.builder()
                .tableName("ctb_order_processing_trade_price_item")
                .parameterField("fk_order_processing_trade_id")
                .parameterId(entity.getId())
                .serviceCompanyId(entity.getFkServiceCompanyId())
                .orderType(1)
                .name(entity.getTitle())
                .operator(operator)
                .build());
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

    private CtbOrderProcessingTradeManagerChangeStreamEntity getLastManagerChangeStreamEntity(Long orderProcessingTradeId) {
        List<CtbOrderProcessingTradeManagerChangeStreamEntity> managerChangeStreamEntityList = orderProcessingTradeManagerChangeStreamDao.selectList(new QueryWrapper<CtbOrderProcessingTradeManagerChangeStreamEntity>().eq("fk_order_processing_trade_id", orderProcessingTradeId).orderByAsc("id"));
        if (managerChangeStreamEntityList != null && !managerChangeStreamEntityList.isEmpty()) {
            return managerChangeStreamEntityList.get(managerChangeStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    private HashMap<String, Object> deleteById(Long id) {
        HashMap<String, Object> resultMap = new HashMap<>();

        CtbOrderProcessingTradeEntity contractEntity = ctbOrderProcessingTradeDao.detail(id);
        // 删除合同下商品和料号
        for (CtbOrderProcessingTradeGoodsEntity orderProcessingTradeGoodsEntity : contractEntity.getOrderProcessingTradeGoodsList()) {
            goodsPartNoDao.deleteById(orderProcessingTradeGoodsEntity.getPartNoEntity().getId());
            orderProcessingTradeGoodsDao.deleteById(orderProcessingTradeGoodsEntity.getId());
        }
        // 删除合同下附件
        for (CtbImgOrderProcessingTradeEntity imgContractEntity : contractEntity.getImgOrderProcessingTradeEntityList()) {
            imgOrderProcessingTradeDao.deleteById(imgContractEntity.getId());
        }
        // 删除合同
        ctbOrderProcessingTradeDao.deleteById(id);
        return resultMap;
    }

    /**
     * 判断合同完整性
     */
    private String checkContractStart(CtbOrderProcessingTradeEntity entity) {
        if (entity.getOrderProcessingTradeGoodsList().size() <= 0) {
            return "【" + entity.getTitle() + "】必须包含合同商品";
        } else {
            for (CtbOrderProcessingTradeGoodsEntity contractGoods : entity.getOrderProcessingTradeGoodsList()) {
                if (contractGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || contractGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    return "【" + entity.getTitle() + "】中合同商品数量和价格必须大于0";
                }
            }
        }
        return null;
    }

    /**
     * @description aeo导出excel
     * @author chenning
     * @date 2020/4/2 10:00
     */
    @Override
    public String AEOExportExcel(HttpServletResponse response, CtbOrderProcessingTradeEntity entity) {
        List<ExcelSheet<T>> list = new ArrayList<>();
        // 财务
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

}