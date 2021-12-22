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
        // 1、无合同对应的出口单，直接创建
        // 2、有合同对应的出口单
        // ① 合同有信息编辑中的出口单（状态1或3），直接跳转到该出口单信息编辑对话框
        // ② 合同有关务编辑中的出口单（状态4或12），直接跳转到该出口单关务编辑对话框
        // ③ 合同有信息审核或关务审核的出口单（状态2或11），提示【您有待审核的出口单，请耐心等待上级审核完毕】
        // ④ 所有出口单都已审核完毕（状态>13），“所有”已出口的商品数量 >= 合同商品数量，提示【合同数量已经完成，是否继续创建】
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
                        resultMap.put("msg", "当前合同已有编辑中的出口单，且该出口单目前由报关行操作中，暂时不可操作。");
                        return resultMap;
                    } else {
                        resultMap.put("code", 200);
                        resultMap.put("id", contractExport.getId());
                        return resultMap;
                    }
                } else if (Arrays.asList(2, 11).contains(contractExport.getStatus())) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "您有待审核的出口单，请耐心审核上级审核完毕。");
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
                resultMap.put("msg", "所有合同中料件需出口数量已经完成，继续出口该合同中的商品可能产生必须的海关手续，请知晓后进行后续操作。");
                return resultMap;
            }
        }
    }

    /**
     * 具体创建出口单方法
     */
    private Long createExport(CbsExportEntity entity) {
        exportDao.insert(entity);
        // 初始化商品
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

        // 为出口单创建第一个状态流
        CbsExportStatusStreamEntity statusStreamEntity = new CbsExportStatusStreamEntity();
        statusStreamEntity.setFkExportId(entity.getId());
        statusStreamEntity.setRemark("创建");
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
        // 更新商品
        if (entity.getExportGoodsList() != null) {
            List<CbsExportGoodsEntity> oldGoodsList = exportGoodsDao.listByExportId(entity.getId());
            for (CbsExportGoodsEntity oldGoods : oldGoodsList) {
                // 删除旧商品
                exportGoodsDao.deleteById(oldGoods);
            }
            List<CbsExportGoodsEntity> newGoodsList = entity.getExportGoodsList();
            for (CbsExportGoodsEntity newGoods : newGoodsList) {
                // 添加新商品
                exportGoodsDao.insert(newGoods);
            }
        }
        // 更新单内航次
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
     * 提审
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
                throw new RuntimeException("出口单状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个出口单,请联系管理员");
        }
    }

    /**
     * @Description: 提审
     * 可以提审的状态：1、编辑中，3、不通过
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
     * @Description: 审核
     * 可以审核的状态：2、提审
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
                        // 更新料号进口数量
                        CbsGoodsPartNoEntity partNoEntity = exportGoodsEntity.getPartNoEntity();
                        partNoEntity.setExportCount(partNoEntity.getExportCount().add(exportGoodsEntity.getCount()));
                        goodsPartNoDao.update(partNoEntity, new UpdateWrapper<CbsGoodsPartNoEntity>().eq("goods_part_no", partNoEntity.getGoodsPartNo()));
                    }
                }
            } else {
                throw new RuntimeException("出口单状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个出口单,请联系管理员");
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
                throw new RuntimeException("出口单状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个出口单,请联系管理员");
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

                // 如果最终审核结果通过，插入出口单涉及的收入支出
                if (statusStreamEntity.getStatus() == 13) {
                    insertMoneyIO(entity, statusStreamEntity.getOperator());
                    // 如果是已绑定的合同，更新Ctb对应加贸单状态5
                    BindCbsCtbExportEntity bindCbsCtbExportEntity = entity.getBindEntity();
                    if (bindCbsCtbExportEntity != null) {
                        CtbExportEntity ctbExportEntity = bindCbsCtbExportEntity.getCtbExportEntity();
                        ctbExportEntity.setStatus(5);
                        ctbExportDao.updateByIdWithoutTenant(ctbExportEntity);
                        // 确定后priceList到moneyIn和moneyOut
                        processCtbPriceItemList(ctbExportEntity);
                    }
                }
            } else {
                throw new RuntimeException("出口单状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个出口单,请联系管理员");
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
        // 出口货值 - 收入
        CbsMoneyInEntity moneyInEntity = new CbsMoneyInEntity();
        moneyInEntity.setTitle("出口单号 - " + entity.getId() + " 出口货值");
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
        // 国际运费
        CbsMoneyOutEntity moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("出口单号 - " + entity.getId() + " 国际运费");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode(entity.getFreightCurrencyCode());
        moneyOutEntity.setType(11);
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        moneyOutEntity.setMoney(entity.getFreightMoney());
        moneyOutDao.insert(moneyOutEntity);
        // 保费
        moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("出口单号 - " + entity.getId() + " 国际保费");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode(entity.getPremiumCurrencyCode());
        moneyOutEntity.setType(12);
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        moneyOutEntity.setMoney(entity.getPremiumMoney());
        moneyOutDao.insert(moneyOutEntity);
        // 杂费
        moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("出口单号 - " + entity.getId() + " 国际杂费");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode(entity.getIncidentalCurrencyCode());
        moneyOutEntity.setType(13);
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        moneyOutEntity.setMoney(entity.getIncidentalMoney());
        moneyOutDao.insert(moneyOutEntity);
        // 报关费
        moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("出口单号 - " + entity.getId() + " 报关费");
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
     * 判断出口单完整性
     */
    private String checkExportComplete(CbsExportEntity entity) {
        if (entity.getExportGoodsList().size() <= 0) {
            return "出口单必须包含出口商品";
        } else {
            for (CbsExportGoodsEntity exportGoods : entity.getExportGoodsList()) {
                if (exportGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || exportGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    return "出口商品数量和价格必须大于0";
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
            CbsExportVoyageEntity voyageEntity = entity.getVoyageList().get(entity.getVoyageList().size() - 1);
            if (voyageEntity == null) return "最近一次修改的航次信息为空";
            else if (voyageEntity.getShipCompanyEntity() == null) return "船务公司不能为空";
            else if (StringUtils.isEmpty(voyageEntity.getTransportName())) return "运输工具名称不能为空";
            else if (StringUtils.isEmpty(voyageEntity.getVoyageNo())) return "航次号不能为空";
            else if (StringUtils.isEmpty(voyageEntity.getBLNo())) return "提单号不能为空";
        }
        // 费用
        if (StringUtils.isEmpty(entity.getInvoiceCode())) return "出口发票编号不能为空";
        if (entity.getInvoiceMoney() == null || entity.getInvoiceMoney().compareTo(BigDecimal.ZERO) <= 0)
            return "出口发票金额必须大于零";

        /*if (entity.getFreightMoney() == null || entity.getFreightMoney().compareTo(BigDecimal.ZERO) <= 0) return "运费金额必须大于零";
        if (entity.getPremiumMoney() == null || entity.getPremiumMoney().compareTo(BigDecimal.ZERO) <= 0) return "保费金额必须大于零";
        if (entity.getIncidentalMoney() == null || entity.getIncidentalMoney().compareTo(BigDecimal.ZERO) <= 0) return "杂费金额必须大于零";
        if (entity.getFreightCurrency() == null) return "运费币制不能为空";
        if (entity.getPremiumCurrency() == null) return "保费币制不能为空";
        if (entity.getIncidentalCurrency() == null) return "杂费币制不能为空";*/

        // 附件
        if (entity.getImgExportLadingBillList() == null || entity.getImgExportLadingBillList().isEmpty())
            return "提单附件不能为空";
        /*if (entity.getImgExportContractShipList() == null || entity.getImgExportContractShipList().isEmpty())
            return "船务合同附件不能为空";*/
        if (entity.getImgExportPackingListList() == null || entity.getImgExportPackingListList().isEmpty())
            return "箱单附件不能为空";
        if (entity.getImgExportInvoiceList() == null || entity.getImgExportInvoiceList().isEmpty()) return "出口发票附件不能为空";

        return null;
    }

    /**
     * 判断出口单完整性
     */
    private String checkExportCustomComplete(CbsExportEntity entity) {
        // 船务公司
        if (entity.getVoyageList() != null && entity.getVoyageList().size() > 1) {
            CbsExportVoyageEntity voyageEntity = entity.getVoyageList().get(entity.getVoyageList().size() - 1);
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
        if (entity.getEntryBillCode() == null) return "出口报关单号不能为空";
        if (entity.getEntryBillCreateTime() == null) return "提报时间不能为空";
        if (entity.getEntryBillPassTime() == null) return "清关时间不能为空";
        if (entity.getImgExportEntryBillList() == null || entity.getImgExportEntryBillList().isEmpty())
            return "报关单附件不能为空";
        if (entity.getImgExportDeliveryOrderList() == null || entity.getImgExportDeliveryOrderList().isEmpty())
            return "提货单附件不能为空";

        return null;
    }
}