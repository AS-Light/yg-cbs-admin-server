package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.enumeration.DocumentControlEnum;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.bind.entity.BindCbsCtbExportEntity;
import io.renren.modules.cbs.dao.*;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.cbs.service.CbsExportService;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import io.renren.modules.ctb.dao.CtbExportDao;
import io.renren.modules.ctb.entity.CtbExportEntity;
import io.renren.modules.ctb.vo.PriceItemVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service("cbsExportService")
public class CbsExportServiceImpl extends ServiceImpl<CbsExportDao, CbsExportEntity> implements CbsExportService {

    private CbsExportDao exportDao;
    private CbsContractDao contractDao;
    private CbsExportGoodsDao exportGoodsDao;
    private CbsGoodsPartNoDao goodsPartNoDao;
    private CbsExportStatusStreamDao exportStatusStreamDao;
    private CbsExportVoyageDao exportVoyageDao;
    private CbsMoneyInDao moneyInDao;
    private CbsMoneyOutDao moneyOutDao;
    private CommonFunction commonFunction;
    private CtbExportDao ctbExportDao;

    public CbsExportServiceImpl(CbsExportDao exportDao,
                                CbsContractDao contractDao,
                                CbsExportGoodsDao exportGoodsDao,
                                CbsGoodsPartNoDao goodsPartNoDao,
                                CbsExportStatusStreamDao exportStatusStreamDao,
                                CbsExportVoyageDao exportVoyageDao,
                                CbsMoneyInDao moneyInDao,
                                CbsMoneyOutDao moneyOutDao,
                                CommonFunction commonFunction,
                                CtbExportDao ctbExportDao) {
        this.exportDao = exportDao;
        this.contractDao = contractDao;
        this.exportGoodsDao = exportGoodsDao;
        this.goodsPartNoDao = goodsPartNoDao;
        this.exportStatusStreamDao = exportStatusStreamDao;
        this.exportVoyageDao = exportVoyageDao;
        this.moneyInDao = moneyInDao;
        this.moneyOutDao = moneyOutDao;
        this.commonFunction = commonFunction;
        this.ctbExportDao = ctbExportDao;
    }

    @Override
    public PageUtils queryIndex(CbsExportEntity entity) {
        IPage<CbsExportEntity> page = exportDao.queryIndex(new QueryPage<CbsExportEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public List<CbsExportEntity> queryForStoreOut() {
        return exportDao.queryForStoreOut();
    }

    @Override
    public CbsExportEntity detail(Long id) {
        return exportDao.detail(id);
    }

    @Override
    public HashMap<String, Object> saveReturnId(CbsExportEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        // 1?????????????????????????????????????????????
        // 2??????????????????????????????
        // ??? ?????????????????????????????????????????????1???3??????????????????????????????????????????????????????
        // ??? ?????????????????????????????????????????????4???12??????????????????????????????????????????????????????
        // ??? ?????????????????????????????????????????????????????????2???11?????????????????????????????????????????????????????????????????????????????????
        // ??? ??????????????????????????????????????????>13?????????????????????????????????????????? >= ??????????????????????????????????????????????????????????????????????????????
        CbsContractEntity contract = contractDao.simpleDetailWithGoods(entity.getFkContractId());
        List<CbsExportEntity> contractExportList = exportDao.queryByContractIdWithGoods(entity.getFkContractId());
        if (contractExportList == null || contractExportList.isEmpty()) {
            resultMap.put("code", 200);
            resultMap.put("id", createExport(entity));
            return resultMap;
        } else {
            for (CbsExportEntity contractExport : contractExportList) {
                if (Arrays.asList(1, 3, 4, 12).contains(contractExport.getStatus())) {
                    if (contractExport.getBindEntity() != null && contractExport.getBindEntity().getIsWorkByCtb()) {
                        resultMap.put("code", 500);
                        resultMap.put("msg", "????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                        return resultMap;
                    } else {
                        resultMap.put("code", 200);
                        resultMap.put("id", contractExport.getId());
                        return resultMap;
                    }
                } else if (Arrays.asList(2, 11).contains(contractExport.getStatus())) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "??????????????????????????????????????????????????????????????????");
                    return resultMap;
                }
            }
            boolean allGoodsCountComplete = true;
            for (CbsContractGoodsEntity contractGoods : contract.getContractGoodsList()) {
                CbsGoodsPartNoEntity goodsPartNoEntity = contractGoods.getPartNoEntity();
                if (goodsPartNoEntity.getContractCount().compareTo(goodsPartNoEntity.getExportCount()) > 0) {
                    allGoodsCountComplete = false;
                }
            }
            if (!allGoodsCountComplete || (entity.getForceToCreate() != null && entity.getForceToCreate())) {
                resultMap.put("code", 200);
                resultMap.put("id", createExport(entity));
                return resultMap;
            } else {
                resultMap.put("code", 300);
                resultMap.put("msg", "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                return resultMap;
            }
        }
    }

    /**
     * ???????????????????????????
     */
    private Long createExport(CbsExportEntity entity) {
        exportDao.insert(entity);
        // ???????????????
        CbsContractEntity contractEntity = contractDao.detail(entity.getFkContractId());
        for (CbsContractGoodsEntity contractGoodsEntity : contractEntity.getContractGoodsList()) {
            CbsExportGoodsEntity exportGoodsEntity = new CbsExportGoodsEntity();
            exportGoodsEntity.setFkExportId(entity.getId());
            exportGoodsEntity.setCount(contractGoodsEntity.getPartNoEntity().getContractCount().subtract(contractGoodsEntity.getPartNoEntity().getExportCount()));
            exportGoodsEntity.setStoreOutCount(BigDecimal.ZERO);
            exportGoodsEntity.setCreateTime(new Date());
            exportGoodsEntity.setGoodsPartNo(contractGoodsEntity.getId());
            exportGoodsEntity.setUpdateTime(new Date());
            exportGoodsDao.insert(exportGoodsEntity);
        }

        // ????????????????????????????????????
        CbsExportStatusStreamEntity statusStreamEntity = new CbsExportStatusStreamEntity();
        statusStreamEntity.setFkExportId(entity.getId());
        statusStreamEntity.setRemark("??????");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        exportStatusStreamDao.insert(statusStreamEntity);
        return entity.getId();
    }

    @Override
    @Transactional
    public Integer updateAllById(CbsExportEntity entity) {
        CbsExportEntity cbsExportEntity = exportDao.selectById(entity.getId());
        entity.setFkContractId(cbsExportEntity.getFkContractId());
        exportDao.updateById(entity);
        // ????????????
        if (entity.getExportGoodsList() != null) {
            List<CbsExportGoodsEntity> oldGoodsList = exportGoodsDao.listByExportId(entity.getId());
            for (CbsExportGoodsEntity oldGoods : oldGoodsList) {
                // ???????????????
                exportGoodsDao.deleteById(oldGoods);
            }
            List<CbsExportGoodsEntity> newGoodsList = entity.getExportGoodsList();
            for (CbsExportGoodsEntity newGoods : newGoodsList) {
                // ???????????????
                exportGoodsDao.insert(newGoods);
            }
        }
        // ??????????????????
        if (entity.getVoyageList() != null && !entity.getVoyageList().isEmpty()) {
            CbsExportVoyageEntity voyageEntity = entity.getVoyageList().get(0);
            voyageEntity.setCreateTime(new Date());
            voyageEntity.setFkExportId(entity.getId());
            if (voyageEntity.getId() == null) {
                exportVoyageDao.insert(voyageEntity);
            } else {
                exportVoyageDao.updateById(voyageEntity);
            }
        }
        if (entity.getImgExportContractShipList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgExportContractShipEntity imgContractShipEntity : entity.getImgExportContractShipList()) {
                imgList.add(imgContractShipEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_EXPORT_CONTRACT_SHIP.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportPackingListList() != null) {
            /*imgExportPackingListDao.delete(new QueryWrapper<CbsImgExportPackingListEntity>().eq("fk_export_id", entity.getId()));
            for (CbsImgExportPackingListEntity imgPackingListEntity : entity.getImgExportPackingListList()) {
                imgExportPackingListDao.insert(imgPackingListEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgExportPackingListEntity imgPackingListEntity : entity.getImgExportPackingListList()) {
                imgList.add(imgPackingListEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_EXPORT_PACKING_LIST.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportInvoiceList() != null) {
            /*imgExportInvoiceDao.delete(new QueryWrapper<CbsImgExportInvoiceEntity>().eq("fk_export_id", entity.getId()));
            for (CbsImgExportInvoiceEntity imgExportInvoiceEntity : entity.getImgExportInvoiceList()) {
                imgExportInvoiceDao.insert(imgExportInvoiceEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgExportInvoiceEntity imgExportInvoiceEntity : entity.getImgExportInvoiceList()) {
                imgList.add(imgExportInvoiceEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_EXPORT_INVOICE.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportLadingBillList() != null) {
            /*imgExportLadingBillDao.delete(new QueryWrapper<CbsImgExportLadingBillEntity>().eq("fk_export_id", entity.getId()));
            for (CbsImgExportLadingBillEntity imgExportLadingBillEntity : entity.getImgExportLadingBillList()) {
                imgExportLadingBillDao.insert(imgExportLadingBillEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgExportLadingBillEntity imgExportLadingBillEntity : entity.getImgExportLadingBillList()) {
                imgList.add(imgExportLadingBillEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_EXPORT_LADING_BILL.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportDeliveryOrderList() != null) {
            /*imgExportDeliveryOrderDao.delete(new QueryWrapper<CbsImgExportDeliveryOrderEntity>().eq("fk_export_id", entity.getId()));
            for (CbsImgExportDeliveryOrderEntity imgExportDeliveryOrderEntity : entity.getImgExportDeliveryOrderList()) {
                imgExportDeliveryOrderDao.insert(imgExportDeliveryOrderEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgExportDeliveryOrderEntity imgExportDeliveryOrderEntity : entity.getImgExportDeliveryOrderList()) {
                imgList.add(imgExportDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_EXPORT_DELIVERY_ORDER.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportOthersList() != null) {
            /*imgExportOthersDao.delete(new QueryWrapper<CbsImgExportOthersEntity>().eq("fk_export_id", entity.getId()));
            for (CbsImgExportOthersEntity imgExportOthersEntity : entity.getImgExportOthersList()) {
                imgExportOthersDao.insert(imgExportOthersEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgExportOthersEntity imgExportOthersEntity : entity.getImgExportOthersList()) {
                imgList.add(imgExportOthersEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_EXPORT_OTHERS.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportEntryBillList() != null) {
            /*imgExportEntryBillDao.delete(new QueryWrapper<CbsImgExportEntryBillEntity>().eq("fk_export_id", entity.getId()));
            for (CbsImgExportEntryBillEntity imgExportEntryBillEntity : entity.getImgExportEntryBillList()) {
                imgExportEntryBillDao.insert(imgExportEntryBillEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgExportEntryBillEntity imgExportEntryBillEntity : entity.getImgExportEntryBillList()) {
                imgList.add(imgExportEntryBillEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_EXPORT_ENTRY_BILL.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }

        if (entity.getImgExportPayInAdvanceList() != null) {
           /* imgExportPayInAdvanceDao.delete(new QueryWrapper<CbsImgExportPayInAdvanceEntity>().eq("fk_export_id", entity.getId()));
            for (CbsImgExportPayInAdvanceEntity imgExportPayInAdvanceEntity : entity.getImgExportPayInAdvanceList()) {
                imgExportPayInAdvanceDao.insert(imgExportPayInAdvanceEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgExportPayInAdvanceEntity imgExportPayInAdvanceEntity : entity.getImgExportPayInAdvanceList()) {
                imgList.add(imgExportPayInAdvanceEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_EXPORT_PAY_IN_ADVANCE.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportPayTailList() != null) {
            /*imgExportPayTailDao.delete(new QueryWrapper<CbsImgExportPayTailEntity>().eq("fk_export_id", entity.getId()));
            for (CbsImgExportPayTailEntity imgExportPayTailEntity : entity.getImgExportPayTailList()) {
                imgExportPayTailDao.insert(imgExportPayTailEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgExportPayTailEntity imgExportPayTailEntity : entity.getImgExportPayTailList()) {
                imgList.add(imgExportPayTailEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_EXPORT_PAY_TAIL.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportLicenseAgreementList() != null) {
            /*imgExportLicenseAgreementDao.delete(new QueryWrapper<CbsImgExportLicenseAgreementEntity>().eq("fk_export_id", entity.getId()));
            for (CbsImgExportLicenseAgreementEntity imgExportLicenseAgreementEntity : entity.getImgExportLicenseAgreementList()) {
                imgExportLicenseAgreementDao.insert(imgExportLicenseAgreementEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgExportLicenseAgreementEntity imgExportLicenseAgreementEntity : entity.getImgExportLicenseAgreementList()) {
                imgList.add(imgExportLicenseAgreementEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_EXPORT_LICENSE_AGREEMENT.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgExportPowerOfAttorneyList() != null) {
            /*imgExportPowerOfAttorneyDao.delete(new QueryWrapper<CbsImgExportPowerOfAttorneyEntity>().eq("fk_export_id", entity.getId()));
            for (CbsImgExportPowerOfAttorneyEntity imgExportPowerOfAttorneyEntity : entity.getImgExportPowerOfAttorneyList()) {
                imgExportPowerOfAttorneyDao.insert(imgExportPowerOfAttorneyEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgExportPowerOfAttorneyEntity imgExportPowerOfAttorneyEntity : entity.getImgExportPowerOfAttorneyList()) {
                imgList.add(imgExportPowerOfAttorneyEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_EXPORT_POWER_OF_ATTORNEY.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_export_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        return 1;
    }

    @Override
    @Transactional
    public Object updateVoyageChange(CbsExportVoyageEntity entity) {
        CbsExportEntity exportEntity = exportDao.selectById(entity.getFkExportId());
        exportEntity.setPassPortCode(entity.getPassPortCode());
        exportDao.updateById(exportEntity);
        if (entity.getId() == null) {
            entity.setCreateTime(new Date());
            return exportVoyageDao.insert(entity);
        } else {
            return exportVoyageDao.updateById(entity);
        }
    }

    /**
     * ??????
     */
    @Override
    @Transactional
    public void submit(CbsExportStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsExportEntity entity = exportDao.detail(statusStreamEntity.getFkExportId());
        String errMsg = checkExportComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CbsExportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkExportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                entity.setStatus(2);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(2);

                exportDao.updateById(entity);
                exportStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("????????????????????????????????????????????????????????????????????????");
            }
        } else {
            throw new RuntimeException("???????????????????????????,??????????????????");
        }
    }

    /**
     * @Description: ??????
     * ????????????????????????1???????????????3????????????
     */
    @Override
    @Transactional
    public Integer submitBack(CbsExportStatusStreamEntity statusStreamEntity) {
        CbsExportEntity entity = exportDao.selectById(statusStreamEntity.getFkExportId());
        CbsExportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkExportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                exportDao.updateById(entity);
                exportStatusStreamDao.insert(statusStreamEntity);
                return 1;
            } else {
                return -1;
            }
        } else {
            return -3;
        }
    }

    /**
     * @Description: ??????
     * ????????????????????????2?????????
     */
    @Override
    @Transactional
    public void check(CbsExportStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsExportEntity entity = exportDao.simpleDetailWithGoodsItems(statusStreamEntity.getFkExportId());
        CbsExportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkExportId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                exportDao.updateById(entity);
                exportStatusStreamDao.insert(statusStreamEntity);
                if (statusStreamEntity.getStatus() == 4) {
                    for (CbsExportGoodsEntity exportGoodsEntity : entity.getExportGoodsList()) {
                        // ????????????????????????
                        CbsGoodsPartNoEntity partNoEntity = exportGoodsEntity.getPartNoEntity();
                        partNoEntity.setExportCount(partNoEntity.getExportCount().add(exportGoodsEntity.getCount()));
                        goodsPartNoDao.update(partNoEntity, new UpdateWrapper<CbsGoodsPartNoEntity>().eq("goods_part_no", partNoEntity.getGoodsPartNo()));
                    }
                }
            } else {
                throw new RuntimeException("?????????????????????????????????????????????");
            }
        } else {
            throw new RuntimeException("???????????????????????????,??????????????????");
        }
    }

    @Override
    @Transactional
    public void submitCustom(CbsExportStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsExportEntity entity = exportDao.detail(statusStreamEntity.getFkExportId());
        String errMsg = checkExportCustomComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CbsExportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkExportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 4 || lastStatusStreamEntity.getStatus() == 12) {
                entity.setStatus(11);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(11);

                exportDao.updateById(entity);
                exportStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("????????????????????????????????????????????????????????????????????????");
            }
        } else {
            throw new RuntimeException("???????????????????????????,??????????????????");
        }
    }

    @Override
    @Transactional
    public Integer submitBackCustom(CbsExportStatusStreamEntity statusStreamEntity) {
        CbsExportEntity entity = exportDao.selectById(statusStreamEntity.getFkExportId());
        CbsExportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkExportId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 11) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                exportDao.updateById(entity);
                exportStatusStreamDao.insert(statusStreamEntity);
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
    public void checkCustom(CbsExportStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsExportEntity entity = exportDao.detail(statusStreamEntity.getFkExportId());
        CbsExportStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkExportId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 11) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                exportDao.updateById(entity);
                exportStatusStreamDao.insert(statusStreamEntity);

                // ?????????????????????????????????????????????????????????????????????
                if (statusStreamEntity.getStatus() == 13) {
                    insertMoneyIO(entity, statusStreamEntity.getOperator());
                    // ????????????????????????????????????Ctb?????????????????????5
                    BindCbsCtbExportEntity bindCbsCtbExportEntity = entity.getBindEntity();
                    if (bindCbsCtbExportEntity != null) {
                        CtbExportEntity ctbExportEntity = bindCbsCtbExportEntity.getCtbExportEntity();
                        ctbExportEntity.setStatus(5);
                        ctbExportDao.updateByIdWithoutTenant(ctbExportEntity);
                        // ?????????priceList???moneyIn???moneyOut
                        processCtbPriceItemList(ctbExportEntity);
                    }
                }
            } else {
                throw new RuntimeException("?????????????????????????????????????????????");
            }
        } else {
            throw new RuntimeException("???????????????????????????,??????????????????");
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        List<Long> goodsIds = exportGoodsDao.listIdsByExportIds(ids);
        if (goodsIds != null && !goodsIds.isEmpty()) {
            exportGoodsDao.deleteBatchIds(goodsIds);
        }
        removeByIds(Arrays.asList(ids));
    }

    private void processCtbPriceItemList(CtbExportEntity entity) {
        commonFunction.processCtbPriceItemListWithoutTenant(PriceItemVo.builder()
                .tableName("ctb_export_price_item")
                .parameterField("fk_export_id")
                .serviceCompanyId(entity.getFkServiceCompanyId())
                .orderType(3)
                .parameterId(entity.getId())
                .name(entity.getTitle())
                .operator(-1L)
                .build(), entity.getCtbTenantId());
    }

    private void insertMoneyIO(CbsExportEntity entity, Long operator) {
        // ???????????? - ??????
        CbsMoneyInEntity moneyInEntity = new CbsMoneyInEntity();
        moneyInEntity.setTitle("???????????? - " + entity.getId() + " ????????????");
        moneyInEntity.setFkContractId(entity.getFkContractId());
        moneyInEntity.setCurrencyCode(entity.getContractEntity().getCurrencyCode());
        moneyInEntity.setType(2);
        moneyInEntity.setStatus(1);
        moneyInEntity.setOperator(operator);
        BigDecimal money = BigDecimal.ZERO;
        for (CbsExportGoodsEntity exportGoodsEntity : entity.getExportGoodsList()) {
            money = money.add(exportGoodsEntity.getCount().multiply(exportGoodsEntity.getPartNoEntity().getUnitPrice()));
        }
        moneyInEntity.setMoney(money);
        moneyInDao.insert(moneyInEntity);
        // ????????????
        CbsMoneyOutEntity moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("???????????? - " + entity.getId() + " ????????????");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode(entity.getFreightCurrencyCode());
        moneyOutEntity.setType(11);
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        moneyOutEntity.setMoney(entity.getFreightMoney());
        moneyOutDao.insert(moneyOutEntity);
        // ??????
        moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("???????????? - " + entity.getId() + " ????????????");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode(entity.getPremiumCurrencyCode());
        moneyOutEntity.setType(12);
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        moneyOutEntity.setMoney(entity.getPremiumMoney());
        moneyOutDao.insert(moneyOutEntity);
        // ??????
        moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("???????????? - " + entity.getId() + " ????????????");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode(entity.getIncidentalCurrencyCode());
        moneyOutEntity.setType(13);
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        moneyOutEntity.setMoney(entity.getIncidentalMoney());
        moneyOutDao.insert(moneyOutEntity);
        // ?????????
        moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("???????????? - " + entity.getId() + " ?????????");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode(entity.getContractEntity().getCurrencyCode());
        moneyOutEntity.setType(3);
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        moneyOutEntity.setMoney(entity.getCustomsFeeMoney());
        moneyOutDao.insert(moneyOutEntity);
    }

    private CbsExportStatusStreamEntity getLastStatusStreamEntity(Long exportId) {
        List<CbsExportStatusStreamEntity> statusStreamEntityList = exportStatusStreamDao.selectList(new QueryWrapper<CbsExportStatusStreamEntity>().eq("fk_export_id", exportId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * ????????????????????????
     */
    private String checkExportComplete(CbsExportEntity entity) {
        if (entity.getExportGoodsList().size() <= 0) {
            return "?????????????????????????????????";
        } else {
            for (CbsExportGoodsEntity exportGoods : entity.getExportGoodsList()) {
                if (exportGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || exportGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    return "???????????????????????????????????????0";
                }
            }
        }
        // ????????????
        if (entity.getFromCountry() == null) return "?????????????????????";
        if (entity.getFromPort() == null) return "?????????????????????";
        if (entity.getToCountry() == null) return "?????????";
        if (entity.getToPort() == null) return "????????????????????????";
        // ????????????
        if (entity.getVoyageList() == null || entity.getVoyageList().isEmpty()) {
            return "???????????????????????????";
        } else {
            CbsExportVoyageEntity voyageEntity = entity.getVoyageList().get(entity.getVoyageList().size() - 1);
            if (voyageEntity == null) return "???????????????????????????????????????";
            else if (voyageEntity.getShipCompanyEntity() == null) return "????????????????????????";
            else if (StringUtils.isEmpty(voyageEntity.getTransportName())) return "??????????????????????????????";
            else if (StringUtils.isEmpty(voyageEntity.getVoyageNo())) return "?????????????????????";
            else if (StringUtils.isEmpty(voyageEntity.getBLNo())) return "?????????????????????";
        }
        // ??????
        if (StringUtils.isEmpty(entity.getInvoiceCode())) return "??????????????????????????????";
        if (entity.getInvoiceMoney() == null || entity.getInvoiceMoney().compareTo(BigDecimal.ZERO) <= 0)
            return "?????????????????????????????????";

        /*if (entity.getFreightMoney() == null || entity.getFreightMoney().compareTo(BigDecimal.ZERO) <= 0) return "???????????????????????????";
        if (entity.getPremiumMoney() == null || entity.getPremiumMoney().compareTo(BigDecimal.ZERO) <= 0) return "???????????????????????????";
        if (entity.getIncidentalMoney() == null || entity.getIncidentalMoney().compareTo(BigDecimal.ZERO) <= 0) return "???????????????????????????";
        if (entity.getFreightCurrency() == null) return "????????????????????????";
        if (entity.getPremiumCurrency() == null) return "????????????????????????";
        if (entity.getIncidentalCurrency() == null) return "????????????????????????";*/

        // ??????
        if (entity.getImgExportLadingBillList() == null || entity.getImgExportLadingBillList().isEmpty())
            return "????????????????????????";
        /*if (entity.getImgExportContractShipList() == null || entity.getImgExportContractShipList().isEmpty())
            return "??????????????????????????????";*/
        if (entity.getImgExportPackingListList() == null || entity.getImgExportPackingListList().isEmpty())
            return "????????????????????????";
        if (entity.getImgExportInvoiceList() == null || entity.getImgExportInvoiceList().isEmpty()) return "??????????????????????????????";

        return null;
    }

    /**
     * ????????????????????????
     */
    private String checkExportCustomComplete(CbsExportEntity entity) {
        // ????????????
        if (entity.getVoyageList() != null && entity.getVoyageList().size() > 1) {
            CbsExportVoyageEntity voyageEntity = entity.getVoyageList().get(entity.getVoyageList().size() - 1);
            String baseError = "?????????????????????????????????";
            if (voyageEntity == null) return "???????????????????????????????????????";
            else if (entity.getPassPort() == null) return baseError + "?????????????????????";
            else if (voyageEntity.getShipCompanyEntity() == null) return baseError + "????????????????????????";
            else if (StringUtils.isEmpty(voyageEntity.getTransportName())) return baseError + "??????????????????????????????";
            else if (StringUtils.isEmpty(voyageEntity.getVoyageNo())) return baseError + "?????????????????????";
            else if (StringUtils.isEmpty(voyageEntity.getBLNo())) return baseError + "?????????????????????";
        }
        // ????????????
        if (entity.getArrivalTime() == null) return "????????????????????????";
        if (entity.getCustomsBrokerEntity() == null) return "?????????????????????";
        if (entity.getCustomsFeeMoney() == null || entity.getCustomsFeeMoney().compareTo(BigDecimal.ZERO) <= 0)
            return "???????????????????????????";
        // ????????????
        if (entity.getEntryBillCode() == null) return "??????????????????????????????";
        if (entity.getEntryBillCreateTime() == null) return "????????????????????????";
        if (entity.getEntryBillPassTime() == null) return "????????????????????????";
        if (entity.getImgExportEntryBillList() == null || entity.getImgExportEntryBillList().isEmpty())
            return "???????????????????????????";
        if (entity.getImgExportDeliveryOrderList() == null || entity.getImgExportDeliveryOrderList().isEmpty())
            return "???????????????????????????";

        return null;
    }
}