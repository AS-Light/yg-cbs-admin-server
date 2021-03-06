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
        // 1?????????????????????????????????????????????
        // 2??????????????????????????????
        // ??? ?????????????????????????????????????????????1???3??????????????????????????????????????????????????????
        // ??? ?????????????????????????????????????????????4???12??????????????????????????????????????????????????????
        // ??? ?????????????????????????????????????????????????????????2???11?????????????????????????????????????????????????????????????????????????????????
        // ??? ??????????????????????????????????????????>13?????????????????????????????????????????? >= ??????????????????????????????????????????????????????????????????????????????
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
                        resultMap.put("msg", "????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                        return resultMap;
                    } else {
                        resultMap.put("code", 200);
                        resultMap.put("id", contractImport.getId());
                        return resultMap;
                    }
                } else if (Arrays.asList(2, 11).contains(contractImport.getStatus())) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "???????????????????????????????????????????????????????????????");
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
                resultMap.put("msg", "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                return resultMap;
            }
        }
    }

    /**
     * ???????????????????????????
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

        // ????????????????????????????????????
        CbsImportStatusStreamEntity statusStreamEntity = new CbsImportStatusStreamEntity();
        statusStreamEntity.setFkImportId(entity.getId());
        statusStreamEntity.setRemark("??????");
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
        // ????????????
        if (entity.getImportGoodsList() != null) {
            List<CbsImportGoodsEntity> oldGoodsList = importGoodsDao.listByImportId(entity.getId());
            for (CbsImportGoodsEntity oldGoods : oldGoodsList) {
                // ???????????????
                importGoodsDao.deleteById(oldGoods);
            }
            List<CbsImportGoodsEntity> newGoodsList = entity.getImportGoodsList();
            for (CbsImportGoodsEntity newGoods : newGoodsList) {
                // ???????????????
                importGoodsDao.insert(newGoods);
            }
        }
        // ??????????????????????????????
        if (entity.getFromCountry() != null) entity.setFromCountryCode(entity.getFromCountry().getCode());
        if (entity.getFromPort() != null) entity.setFromPortCode(entity.getFromPort().getPortCode());
        if (entity.getToCountry() != null) entity.setToCountryCode(entity.getToCountry().getCode());
        if (entity.getToPort() != null) entity.setToPortCode(entity.getToPort().getDeclPort());
        // ??????????????????
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
        // ??????????????????
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
        // ????????????
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
        // ??????????????????
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
        // ?????????????????????
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
        // ????????????????????????
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
        // ?????????????????????
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
        // ?????????????????????
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
        // ??????????????????
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
        // ??????????????????????????????
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
        // ?????????????????????
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
     * ????????????
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
     * ??????
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
     * @Description: ??????
     * ????????????????????????2?????????
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
                        // ????????????????????????
                        CbsGoodsPartNoEntity partNoEntity = importGoodsEntity.getPartNoEntity();
                        partNoEntity.setImportCount(partNoEntity.getImportCount().add(importGoodsEntity.getCount()));
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
                throw new RuntimeException("????????????????????????????????????????????????????????????????????????");
            }
        } else {
            throw new RuntimeException("???????????????????????????,??????????????????");
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

                // ?????????????????????????????????????????????????????????????????????
                if (statusStreamEntity.getStatus() == 13) {
                    insertMoneyIO(entity, operator);
                    // ????????????????????????????????????Ctb?????????????????????5
                    BindCbsCtbImportEntity bindCbsCtbImportEntity = entity.getBindEntity();
                    if (bindCbsCtbImportEntity != null) {
                        CtbImportEntity ctbImportEntity = bindCbsCtbImportEntity.getCtbImportEntity();
                        ctbImportEntity.setStatus(5);
                        ctbImportDao.updateByIdWithoutTenant(ctbImportEntity);
                        // ?????????priceList???moneyIn???moneyOut
                        processCtbPriceItemList(ctbImportEntity);
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
        List<Long> goodsIds = importGoodsDao.listIdsByImportIds(ids);
        if (goodsIds != null && !goodsIds.isEmpty()) {
            importGoodsDao.deleteBatchIds(goodsIds);
        }
        removeByIds(Arrays.asList(ids));
    }

    private void insertMoneyIO(CbsImportEntity entity, Long operator) {
        // ???????????? - ??????
        CbsMoneyOutEntity moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("???????????? - " + entity.getId() + " ????????????");
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
        // ?????????
        moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("???????????? - " + entity.getId() + " ?????????");
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
     * ?????????????????????
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
     * ????????????????????????
     */
    private String checkImportComplete(CbsImportEntity entity) {
        if (entity.getImportGoodsList().size() <= 0) {
            return "?????????????????????????????????";
        } else {
            for (CbsImportGoodsEntity importGoods : entity.getImportGoodsList()) {
                if (importGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || importGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
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
            CbsImportVoyageEntity voyageEntity = entity.getVoyageList().get(entity.getVoyageList().size() - 1);
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
        if (entity.getImgImportLadingBillList() == null || entity.getImgImportLadingBillList().isEmpty())
            return "????????????????????????";
        /*if (entity.getImgImportContractShipList() == null || entity.getImgImportContractShipList().isEmpty())
            return "??????????????????????????????";*/
        if (entity.getImgImportPackingListList() == null || entity.getImgImportPackingListList().isEmpty())
            return "????????????????????????";
        if (entity.getImgImportInvoiceList() == null || entity.getImgImportInvoiceList().isEmpty()) return "??????????????????????????????";

        return null;
    }

    /**
     * ????????????????????????
     */
    private String checkImportCustomComplete(CbsImportEntity entity) {
        // ????????????
        if (entity.getVoyageList() != null && entity.getVoyageList().size() > 1) {
            CbsImportVoyageEntity voyageEntity = entity.getVoyageList().get(entity.getVoyageList().size() - 1);
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
        if (entity.getImgImportEntryBillList() == null || entity.getImgImportEntryBillList().isEmpty())
            return "???????????????????????????";
        if (entity.getHasDeliveryCode()) {
            if (StringUtils.isEmpty(entity.getDeliveryCode()))
                return "?????????????????????";
        } else if (entity.getImgImportDeliveryOrderList() == null || entity.getImgImportDeliveryOrderList().isEmpty())
            return "???????????????????????????";

        return null;
    }
}