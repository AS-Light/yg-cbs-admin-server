package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import io.renren.common.enumeration.DocumentControlEnum;
import io.renren.common.enumeration.MoneyEnum;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.*;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.cbs.service.CbsPurchaseService;
import io.renren.modules.cbs.service.CbsStoreGoodsInService;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service("cbsPurchaseService")
public class CbsPurchaseServiceImpl extends ServiceImpl<CbsPurchaseDao, CbsPurchaseEntity> implements CbsPurchaseService {

    private CbsPurchaseDao purchaseDao;
    private CbsContractDao contractDao;
    private CbsPurchaseStatusStreamDao purchaseStatusStreamDao;
    private CbsPurchaseGoodsDao purchaseGoodsDao;
    private CbsGoodsPartNoDao goodsPartNoDao;
    private CbsImgPurchaseInvoiceDao imgPurchaseInvoiceDao;
    private CbsImgPurchaseDeliveryOrderDao imgPurchaseDeliveryOrderDao;
    private CbsImgPurchaseReceiptDao imgPurchaseReceiptDao;
    private CbsMoneyOutDao moneyOutDao;

    private CbsStoreGoodsInService storeGoodsInService;
    private CommonFunction commonFunction;

    @Autowired
    public CbsPurchaseServiceImpl(CbsPurchaseDao purchaseDao,
                                  CbsContractDao contractDao,
                                  CbsPurchaseStatusStreamDao purchaseStatusStreamDao,
                                  CbsPurchaseGoodsDao purchaseGoodsDao,
                                  CbsGoodsPartNoDao goodsPartNoDao,
                                  CbsImgPurchaseInvoiceDao imgPurchaseInvoiceDao,
                                  CbsImgPurchaseDeliveryOrderDao imgPurchaseDeliveryOrderDao,
                                  CbsImgPurchaseReceiptDao imgPurchaseReceiptDao,
                                  CbsMoneyOutDao moneyOutDao,
                                  CommonFunction commonFunction) {
        this.purchaseDao = purchaseDao;
        this.contractDao = contractDao;
        this.purchaseStatusStreamDao = purchaseStatusStreamDao;
        this.purchaseGoodsDao = purchaseGoodsDao;
        this.goodsPartNoDao = goodsPartNoDao;
        this.imgPurchaseInvoiceDao = imgPurchaseInvoiceDao;
        this.imgPurchaseDeliveryOrderDao = imgPurchaseDeliveryOrderDao;
        this.imgPurchaseReceiptDao = imgPurchaseReceiptDao;
        this.moneyOutDao = moneyOutDao;
        this.commonFunction = commonFunction;
    }

    @Autowired
    public void setStoreGoodsInService(CbsStoreGoodsInService storeGoodsInService) {
        this.storeGoodsInService = storeGoodsInService;
    }

    @Override
    public PageUtils queryIndex(CbsPurchaseEntity entity) {
        IPage<CbsPurchaseEntity> page = purchaseDao.queryIndex(new QueryPage<CbsPurchaseEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public List<CbsPurchaseEntity> queryForStoreIn() {
        return purchaseDao.queryForStoreIn();
    }

    @Override
    public CbsPurchaseEntity detail(Long id) {
        return purchaseDao.detail(id);
    }

    @Override
    @Transactional
    public Long saveReturnId(CbsPurchaseEntity entity) {
        // 1、无合同对应的国内收货单，直接创建
        // 2、有合同对应的国内收货单
        // ① 合同有信息编辑中的国内收货单（状态1或3），直接跳转到该国内收货单信息编辑对话框
        // ③ 合同有信息审核或关务审核的国内收货单（状态2），提示【您有待审核的国内收货单，请耐心等待上级审核完毕】
        // ④ 所有国内收货单都已审核完毕（状态>3），“所有”已国内收货的商品数量 >= 合同商品数量，提示【合同数量已经完成，是否继续创建】
        CbsContractEntity contract = contractDao.simpleDetailWithGoods(entity.getFkContractId());
        List<CbsPurchaseEntity> contractPurchaseList = purchaseDao.queryByContractIdWithGoods(entity.getFkContractId());
        if (contractPurchaseList == null || contractPurchaseList.isEmpty()) {
            return createPurchase(entity);
        } else {
            for (CbsPurchaseEntity contractPurchase : contractPurchaseList) {
                if (Arrays.asList(1, 3).contains(contractPurchase.getStatus())) {
                    return contractPurchase.getId();
                } else if (contractPurchase.getStatus() == 3) {
                    throw new RuntimeException("您有待审核的国内收货单，请耐心审核上级审核完毕");
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
                return createPurchase(entity);
            } else {
                Map<String, Object> errMsgMap = new HashMap<>();
                errMsgMap.put("code", 300);
                errMsgMap.put("msg", "所有合同中料件需接收数量已经完成，继续接收该合同中的商品可能造成数据错误，请知晓风险后进行后续操作。");
                throw new RuntimeException(new Gson().toJson(errMsgMap));
            }
        }
    }

    /**
     * 具体创建国内收货单方法
     */
    private Long createPurchase(CbsPurchaseEntity entity) {
        purchaseDao.insert(entity);
        // 初始化商品
        CbsContractEntity contractEntity = contractDao.detail(entity.getFkContractId());
        for (CbsContractGoodsEntity contractGoodsEntity : contractEntity.getContractGoodsList()) {
            CbsPurchaseGoodsEntity purchaseGoodsEntity = new CbsPurchaseGoodsEntity();
            purchaseGoodsEntity.setFkPurchaseId(entity.getId());
            purchaseGoodsEntity.setCount(BigDecimal.ZERO);
            purchaseGoodsEntity.setCreateTime(new Date());
            purchaseGoodsEntity.setGoodsPartNo(contractGoodsEntity.getId());
            purchaseGoodsEntity.setUpdateTime(new Date());
            purchaseGoodsDao.insert(purchaseGoodsEntity);
        }
        // 为国内收货单创建第一个状态流
        CbsPurchaseStatusStreamEntity statusStreamEntity = new CbsPurchaseStatusStreamEntity();
        statusStreamEntity.setFkPurchaseId(entity.getId());
        statusStreamEntity.setRemark("创建");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        purchaseStatusStreamDao.insert(statusStreamEntity);
        return entity.getId();
    }

    @Override
    @Transactional
    public Integer updateAllById(CbsPurchaseEntity entity) {
        purchaseDao.updateById(entity);
        // 更新商品
        if (entity.getPurchaseGoodsList() != null) {
            List<CbsPurchaseGoodsEntity> oldGoodsList = purchaseGoodsDao.listByPurchaseId(entity.getId());
            for (CbsPurchaseGoodsEntity oldGoods : oldGoodsList) {
                // 删除旧商品
                purchaseGoodsDao.deleteById(oldGoods);
            }
            List<CbsPurchaseGoodsEntity> newGoodsList = entity.getPurchaseGoodsList();
            for (CbsPurchaseGoodsEntity newGoods : newGoodsList) {
                // 添加新商品
                purchaseGoodsDao.insert(newGoods);
            }
        }
        // 更新发票附件
        if (entity.getImgPurchaseInvoiceList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgPurchaseInvoiceEntity imgImportInvoiceEntity : entity.getImgPurchaseInvoiceList()) {
                imgList.add(imgImportInvoiceEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_PURCHASE_INVOICE.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_purchase_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新提货单附件
        if (entity.getImgPurchaseDeliveryOrderList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgPurchaseDeliveryOrderEntity imgPurchaseDeliveryOrderEntity : entity.getImgPurchaseDeliveryOrderList()) {
                imgList.add(imgPurchaseDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_PURCHASE_DELIVERY_ORDER.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_purchase_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        // 更新收货单附件
        if (entity.getImgPurchaseReceiptList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgPurchaseReceiptEntity imgPurchaseReceiptEntity : entity.getImgPurchaseReceiptList()) {
                imgList.add(imgPurchaseReceiptEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_PURCHASE_RECEIPT.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_purchase_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        return 1;
    }

    /**
     * 提审
     */
    @Override
    @Transactional
    public void submit(CbsPurchaseStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsPurchaseEntity entity = purchaseDao.detail(statusStreamEntity.getFkPurchaseId());
        String errMsg = checkPurchaseComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CbsPurchaseStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkPurchaseId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                entity.setStatus(2);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(2);

                purchaseDao.updateById(entity);
                purchaseStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("国内收货单状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个国内收货单,请联系管理员");
        }
    }

    /**
     * @Description: 提审
     * 可以提审的状态：1、编辑中，3、不通过
     */
    @Override
    @Transactional
    public Integer submitBack(CbsPurchaseStatusStreamEntity statusStreamEntity) {
        CbsPurchaseEntity entity = purchaseDao.selectById(statusStreamEntity.getFkPurchaseId());
        CbsPurchaseStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkPurchaseId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                purchaseDao.updateById(entity);
                purchaseStatusStreamDao.insert(statusStreamEntity);
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
    public HashMap<String, Object> check(CbsPurchaseStatusStreamEntity statusStreamEntity, Long operator) {
        HashMap<String, Object> resultMap = new HashMap<>();
        CbsPurchaseEntity entity = purchaseDao.detail(statusStreamEntity.getFkPurchaseId());
        CbsPurchaseStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkPurchaseId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                purchaseDao.updateById(entity);
                purchaseStatusStreamDao.insert(statusStreamEntity);

                // 如果最终审核结果通过，插入进口单涉及的收入支出
                if (statusStreamEntity.getStatus() == 4) {
                    for (CbsPurchaseGoodsEntity purchaseGoodsEntity : entity.getPurchaseGoodsList()) {
                        // 更新料号发货数量
                        CbsGoodsPartNoEntity partNoEntity = purchaseGoodsEntity.getPartNoEntity();
                        partNoEntity.setImportCount(partNoEntity.getImportCount().add(purchaseGoodsEntity.getCount()));
                        goodsPartNoDao.update(partNoEntity, new UpdateWrapper<CbsGoodsPartNoEntity>().eq("goods_part_no", partNoEntity.getGoodsPartNo()));
                    }
                    insertMoneyIO(entity, operator);
                    // 如果同步入库，创建一条同步入库单，且状态变更为已审核（防止修改数量）
                    if (entity.getSynStoreIn()) {
                        // 创建
                        CbsStoreGoodsInEntity storeGoodsInEntity = new CbsStoreGoodsInEntity();
                        storeGoodsInEntity.setFkPurchaseId(entity.getId());
                        storeGoodsInEntity.setType(3);
                        resultMap = storeGoodsInService.saveReturnId(storeGoodsInEntity);
                        if (resultMap.containsKey("code") && resultMap.containsKey("msg")) {
                            return resultMap;
                        }
                        if (!resultMap.containsKey("id")) {
                            resultMap = new HashMap<>();
                            resultMap.put("code", 500);
                            resultMap.put("msg", "同步入库单创建失败，请重试，或尝试创建国内收货单后进行分批入库。");
                        }
                        Long storeGoodsInId = (long) resultMap.get("id");
                        // 更新
                        storeGoodsInEntity = storeGoodsInService.detail(storeGoodsInId);
                        storeGoodsInEntity.setStartAddress(entity.getStartAddress());
                        storeGoodsInEntity.setStartContact(entity.getStartContact());
                        storeGoodsInEntity.setStartPhone(entity.getStartPhone());
                        storeGoodsInEntity.setEndAddress(entity.getEndAddress());
                        storeGoodsInEntity.setEndContact(entity.getEndContact());
                        storeGoodsInEntity.setEndPhone(entity.getEndPhone());
                        storeGoodsInEntity.setFkTransitCompanyId(entity.getFkTransitCompanyId());
                        storeGoodsInEntity.setWagonNumber(entity.getWagonNumber());
                        storeGoodsInEntity.setTransFeeMoney(BigDecimal.ZERO);
                        storeGoodsInEntity.setFkStoreId(entity.getFkStoreId());
                        storeGoodsInEntity.setDepartTime(entity.getDepartTime());
                        storeGoodsInEntity.setArrivalTime(entity.getArrivalTime());
                        storeGoodsInEntity.setStoreGoodsInItemEntityList(new ArrayList<>());
                        storeGoodsInEntity.setImgStoreGoodsInReceiptEntityList(new ArrayList<>());
                        storeGoodsInEntity.setOperator(operator);
                        for (CbsPurchaseGoodsEntity purchaseGoodsEntity : entity.getPurchaseGoodsList()) {
                            CbsStoreGoodsInItemEntity storeGoodsInItemEntity = new CbsStoreGoodsInItemEntity();
                            storeGoodsInItemEntity.setFkStoreGoodsInId(storeGoodsInId);
                            storeGoodsInItemEntity.setCreateTime(new Date());
                            storeGoodsInItemEntity.setGoodsInCount(purchaseGoodsEntity.getCount());
                            storeGoodsInItemEntity.setGoodsPartNo(purchaseGoodsEntity.getGoodsPartNo());
                            storeGoodsInEntity.getStoreGoodsInItemEntityList().add(storeGoodsInItemEntity);
                        }
                        for (CbsImgPurchaseReceiptEntity imgPurchaseReceiptEntity : entity.getImgPurchaseReceiptList()) {
                            CbsImgStoreGoodsInReceiptEntity imgStoreGoodsInReceiptEntity = new CbsImgStoreGoodsInReceiptEntity();
                            imgStoreGoodsInReceiptEntity.setFkStoreGoodsInId(storeGoodsInId);
                            imgStoreGoodsInReceiptEntity.setCreateTime(new Date());
                            imgStoreGoodsInReceiptEntity.setImgUrl(imgPurchaseReceiptEntity.getImgUrl());
                            storeGoodsInEntity.getImgStoreGoodsInReceiptEntityList().add(imgStoreGoodsInReceiptEntity);
                        }
                        storeGoodsInService.updateAllById(storeGoodsInEntity);
                        // 提审
                        CbsStoreGoodsInStatusStreamEntity storeGoodsInStatusStreamEntity = new CbsStoreGoodsInStatusStreamEntity();
                        storeGoodsInStatusStreamEntity.setFkStoreGoodsInId(storeGoodsInId);
                        storeGoodsInStatusStreamEntity.setCreateTime(new Date());
                        storeGoodsInStatusStreamEntity.setLastStatus(1);
                        storeGoodsInStatusStreamEntity.setStatus(2);
                        storeGoodsInStatusStreamEntity.setOperator(operator);
                        storeGoodsInStatusStreamEntity.setRemark("国内采购同步入库同步提审");
                        storeGoodsInService.submit(storeGoodsInStatusStreamEntity);
                        // 审核
                        storeGoodsInStatusStreamEntity.setLastStatus(2);
                        storeGoodsInStatusStreamEntity.setStatus(4);
                        storeGoodsInStatusStreamEntity.setRemark("国内采购同步入库同步审核");
                        storeGoodsInService.check(storeGoodsInStatusStreamEntity);
                    }
                }
                resultMap.put("code", 200);
                return resultMap;
            } else {
                resultMap.put("code", 500);
                resultMap.put("msg", "国内收货单状态不合法，请先提交审核。");
                return resultMap;
            }
        } else {
            resultMap.put("code", 500);
            resultMap.put("msg", "没有找到这个国内收货单,请联系管理员。");
            return resultMap;
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        List<Long> goodsIds = purchaseGoodsDao.listIdsByPurchaseIds(ids);
        if (goodsIds != null && !goodsIds.isEmpty()) {
            purchaseGoodsDao.deleteBatchIds(goodsIds);
        }
        removeByIds(Arrays.asList(ids));
    }

    private void insertMoneyIO(CbsPurchaseEntity entity, Long operator) {
        // 进货货值 - 支出
        CbsMoneyOutEntity moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("国内收货单号 - " + entity.getId() + " 国内收货货值");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode("142");
        moneyOutEntity.setType(MoneyEnum.MONEY_TYPE_1001.getCode());
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        BigDecimal money = BigDecimal.ZERO;
        for (CbsPurchaseGoodsEntity purchaseGoodsEntity : entity.getPurchaseGoodsList()) {
            money = money.add(purchaseGoodsEntity.getCount().multiply(purchaseGoodsEntity.getPartNoEntity().getUnitPrice()));
        }
        moneyOutEntity.setMoney(money);
        moneyOutDao.insert(moneyOutEntity);
        // 报关费
        moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("国内收货单号 - " + entity.getId() + " 国内运费");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode("142");
        moneyOutEntity.setType(MoneyEnum.MONEY_TYPE_21.getCode());
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        money = entity.getTransFeeMoney();
        moneyOutEntity.setMoney(money);
        moneyOutDao.insert(moneyOutEntity);
    }

    /**
     * 获取上一个状态
     */
    private CbsPurchaseStatusStreamEntity getLastStatusStreamEntity(Long purchaseId) {
        List<CbsPurchaseStatusStreamEntity> statusStreamEntityList = purchaseStatusStreamDao.selectList(new QueryWrapper<CbsPurchaseStatusStreamEntity>().eq("fk_purchase_id", purchaseId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 判断国内收货单完整性
     */
    private String checkPurchaseComplete(CbsPurchaseEntity entity) {
        if (entity.getPurchaseGoodsList().size() <= 0) {
            return "必须包含国内收货商品";
        } else {
            for (CbsPurchaseGoodsEntity purchaseGoods : entity.getPurchaseGoodsList()) {
                if (purchaseGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || purchaseGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    return "商品数量和价格必须大于0";
                }
            }
        }
        // 起点终点
        if (entity.getStartAddress() == null || entity.getStartContact() == null || entity.getStartPhone() == null) {
            return "请完整填写提货地点、联系人、联系方式";
        }
        if (entity.getEndAddress() == null || entity.getEndContact() == null || entity.getEndPhone() == null) {
            return "请完整填写送达地点、联系人、联系方式";
        }
        // 费用
        if (StringUtils.isEmpty(entity.getInvoiceCode())) {
            return "发票编号不能为空";
        }
        if (entity.getInvoiceMoney() == null || entity.getInvoiceMoney().compareTo(BigDecimal.ZERO) <= 0) {
            return "发票金额必须大于零";
        }
        // 附件
        if (entity.getImgPurchaseInvoiceList() == null || entity.getImgPurchaseInvoiceList().isEmpty()) {
            return "发票附件不能为空";
        }
        if (entity.getImgPurchaseDeliveryOrderList() == null || entity.getImgPurchaseDeliveryOrderList().isEmpty()) {
            return "提货单不能为空";
        }
        // 如果是同步入库，仓库、签收证明不能为空
        if (entity.getSynStoreIn()) {
            if (entity.getFkStoreId() == null) {
                return "同步入库情况下入库仓库不能为空";
            }
            if (entity.getImgPurchaseReceiptList() == null || entity.getImgPurchaseReceiptList().isEmpty()) {
                return "同步入库情况下签收证明不能为空";
            }
        }
        return null;
    }
}