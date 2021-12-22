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
import io.renren.modules.cbs.service.CbsSellService;
import io.renren.modules.cbs.service.CbsStoreGoodsOutSellService;
import io.renren.modules.cbs.service.CbsStoreGoodsOutService;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service("cbsSellService")
public class CbsSellServiceImpl extends ServiceImpl<CbsSellDao, CbsSellEntity> implements CbsSellService {

    private CbsSellDao sellDao;
    private CbsContractDao contractDao;
    private CbsSellStatusStreamDao sellStatusStreamDao;
    private CbsSellGoodsDao sellGoodsDao;
    private CbsGoodsPartNoDao goodsPartNoDao;
    private CbsImgSellInvoiceDao imgSellInvoiceDao;
    private CbsImgSellDeliveryOrderDao imgSellDeliveryOrderDao;
    private CbsImgSellReceiptDao imgSellReceiptDao;
    private CbsImgStoreGoodsOutReceiptDao imgStoreGoodsOutReceiptDao;
    private CbsMoneyInDao moneyInDao;
    private CbsMoneyOutDao moneyOutDao;

    private CbsStoreGoodsOutService storeGoodsOutService;
    private CbsStoreGoodsOutSellService storeGoodsOutSellService;
    private CommonFunction commonFunction;

    public CbsSellServiceImpl(CbsSellDao sellDao,
                              CbsContractDao contractDao,
                              CbsSellStatusStreamDao sellStatusStreamDao,
                              CbsSellGoodsDao sellGoodsDao,
                              CbsGoodsPartNoDao goodsPartNoDao,
                              CbsImgSellInvoiceDao imgSellInvoiceDao,
                              CbsImgSellDeliveryOrderDao imgSellDeliveryOrderDao,
                              CbsImgSellReceiptDao imgSellReceiptDao,
                              CbsImgStoreGoodsOutReceiptDao imgStoreGoodsOutReceiptDao,
                              CbsMoneyInDao moneyInDao,
                              CbsMoneyOutDao moneyOutDao,
                              CommonFunction commonFunction) {
        this.sellDao = sellDao;
        this.contractDao = contractDao;
        this.sellStatusStreamDao = sellStatusStreamDao;
        this.sellGoodsDao = sellGoodsDao;
        this.goodsPartNoDao = goodsPartNoDao;
        this.imgSellInvoiceDao = imgSellInvoiceDao;
        this.imgSellDeliveryOrderDao = imgSellDeliveryOrderDao;
        this.imgSellReceiptDao = imgSellReceiptDao;
        this.imgStoreGoodsOutReceiptDao = imgStoreGoodsOutReceiptDao;
        this.moneyInDao = moneyInDao;
        this.moneyOutDao = moneyOutDao;
        this.commonFunction = commonFunction;
    }

    @Autowired
    public void setStoreGoodsOutService(CbsStoreGoodsOutService storeGoodsOutService) {
        this.storeGoodsOutService = storeGoodsOutService;
    }

    @Autowired
    public void setStoreGoodsOutSellService(CbsStoreGoodsOutSellService storeGoodsOutSellService) {
        this.storeGoodsOutSellService = storeGoodsOutSellService;
    }

    @Override
    public PageUtils queryIndex(CbsSellEntity entity) {
        IPage<CbsSellEntity> page = sellDao.queryIndex(new QueryPage<CbsSellEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public List<CbsSellEntity> queryForStoreOut() {
        return sellDao.queryForStoreOut();
    }

    @Override
    public CbsSellEntity detail(Long id) {
        return sellDao.detail(id);
    }

    @Override
    public Long saveReturnId(CbsSellEntity entity) {
        // 1、无合同对应的国内发货单，直接创建
        // 2、有合同对应的国内发货单
        // ① 合同有信息编辑中的国内发货单（状态1或3），直接跳转到该国内发货单信息编辑对话框
        // ② 合同有关务编辑中的国内发货单（状态4或12），直接跳转到该国内发货单关务编辑对话框
        // ③ 合同有信息审核或关务审核的国内发货单（状态2或11），提示【您有待审核的国内发货单，请耐心等待上级审核完毕】
        // ④ 所有国内发货单都已审核完毕（状态>13），“所有”已国内发货的商品数量 >= 合同商品数量，提示【合同数量已经完成，是否继续创建】
        CbsContractEntity contract = contractDao.simpleDetailWithGoods(entity.getFkContractId());
        List<CbsSellEntity> contractSellList = sellDao.queryByContractIdWithGoods(entity.getFkContractId());
        if (contractSellList == null || contractSellList.isEmpty()) {
            return createSell(entity);
        } else {
            for (CbsSellEntity contractSell : contractSellList) {
                if (Arrays.asList(1, 3).contains(contractSell.getStatus())) {
                    return contractSell.getId();
                } else if (contractSell.getStatus() == 2) {
                    throw new RuntimeException("您有待审核的国内发货单，请耐心审核上级审核完毕");
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
                return createSell(entity);
            } else {
                Map<String, Object> errMsgMap = new HashMap<>();
                errMsgMap.put("code", 300);
                errMsgMap.put("msg", "所有合同中料件需发出数量已经完成，继续发出该合同中的商品可能造成数据错误，请知晓风险后进行后续操作。");
                throw new RuntimeException(new Gson().toJson(errMsgMap));
            }
        }
    }

    /**
     * 具体创建国内发货单方法
     */
    private Long createSell(CbsSellEntity entity) {
        sellDao.insert(entity);
        // 为国内发货单创建第一个状态流
        CbsSellStatusStreamEntity statusStreamEntity = new CbsSellStatusStreamEntity();
        statusStreamEntity.setFkSellId(entity.getId());
        statusStreamEntity.setRemark("创建");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        sellStatusStreamDao.insert(statusStreamEntity);
        return entity.getId();
    }

    @Override
    @Transactional
    public Integer updateAllById(CbsSellEntity entity) {
        sellDao.updateById(entity);
        // 更新商品
        if (entity.getSellGoodsList() != null) {
            List<CbsSellGoodsEntity> oldGoodsList = sellGoodsDao.listBySellId(entity.getId());
            for (CbsSellGoodsEntity oldGoods : oldGoodsList) {
                // 删除旧商品
                sellGoodsDao.deleteById(oldGoods);
            }
            List<CbsSellGoodsEntity> newGoodsList = entity.getSellGoodsList();
            for (CbsSellGoodsEntity newGoods : newGoodsList) {
                // 添加新商品
                sellGoodsDao.insert(newGoods);
            }
        }
        if (entity.getImgSellInvoiceList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgSellInvoiceEntity imgSellInvoiceEntity : entity.getImgSellInvoiceList()) {
                imgList.add(imgSellInvoiceEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_SELL_INVOICE.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_sell_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgSellDeliveryOrderList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgSellDeliveryOrderEntity imgSellDeliveryOrderEntity : entity.getImgSellDeliveryOrderList()) {
                imgList.add(imgSellDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_SELL_DELIVERY_ORDER.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_sell_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        if (entity.getImgSellReceiptList() != null) {
            List<String> imgList = new ArrayList<>();
            for (CbsImgSellReceiptEntity imgSellReceiptEntity : entity.getImgSellReceiptList()) {
                imgList.add(imgSellReceiptEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_SELL_RECEIPT.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_sell_id")
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
    public void submit(CbsSellStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsSellEntity entity = sellDao.detail(statusStreamEntity.getFkSellId());
        String errMsg = checkSellComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CbsSellStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkSellId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                entity.setStatus(2);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(2);

                sellDao.updateById(entity);
                sellStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("国内发货单状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个国内发货单,请联系管理员");
        }
    }

    /**
     * @Description: 提审
     * 可以提审的状态：1、编辑中，3、不通过
     */
    @Override
    @Transactional
    public Integer submitBack(CbsSellStatusStreamEntity statusStreamEntity) {
        CbsSellEntity entity = sellDao.selectById(statusStreamEntity.getFkSellId());
        CbsSellStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkSellId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                sellDao.updateById(entity);
                sellStatusStreamDao.insert(statusStreamEntity);
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
    public void check(CbsSellStatusStreamEntity statusStreamEntity, Long operator) throws RuntimeException {
        CbsSellEntity entity = sellDao.detail(statusStreamEntity.getFkSellId());
        CbsSellStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkSellId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                sellDao.updateById(entity);
                sellStatusStreamDao.insert(statusStreamEntity);
                // 如果最终审核结果通过，插入进口单涉及的收入支出
                if (statusStreamEntity.getStatus() == 4) {
                    for (CbsSellGoodsEntity sellGoodsEntity : entity.getSellGoodsList()) {
                        // 更新料号发货数量
                        CbsGoodsPartNoEntity partNoEntity = sellGoodsEntity.getPartNoEntity();
                        partNoEntity.setExportCount(partNoEntity.getExportCount().add(sellGoodsEntity.getCount()));
                        goodsPartNoDao.update(partNoEntity, new UpdateWrapper<CbsGoodsPartNoEntity>().eq("goods_part_no", partNoEntity.getGoodsPartNo()));
                    }
                    insertMoneyIO(entity, operator);
                    // 如果同步出库，创建一条同步出库单，且状态变更为已审核（防止修改数量）
                    if (entity.getSynStoreOut()) {
                        // 创建
                        CbsStoreGoodsOutEntity storeGoodsOutEntity = new CbsStoreGoodsOutEntity();
                        storeGoodsOutEntity.setFkSellId(entity.getId());
                        storeGoodsOutEntity.setType(3);
                        HashMap<String, Object> resultMap = storeGoodsOutService.saveReturnId(storeGoodsOutEntity);
                        if (resultMap.containsKey("id")) {
                            Long storeGoodsOutId = (long) resultMap.get("id");
                            // 更新
                            storeGoodsOutEntity = storeGoodsOutService.detail(storeGoodsOutId);
                            storeGoodsOutEntity.setStartAddress(entity.getStartAddress());
                            storeGoodsOutEntity.setStartContact(entity.getStartContact());
                            storeGoodsOutEntity.setStartPhone(entity.getStartPhone());
                            storeGoodsOutEntity.setEndAddress(entity.getEndAddress());
                            storeGoodsOutEntity.setEndContact(entity.getEndContact());
                            storeGoodsOutEntity.setEndPhone(entity.getEndPhone());
                            storeGoodsOutEntity.setFkTransitCompanyId(entity.getFkTransitCompanyId());
                            storeGoodsOutEntity.setWagonNumber(entity.getWagonNumber());
                            storeGoodsOutEntity.setTransFeeMoney(BigDecimal.ZERO);
                            storeGoodsOutEntity.setFkStoreId(entity.getFkStoreId());
                            storeGoodsOutEntity.setDepartTime(entity.getDepartTime());
                            storeGoodsOutEntity.setArrivalTime(entity.getArrivalTime());
                            storeGoodsOutEntity.setStoreGoodsOutItemEntityList(new ArrayList<>());
                            storeGoodsOutEntity.setOperator(operator);
                            for (CbsSellGoodsEntity sellGoodsEntity : entity.getSellGoodsList()) {
                                CbsStoreGoodsOutItemEntity storeGoodsOutItemEntity = new CbsStoreGoodsOutItemEntity();
                                storeGoodsOutItemEntity.setFkStoreGoodsOutId(storeGoodsOutId);
                                storeGoodsOutItemEntity.setCreateTime(new Date());
                                storeGoodsOutItemEntity.setGoodsOutCount(sellGoodsEntity.getCount());
                                storeGoodsOutItemEntity.setGoodsPartNo(sellGoodsEntity.getGoodsPartNo());
                                storeGoodsOutItemEntity.setOutFromGoodsPartNo(sellGoodsEntity.getOutFromGoodsPartNo());
                                storeGoodsOutEntity.getStoreGoodsOutItemEntityList().add(storeGoodsOutItemEntity);
                            }
                            storeGoodsOutService.updateAllById(storeGoodsOutEntity);
                            // 提审
                            CbsStoreGoodsOutStatusStreamEntity storeGoodsOutStatusStreamEntity = new CbsStoreGoodsOutStatusStreamEntity();
                            storeGoodsOutStatusStreamEntity.setFkStoreGoodsOutId(storeGoodsOutId);
                            storeGoodsOutStatusStreamEntity.setCreateTime(new Date());
                            storeGoodsOutStatusStreamEntity.setLastStatus(1);
                            storeGoodsOutStatusStreamEntity.setStatus(2);
                            storeGoodsOutStatusStreamEntity.setOperator(operator);
                            storeGoodsOutStatusStreamEntity.setRemark("国内发货同步出库同步提审");
                            storeGoodsOutService.submit(storeGoodsOutStatusStreamEntity);
                            // 审核
                            storeGoodsOutStatusStreamEntity.setLastStatus(2);
                            storeGoodsOutStatusStreamEntity.setStatus(4);
                            storeGoodsOutStatusStreamEntity.setRemark("国内发货同步出库同步审核");
                            storeGoodsOutService.check(storeGoodsOutStatusStreamEntity);
                        }
                    }
                }
            } else {
                throw new RuntimeException("国内发货单状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个国内发货单,请联系管理员");
        }
    }

    /**
     * （补充）更新签收证明
     */
    @Override
    @Transactional
    public void updateReceipt(Long sellId, List<CbsImgSellReceiptEntity> imgReceiptList, Long operator) {
        if (imgReceiptList != null) {
            // 更新签收证明
            imgSellReceiptDao.delete(new QueryWrapper<CbsImgSellReceiptEntity>().eq("fk_sell_id", sellId));
            for (CbsImgSellReceiptEntity imgSellReceiptEntity : imgReceiptList) {
                imgSellReceiptDao.insert(imgSellReceiptEntity);
            }
            // 同步出库情况下，更新出库单签收证明
            CbsSellEntity sellEntity = sellDao.selectById(sellId);
            if (sellEntity.getSynStoreOut()) {
                CbsStoreGoodsOutSellEntity storeGoodsOutSellEntity = storeGoodsOutSellService.selectBySellId(sellId);
                if (storeGoodsOutSellEntity != null && storeGoodsOutSellEntity.getGoodsOutEntity() != null) {
                    imgStoreGoodsOutReceiptDao.delete(new QueryWrapper<CbsImgStoreGoodsOutReceiptEntity>().eq("fk_store_goods_out_id", storeGoodsOutSellEntity.getGoodsOutEntity().getId()));
                    for (CbsImgSellReceiptEntity imgSellReceiptEntity : imgReceiptList) {
                        CbsImgStoreGoodsOutReceiptEntity storeGoodsOutReceiptEntity = getStoreGoodsOutReceiptFromSellReceipt(imgSellReceiptEntity);
                        storeGoodsOutReceiptEntity.setFkStoreGoodsOutId(storeGoodsOutSellEntity.getGoodsOutEntity().getId());
                        imgStoreGoodsOutReceiptDao.insert(storeGoodsOutReceiptEntity);
                    }
                }
            }
            // 更新状态
            CbsSellStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(sellId);
            if (lastStatusStreamEntity != null) {
                CbsSellStatusStreamEntity statusStreamEntity = new CbsSellStatusStreamEntity();
                statusStreamEntity.setFkSellId(sellId);
                statusStreamEntity.setStatus(5);
                statusStreamEntity.setOperator(operator);
                statusStreamEntity.setRemark(lastStatusStreamEntity.getStatus() == 5 ? "更新签收证明" : "补充签收证明");
                statusStreamEntity.setCreateTime(new Date());

                sellEntity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                sellDao.updateById(sellEntity);
                sellStatusStreamDao.insert(statusStreamEntity);
            }
        }

    }

    /**
     * @Description: 审核签收证明
     * 可以审核的状态：5、签收证明已上传
     */
    @Override
    @Transactional
    public void checkReceipt(Long sellId, Long operator) throws RuntimeException {
        CbsSellEntity entity = sellDao.selectById(sellId);
        CbsSellStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(sellId);
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 5) {
                CbsSellStatusStreamEntity statusStreamEntity = new CbsSellStatusStreamEntity();
                statusStreamEntity.setFkSellId(sellId);
                statusStreamEntity.setStatus(6);
                statusStreamEntity.setOperator(operator);
                statusStreamEntity.setRemark("已确核");
                statusStreamEntity.setCreateTime(new Date());

                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                sellDao.updateById(entity);
                sellStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("国内发货单状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个国内发货单,请联系管理员");
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        List<Long> goodsIds = sellGoodsDao.listIdsBySellIds(ids);
        if (goodsIds != null && !goodsIds.isEmpty()) {
            sellGoodsDao.deleteBatchIds(goodsIds);
        }
        removeByIds(Arrays.asList(ids));
    }

    /**
     * 创建财务流水
     */
    private void insertMoneyIO(CbsSellEntity entity, Long operator) {
        // 国内发货货值 - 收入
        CbsMoneyInEntity moneyInEntity = new CbsMoneyInEntity();
        moneyInEntity.setTitle("国内发货单号 - " + entity.getId() + " 国内发货货值");
        moneyInEntity.setFkContractId(entity.getFkContractId());
        moneyInEntity.setCurrencyCode(entity.getContractEntity().getCurrencyCode());
        moneyInEntity.setType(MoneyEnum.MONEY_TYPE_1002.getCode());
        moneyInEntity.setStatus(1);
        moneyInEntity.setOperator(operator);
        BigDecimal money = BigDecimal.ZERO;
        for (CbsSellGoodsEntity sellGoodsEntity : entity.getSellGoodsList()) {
            money = money.add(sellGoodsEntity.getCount().multiply(sellGoodsEntity.getPartNoEntity().getUnitPrice()));
        }
        moneyInEntity.setMoney(money);
        moneyInDao.insert(moneyInEntity);
        // 国内运费
        CbsMoneyOutEntity moneyOutEntity = new CbsMoneyOutEntity();
        moneyOutEntity.setTitle("国内发货单号 - " + entity.getId() + " 国内运费");
        moneyOutEntity.setFkContractId(entity.getFkContractId());
        moneyOutEntity.setCurrencyCode("142");
        moneyOutEntity.setType(MoneyEnum.MONEY_TYPE_21.getCode());
        moneyOutEntity.setStatus(1);
        moneyOutEntity.setOperator(operator);
        moneyOutEntity.setMoney(entity.getTransFeeMoney());
        moneyOutDao.insert(moneyOutEntity);
    }

    /**
     * 获取上一状态流
     */
    private CbsSellStatusStreamEntity getLastStatusStreamEntity(Long sellId) {
        List<CbsSellStatusStreamEntity> statusStreamEntityList = sellStatusStreamDao.selectList(new QueryWrapper<CbsSellStatusStreamEntity>().eq("fk_sell_id", sellId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 国内发货单签收证明转化成出库单签收证明
     */
    private CbsImgStoreGoodsOutReceiptEntity getStoreGoodsOutReceiptFromSellReceipt(CbsImgSellReceiptEntity sellReceiptEntity) {
        CbsImgStoreGoodsOutReceiptEntity storeGoodsOutReceiptEntity = new CbsImgStoreGoodsOutReceiptEntity();
        storeGoodsOutReceiptEntity.setImgUrl(sellReceiptEntity.getImgUrl());
        storeGoodsOutReceiptEntity.setCreateTime(sellReceiptEntity.getCreateTime());
        storeGoodsOutReceiptEntity.setUpdateTime(sellReceiptEntity.getUpdateTime());
        storeGoodsOutReceiptEntity.setIsValid(sellReceiptEntity.getIsValid());
        storeGoodsOutReceiptEntity.setSort(sellReceiptEntity.getSort());
        return storeGoodsOutReceiptEntity;
    }

    /**
     * 判断国内发货单完整性
     */
    private String checkSellComplete(CbsSellEntity entity) {
        if (entity.getSellGoodsList().size() <= 0) {
            return "必须包含国内发货商品";
        } else {
            for (CbsSellGoodsEntity sellGoods : entity.getSellGoodsList()) {
                if (sellGoods.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) <= 0 || sellGoods.getPartNoEntity().getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    return "商品数量和价格必须大于0";
                }
            }
        }
        // 费用
        if (StringUtils.isEmpty(entity.getInvoiceCode())) {
            return "发票编号不能为空";
        }
        if (entity.getInvoiceMoney() == null || entity.getInvoiceMoney().compareTo(BigDecimal.ZERO) <= 0) {
            return "发票金额必须大于零";
        }
        // 附件
        if (entity.getImgSellInvoiceList() == null || entity.getImgSellInvoiceList().isEmpty()) {
            return "发票附件不能为空";
        }
        // 如果不是同步出库，仓库等选项不加入提审和审核预判
        if (entity.getSynStoreOut()) {
            // 起点终点
            if (entity.getStartAddress() == null || entity.getStartContact() == null || entity.getStartPhone() == null) {
                return "请完整填写提货地点、联系人、联系方式";
            }
            if (entity.getEndAddress() == null || entity.getEndContact() == null || entity.getEndPhone() == null) {
                return "请完整填写送达地点、联系人、联系方式";
            }
            if (entity.getImgSellDeliveryOrderList() == null || entity.getImgSellDeliveryOrderList().isEmpty()) {
                return "提货单不能为空";
            }
        }
        return null;
    }
}