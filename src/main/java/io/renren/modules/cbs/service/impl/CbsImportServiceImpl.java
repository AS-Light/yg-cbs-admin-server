package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.enumeration.DocumentControlEnum;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.bind.entity.BindCbsCtbImportEntity;
import io.renren.modules.cbs.dao.*;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.cbs.service.CbsImportService;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import io.renren.modules.ctb.dao.CtbImportDao;
import io.renren.modules.ctb.entity.CtbImportEntity;
import io.renren.modules.ctb.vo.PriceItemVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service("cbsImportService")
public class CbsImportServiceImpl extends ServiceImpl<CbsImportDao, CbsImportEntity> implements CbsImportService {

    private CbsImportDao importDao;
    private CbsContractDao contractDao;
    private CbsImportStatusStreamDao importStatusStreamDao;
    private CbsImportGoodsDao importGoodsDao;
    private CbsImportVoyageDao importVoyageDao;
    private CbsGoodsPartNoDao goodsPartNoDao;
    private CbsMoneyOutDao moneyOutDao;
    private CommonFunction commonFunction;
    private CtbImportDao ctbImportDao;

    public CbsImportServiceImpl(
            CbsImportDao importDao,
            CbsContractDao contractDao,
            CbsImportStatusStreamDao importStatusStreamDao,
            CbsImportGoodsDao importGoodsDao,
            CbsImportVoyageDao importVoyageDao,
            CbsGoodsPartNoDao goodsPartNoDao,
            CbsMoneyOutDao moneyOutDao,
            CommonFunction commonFunction,
            CtbImportDao ctbImportDao
    ) {
        this.importDao = importDao;
        this.contractDao = contractDao;
        this.importStatusStreamDao = importStatusStreamDao;
        this.importGoodsDao = importGoodsDao;
        this.importVoyageDao = importVoyageDao;
        this.goodsPartNoDao = goodsPartNoDao;
        this.moneyOutDao = moneyOutDao;
        this.commonFunction = commonFunction;
        this.ctbImportDao = ctbImportDao;
    }

    @Override
    public PageUtils queryIndex(CbsImportEntity entity) {
        IPage<CbsImportEntity> page = importDao.queryIndex(new QueryPage<CbsImportEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public List<CbsImportEntity> queryForStoreIn() {
        return importDao.queryForStoreIn();
    }

    @Override
    public CbsImportEntity detail(Long id) {
        return importDao.detail(id);
    }

    @Override
    @Transactional
    public HashMap<String, Object> saveReturnId(CbsImportEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        // 1、无合同对应的进口单，直接创建
        // 2、有合同对应的进口单
        // ① 合同有信息编辑中的进口单（状态1或3），直接跳转到该进口单信息编辑对话框
        // ② 合同有关务编辑中的进口单（状态4或12），直接跳转到该进口单关务编辑对话框
        // ③ 合同有信息审核或关务审核的进口单（状态2或11），提示【您有待审核的进口单，请耐心等待上级审核完毕】
        // ④ 所有进口单都已审核完毕（状态>13），“所有”已进口的商品数量 >= 合同商品数量，提示【合同数量已经完成，是否继续创建】
        CbsContractEntity contract = contractDao.simpleDetailWithGoods(entity.getFkContractId());
        List<CbsImportEntity> contractImportList = importDao.queryByContractIdWithGoods(entity.getFkContractId());
        if (contractImportList == null || contractImportList.isEmpty()) {
            resultMap.put("code", 200);
            resultMap.put("id", createImport(entity));
            return resultMap;
        } else {
            for (CbsImportEntity contractImport : contractImportList) {
                if (Arrays.asList(1, 3, 4, 12).contains(contractImport.getStatus())) {
                    if (contractImport.getBindEntity() != null && contractImport.getBindEntity().getIsWorkByCtb()) {
                        resultMap.put("code", 500);
                        resultMap.put("msg", "当前合同已有编辑中的进口单，且该进口单目前由报关行操作中，暂时不可操作。");
                        return resultMap;
                    } else {
                        resultMap.put("code", 200);
                        resultMap.put("id", contractImport.getId());
                        return resultMap;
                    }
                } else if (Arrays.asList(2, 11).contains(contractImport.getStatus())) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "您有待审核的进口单，请耐心审核上级审核完毕");
                    return resultMap;
                }
            }
            boolean allGoodsCountComplete = true;
            for (CbsContractGoodsEntity contractGoods : contract.getContractGoodsList()) {
                CbsGoodsPartNoEntity goodsPartNoEntity = contractGoods.getPartNoEntity();
                if (goodsPartNoEntity.getContractCount().compareTo(goodsPartNoEntity.getImportCount()) > 0) {
                    allGoodsCountComplete = false;
                }
            }
            if (!allGoodsCountComplete || (entity.getForceToCreate() != null && entity.getForceToCreate())) {
                resultMap.put("code", 200);
                resultMap.put("id", createImport(entity));
                return resultMap;
            } else {
                resultMap.put("code", 300);
                resultMap.put("msg", "所有合同中料件需进口数量已经完成，继续进口该合同中的商品可能产生必须的海关手续，请知晓后进行后续操作。");
                return resultMap;
            }
        }
    }

    /**
     * 具体创建进口单方法
     */
    private Long createImport(CbsImportEntity entity) {
        importDao.insert(entity);
        CbsContractEntity contractEntity = contractDao.detail(entity.getFkContractId());
        for (CbsContractGoodsEntity contractGoodsEntity : contractEntity.getContractGoodsList()) {
            CbsImportGoodsEntity importGoodsEntity = new CbsImportGoodsEntity();
            importGoodsEntity.setFkImportId(entity.getId());
            importGoodsEntity.setCount(BigDecimal.ZERO);
            importGoodsEntity.setCreateTime(new Date());
            importGoodsEntity.setGoodsPartNo(contractGoodsEntity.getId());
            importGoodsEntity.setUpdateTime(new Date());
            importGoodsDao.insert(importGoodsEntity);
        }

        // 为进口单创建第一个状态流
        CbsImportStatusStreamEntity statusStreamEntity = new CbsImportStatusStreamEntity();
        statusStreamEntity.setFkImportId(entity.getId());
        statusStreamEntity.setRemark("创建");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        importStatusStreamDao.insert(statusStreamEntity);
        return entity.getId();
    }

    @Override
    @Transactional
    public Integer updateAllById(CbsImportEntity entity) {
        CbsImportEntity cbsImportEntity = importDao.selectById(entity.getId());
        entity.setFkContractId(cbsImportEntity.getFkContractId());
        importDao.updateById(entity);
        // 更新商品
        if (entity.getImportGoodsList() != null) {
            List<CbsImportGoodsEntity> oldGoodsList = importGoodsDao.listByImportId(entity.getId());
            for (CbsImportGoodsEntity oldGoods : oldGoodsList) {
                // 删除旧商品
                importGoodsDao.deleteById(oldGoods);
            }
            List<CbsImportGoodsEntity> newGoodsList = entity.getImportGoodsList();
            for (CbsImportGoodsEntity newGoods : newGoodsList) {
                // 添加新商品
                importGoodsDao.insert(newGoods);
            }
        }
        // 更新进出国和进出口岸
        if (entity.getFromCountry() != null) entity.setFromCountryCode(entity.getFromCountry().getCode());
        if (entity.getFromPort() != null) entity.setFromPortCode(entity.getFromPort().getPortCode());
        if (entity.getToCountry() != null) entity.setToCountryCode(entity.getToCountry().getCode());
        if (entity.getToPort() != null) entity.setToPortCode(entity.getToPort().getDeclPort());
        // 更新单内航次
        if (entity.getVoyageList() != null && !entity.getVoyageList().isEmpty()) {
            CbsImportVoyageEntity voyageEntity = entity.getVoyageList().get(0);
            voyageEntity.setCreateTime(new Date());
            voyageEntity.setFkImportId(entity.getId());
            if (voyageEntity.getId() == null) {
                importVoyageDao.insert(voyageEntity);
            } else {
                importVoyageDao.updateById(voyageEntity);
            }
        }
        // 更新船务合同
        if (entity.getImgImportContractShipList() != null) {
            /*imgImportContractShipDao.delete(new QueryWrapper<CbsImgImportContractShipEntity>().eq("fk_import_id", entity.getId()));
            for (CbsImgImportContractShipEntity imgContractShipEntity : entity.getImgImportContractShipList()) {
                imgImportContractShipDao.insert(imgContractShipEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgImportContractShipEntity imgContractShipEntity : entity.getImgImportContractShipList()) {
                imgList.add(imgContractShipEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_IMPORT_CONTRACT_SHIP.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新箱单
        if (entity.getImgImportPackingListList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgImportPackingListEntity imgPackingListEntity : entity.getImgImportPackingListList()) {
                imgList.add(imgPackingListEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_IMPORT_PACKING_LIST.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新发票附件
        if (entity.getImgImportInvoiceList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgImportInvoiceEntity imgImportInvoiceEntity : entity.getImgImportInvoiceList()) {
                imgList.add(imgImportInvoiceEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_IMPORT_INVOICE.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgImportLadingBillList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgImportLadingBillEntity imgImportLadingBillEntity : entity.getImgImportLadingBillList()) {
                imgList.add(imgImportLadingBillEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_IMPORT_LADING_BILL.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新提货单附件
        if (entity.getImgImportDeliveryOrderList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgImportDeliveryOrderEntity imgImportDeliveryOrderEntity : entity.getImgImportDeliveryOrderList()) {
                imgList.add(imgImportDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_IMPORT_DELIVERY_ORDER.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新其他单据附件
        if (entity.getImgImportOthersList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgImportOthersEntity imgImportOthersEntity : entity.getImgImportOthersList()) {
                imgList.add(imgImportOthersEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_IMPORT_OTHERS.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新报关单附件
        if (entity.getImgImportEntryBillList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgImportEntryBillEntity imgImportEntryBillEntity : entity.getImgImportEntryBillList()) {
                imgList.add(imgImportEntryBillEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_IMPORT_ENTRY_BILL.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新首付款附件
        if (entity.getImgImportPayInAdvanceList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgImportPayInAdvanceEntity imgImportPayInAdvanceEntity : entity.getImgImportPayInAdvanceList()) {
                imgList.add(imgImportPayInAdvanceEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_IMPORT_PAY_IN_ADVANCE.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新尾款附件
        if (entity.getImgImportPayTailList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgImportPayTailEntity imgImportPayTailEntity : entity.getImgImportPayTailList()) {
                imgList.add(imgImportPayTailEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_IMPORT_PAY_TAIL.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新代理授权协议附件
        if (entity.getImgImportLicenseAgreementList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgImportLicenseAgreementEntity imgImportLicenseAgreementEntity : entity.getImgImportLicenseAgreementList()) {
                imgList.add(imgImportLicenseAgreementEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_IMPORT_LICENSE_AGREEMENT.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新授权书附件
        if (entity.getImgImportPowerOfAttorneyList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgImportPowerOfAttorneyEntity imgImportPowerOfAttorneyEntity : entity.getImgImportPowerOfAttorneyList()) {
                imgList.add(imgImportPowerOfAttorneyEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_IMPORT_POWER_OF_ATTORNEY.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_import_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        return 1;
    }

    /**
     * 变更航次
     */
    @Override
    @Transactional
    public Object updateVoyageChange(CbsImportVoyageEntity entity) {
        CbsImportEntity importEntity = importDao.selectById(entity.getFkImportId());
        importEntity.setPassPortCode(entity.getPassPortCode());
        importDao.updateById(importEntity);
        if (entity.getId() == null) {
            entity.setCreateTime(new Date());
            return importVoyageDao.insert(entity);
        } else {
            return importVoyageDao.updateById(entity);
        }
    }

    /**
     * 提审
     */
    @Override
    @Transactional
    public void submit(CbsImportStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsImportEntity entity = importDao.detail(statusStreamEntity.getFkImportId());
        String errMsg = checkImportComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CbsImportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkImportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                entity.setStatus(2);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(2);

                importDao.updateById(entity);
                importStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("进口单状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个进口单,请联系管理员");
        }
    }

    /**
     * @Description: 提审
     * 可以提审的状态：1、编辑中，3、不通过
     */
    @Override
    @Transactional
    public Integer submitBack(CbsImportStatusStreamEntity statusStreamEntity) {
        CbsImportEntity entity = importDao.selectById(statusStreamEntity.getFkImportId());
        CbsImportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkImportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                importDao.updateById(entity);
                importStatusStreamDao.insert(statusStreamEntity);
                return 1;
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
    public void check(CbsImportStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsImportEntity entity = importDao.simpleDetailWithGoodsItems(statusStreamEntity.getFkImportId());
        CbsImportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkImportId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                importDao.updateById(entity);
                importStatusStreamDao.insert(statusStreamEntity);
                if (statusStreamEntity.getStatus() == 4) {
                    for (CbsImportGoodsEntity importGoodsEntity : entity.getImportGoodsList()) {
                        // 更新料号进口数量
                        CbsGoodsPartNoEntity partNoEntity = importGoodsEntity.getPartNoEntity();
                        partNoEntity.setImportCount(partNoEntity.getImportCount().add(importGoodsEntity.getCount()));
                        goodsPartNoDao.update(partNoEntity, new UpdateWrapper<CbsGoodsPartNoEntity>().eq("goods_part_no", partNoEntity.getGoodsPartNo()));
                    }
                }
            } else {
                throw new RuntimeException("进口单状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个进口单,请联系管理员");
        }
    }

    @Override
    @Transactional
    public void submitCustom(CbsImportStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsImportEntity entity = importDao.detail(statusStreamEntity.getFkImportId());
        String errMsg = checkImportCustomComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CbsImportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkImportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 4 || lastStatusStreamEntity.getStatus() == 12) {
                entity.setStatus(11);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(11);

                importDao.updateById(entity);
                importStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("进口单状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个进口单,请联系管理员");
        }
    }

    @Override
    @Transactional
    public Integer submitBackCustom(CbsImportStatusStreamEntity statusStreamEntity) {
        CbsImportEntity entity = importDao.selectById(statusStreamEntity.getFkImportId());
        CbsImportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkImportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 11) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                importDao.updateById(entity);
                importStatusStreamDao.insert(statusStreamEntity);
                return 1;
            } else {
                return -1;
            }
        } else {
            return -3;
        }
    }

    @Override
    @Transactional
    public void checkCustom(CbsImportStatusStreamEntity statusStreamEntity, Long operator) throws RuntimeException {
        CbsImportEntity entity = importDao.detail(statusStreamEntity.getFkImportId());
        CbsImportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkImportId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 11) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                importDao.updateById(entity);
                importStatusStreamDao.insert(statusStreamEntity);

                // 如果最终审核结果通过，插入进口单涉及的收入支出
                if (statusStreamEntity.getStatus() == 13) {
                    insertMoneyIO(entity, operator);
                    // 如果是已绑定的合同，更新Ctb对应加贸单状态5
                    BindCbsCtbImportEntity bindCbsCtbImportEntity = entity.getBindEntity();
                    if (bindCbsCtbImportEntity != null) {
                        CtbImportEntity ctbImportEntity = bindCbsCtbImportEntity.getCtbImportEntity();
                        ctbImportEntity.setStatus(5);
                        ctbImportDao.updateByIdWithoutTenant(ctbImportEntity);
                        // 确定后priceList到moneyIn和moneyOut
                        processCtbPriceItemList(ctbImportEntity);
                    }
                }
            } else {
                throw new RuntimeException("进口单状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个进口单,请联系管理员");
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        List<Long> goodsIds = importGoodsDao.listIdsByImportIds(ids);
        if (goodsIds != null && !goodsIds.isEmpty()) {
            importGoodsDao.deleteBatchIds(goodsIds);
        }
        removeByIds(Arrays.asList(ids));
    }

    private void insertMoneyIO(CbsImportEntity entity, Long operator) {
        // 进口货值 - 支出
        CbsMoneyOutEntity moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("进口单号 - " + entity.getId() + " 进口货值");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode(entity.getContractEntity().getCurrencyCode());
        moneyOutEntity.setType(1);
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        BigDecimal money = BigDecimal.ZERO;
        for (CbsImportGoodsEntity importGoodsEntity : entity.getImportGoodsList()) {
            money = money.add(importGoodsEntity.getCount().multiply(importGoodsEntity.getPartNoEntity().getUnitPrice()));
        }
        moneyOutEntity.setMoney(money);
        moneyOutDao.insert(moneyOutEntity);
        // 报关费
        moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("进口单号 - " + entity.getId() + " 报关费");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode(entity.getContractEntity().getCurrencyCode());
        moneyOutEntity.setType(3);
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        money = entity.getCustomsFeeMoney();
        moneyOutEntity.setMoney(money);
        moneyOutDao.insert(moneyOutEntity);
    }

    private void processCtbPriceItemList(CtbImportEntity entity) {
        commonFunction.processCtbPriceItemListWithoutTenant(PriceItemVo.builder()
                .tableName("ctb_import_price_item")
                .parameterField("fk_import_id")
                .parameterId(entity.getId())
                .serviceCompanyId(entity.getFkServiceCompanyId())
                .orderType(2)
                .name(entity.getTitle())
                .operator(-1L)
                .build(), entity.getCtbTenantId());
    }

    /**
     * 获取上一个状态
     */
    private CbsImportStatusStreamEntity getLastStatusStreamEntity(Long importId) {
        List<CbsImportStatusStreamEntity> statusStreamEntityList = importStatusStreamDao.selectList(new QueryWrapper<CbsImportStatusStreamEntity>().eq("fk_import_id", importId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 判断进口单完整性
     */
    private String checkImportComplete(CbsImportEntity entity) {
        if (entity.getImportGoodsList().size() <= 0) {
            return "进口单必须包含进口商品";
        } else {
            for (CbsImportGoodsEntity importGoods : entity.getImportGoodsList()) {
                if (importGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || importGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    return "进口商品数量和价格必须大于0";
                }
            }
        }
        // 进出口岸
        if (entity.getFromCountry() == null) return "启运国不能为空";
        if (entity.getFromPort() == null) return "启运港不能为空";
        if (entity.getToCountry() == null) return "贸易国";
        if (entity.getToPort() == null) return "入境口岸不能为空";
        // 船务公司
        if (entity.getVoyageList() == null || entity.getVoyageList().isEmpty()) {
            return "至少有一条航次信息";
        } else {
            CbsImportVoyageEntity voyageEntity = entity.getVoyageList().get(entity.getVoyageList().size() - 1);
            if (voyageEntity == null) return "最近一次修改的航次信息为空";
            else if (voyageEntity.getShipCompanyEntity() == null) return "船务公司不能为空";
            else if (StringUtils.isEmpty(voyageEntity.getTransportName())) return "运输工具名称不能为空";
            else if (StringUtils.isEmpty(voyageEntity.getVoyageNo())) return "航次号不能为空";
            else if (StringUtils.isEmpty(voyageEntity.getBLNo())) return "提单号不能为空";
        }
        // 费用
        if (StringUtils.isEmpty(entity.getInvoiceCode())) return "进口发票编号不能为空";
        if (entity.getInvoiceMoney() == null || entity.getInvoiceMoney().compareTo(BigDecimal.ZERO) <= 0)
            return "进口发票金额必须大于零";

        /*if (entity.getFreightMoney() == null || entity.getFreightMoney().compareTo(BigDecimal.ZERO) <= 0) return "运费金额必须大于零";
        if (entity.getPremiumMoney() == null || entity.getPremiumMoney().compareTo(BigDecimal.ZERO) <= 0) return "保费金额必须大于零";
        if (entity.getIncidentalMoney() == null || entity.getIncidentalMoney().compareTo(BigDecimal.ZERO) <= 0) return "杂费金额必须大于零";
        if (entity.getFreightCurrency() == null) return "运费币制不能为空";
        if (entity.getPremiumCurrency() == null) return "保费币制不能为空";
        if (entity.getIncidentalCurrency() == null) return "杂费币制不能为空";*/

        // 附件
        if (entity.getImgImportLadingBillList() == null || entity.getImgImportLadingBillList().isEmpty())
            return "提单附件不能为空";
        /*if (entity.getImgImportContractShipList() == null || entity.getImgImportContractShipList().isEmpty())
            return "船务合同附件不能为空";*/
        if (entity.getImgImportPackingListList() == null || entity.getImgImportPackingListList().isEmpty())
            return "箱单附件不能为空";
        if (entity.getImgImportInvoiceList() == null || entity.getImgImportInvoiceList().isEmpty()) return "进口发票附件不能为空";

        return null;
    }

    /**
     * 判断进口单完整性
     */
    private String checkImportCustomComplete(CbsImportEntity entity) {
        // 船务公司
        if (entity.getVoyageList() != null && entity.getVoyageList().size() > 1) {
            CbsImportVoyageEntity voyageEntity = entity.getVoyageList().get(entity.getVoyageList().size() - 1);
            String baseError = "已变更过航次信息，新的";
            if (voyageEntity == null) return "最近一次修改的航次信息为空";
            else if (entity.getPassPort() == null) return baseError + "经停港不能为空";
            else if (voyageEntity.getShipCompanyEntity() == null) return baseError + "船务公司不能为空";
            else if (StringUtils.isEmpty(voyageEntity.getTransportName())) return baseError + "运输工具名称不能为空";
            else if (StringUtils.isEmpty(voyageEntity.getVoyageNo())) return baseError + "航次号不能为空";
            else if (StringUtils.isEmpty(voyageEntity.getBLNo())) return baseError + "提单号不能为空";
        }
        // 代理报关
        if (entity.getArrivalTime() == null) return "抵港时间不能为空";
        if (entity.getCustomsBrokerEntity() == null) return "报关行不能为空";
        if (entity.getCustomsFeeMoney() == null || entity.getCustomsFeeMoney().compareTo(BigDecimal.ZERO) <= 0)
            return "报关费用必须大于零";
        // 报关结果
        if (entity.getEntryBillCode() == null) return "进口报关单号不能为空";
        if (entity.getEntryBillCreateTime() == null) return "提报时间不能为空";
        if (entity.getEntryBillPassTime() == null) return "清关时间不能为空";
        if (entity.getImgImportEntryBillList() == null || entity.getImgImportEntryBillList().isEmpty())
            return "报关单附件不能为空";
        if (entity.getHasDeliveryCode()) {
            if (StringUtils.isEmpty(entity.getDeliveryCode()))
                return "提货码不能为空";
        } else if (entity.getImgImportDeliveryOrderList() == null || entity.getImgImportDeliveryOrderList().isEmpty())
            return "提货单附件不能为空";

        return null;
    }
}