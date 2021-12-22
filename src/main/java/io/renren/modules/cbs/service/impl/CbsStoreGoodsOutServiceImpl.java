package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.enumeration.DocumentControlEnum;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.QueryPage;
import io.renren.common.validator.Warehousing;
import io.renren.modules.cbs.dao.*;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.cbs.service.CbsStoreGoodsOutService;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;


@Service("cbsStoreGoodsOutService")
public class CbsStoreGoodsOutServiceImpl extends ServiceImpl<CbsStoreGoodsOutDao, CbsStoreGoodsOutEntity> implements CbsStoreGoodsOutService {

    private CbsStoreGoodsDao storeGoodsDao;
    private CbsProduceGoodsDao produceGoodsDao;
    private CbsExportGoodsDao exportGoodsDao;
    private CbsSellGoodsDao sellGoodsDao;
    private CbsStoreGoodsOutDao storeGoodsOutDao;
    private CbsStoreGoodsOutProduceDao storeGoodsOutProduceDao;
    private CbsStoreGoodsOutExportDao storeGoodsOutExportDao;
    private CbsStoreGoodsOutSellDao storeGoodsOutSellDao;
    private CbsStoreGoodsOutItemDao storeGoodsOutItemDao;
    private CbsImgStoreGoodsOutDeliveryOrderDao imgStoreGoodsOutDeliveryOrderDao;
    private CbsImgStoreGoodsOutReceiptDao imgStoreGoodsOutReceiptDao;
    private CbsStoreGoodsOutStatusStreamDao storeGoodsOutStatusStreamDao;

    private CbsGoodsPartNoDao goodsPartNoDao;
    private CbsMoneyOutDao moneyOutDao;

    private CbsProduceDao produceDao;
    private CbsExportDao exportDao;
    private CbsSellDao sellDao;

    private CommonFunction commonFunction;

    public CbsStoreGoodsOutServiceImpl(CommonFunction commonFunction) {
        this.commonFunction = commonFunction;
    }

    @Autowired
    public void setStoreGoodsDao(CbsStoreGoodsDao storeGoodsDao) {
        this.storeGoodsDao = storeGoodsDao;
    }

    @Autowired
    public void setProduceGoodsDao(CbsProduceGoodsDao produceGoodsDao) {
        this.produceGoodsDao = produceGoodsDao;
    }

    @Autowired
    public void setExportGoodsDao(CbsExportGoodsDao exportGoodsDao) {
        this.exportGoodsDao = exportGoodsDao;
    }

    @Autowired
    public void setSellGoodsDao(CbsSellGoodsDao sellGoodsDao) {
        this.sellGoodsDao = sellGoodsDao;
    }

    @Autowired
    public void setStoreGoodsOutDao(CbsStoreGoodsOutDao storeGoodsOutDao) {
        this.storeGoodsOutDao = storeGoodsOutDao;
    }

    @Autowired
    public void setImgStoreGoodsOutDeliveryOrderDao(CbsImgStoreGoodsOutDeliveryOrderDao imgStoreGoodsOutDeliveryOrderDao) {
        this.imgStoreGoodsOutDeliveryOrderDao = imgStoreGoodsOutDeliveryOrderDao;
    }

    @Autowired
    public void setImgStoreGoodsOutReceiptDao(CbsImgStoreGoodsOutReceiptDao imgStoreGoodsOutReceiptDao) {
        this.imgStoreGoodsOutReceiptDao = imgStoreGoodsOutReceiptDao;
    }

    @Autowired
    public void setStoreGoodsOutExportDao(CbsStoreGoodsOutExportDao storeGoodsOutExportDao) {
        this.storeGoodsOutExportDao = storeGoodsOutExportDao;
    }

    @Autowired
    public void setStoreGoodsOutSellDao(CbsStoreGoodsOutSellDao storeGoodsOutSellDao) {
        this.storeGoodsOutSellDao = storeGoodsOutSellDao;
    }

    @Autowired
    public void setStoreGoodsOutItemDao(CbsStoreGoodsOutItemDao storeGoodsOutItemDao) {
        this.storeGoodsOutItemDao = storeGoodsOutItemDao;
    }

    @Autowired
    public void setStoreGoodsOutProduceDao(CbsStoreGoodsOutProduceDao storeGoodsOutProduceDao) {
        this.storeGoodsOutProduceDao = storeGoodsOutProduceDao;
    }

    @Autowired
    public void setStoreGoodsOutStatusStreamDao(CbsStoreGoodsOutStatusStreamDao storeGoodsOutStatusStreamDao) {
        this.storeGoodsOutStatusStreamDao = storeGoodsOutStatusStreamDao;
    }

    @Autowired
    public void setGoodsPartNoDao(CbsGoodsPartNoDao goodsPartNoDao) {
        this.goodsPartNoDao = goodsPartNoDao;
    }

    @Autowired
    public void setMoneyOutDao(CbsMoneyOutDao moneyOutDao) {
        this.moneyOutDao = moneyOutDao;
    }

    @Autowired
    public void setSellDao(CbsSellDao sellDao) {
        this.sellDao = sellDao;
    }

    @Autowired
    public void setProduceDao(CbsProduceDao produceDao) {
        this.produceDao = produceDao;
    }

    @Autowired
    public void setExportDao(CbsExportDao exportDao) {
        this.exportDao = exportDao;
    }

    @Override
    @Transactional
    public HashMap<String, Object> saveReturnId(CbsStoreGoodsOutEntity entity) {
        // 先检查库存和完成情况
        HashMap<String, Object> resultMap = checkBeforeCreate(entity);
        if (resultMap.containsKey("code")) {
            // code: 200、检查到有编辑中的出库单，400、有错误前端显示warning
            return resultMap;
        }
        entity.setStatus(1);
        storeGoodsOutDao.insert(entity);
        if (entity.getType() == 1) {
            CbsStoreGoodsOutProduceEntity goodsOutProduceEntity = new CbsStoreGoodsOutProduceEntity();
            goodsOutProduceEntity.setFkProduceId(entity.getFkProduceId());
            goodsOutProduceEntity.setFkStoreGoodsOutId(entity.getId());
            storeGoodsOutProduceDao.insert(goodsOutProduceEntity);
        } else if (entity.getType() == 2) {
            CbsExportEntity exportEntity = exportDao.detail(entity.getFkExportId());
            for (CbsImgExportDeliveryOrderEntity deliveryOrderEntity : exportEntity.getImgExportDeliveryOrderList()) {
                CbsImgStoreGoodsOutDeliveryOrderEntity storeGoodsOutDeliveryOrderEntity = deliveryOrderFromExport(deliveryOrderEntity);
                storeGoodsOutDeliveryOrderEntity.setFkStoreGoodsOutId(entity.getId());
                imgStoreGoodsOutDeliveryOrderDao.insert(storeGoodsOutDeliveryOrderEntity);
            }
            // 判断是否已有该出口单的出库单存在，检查创建规则：
            // 有出库单且有编辑中的出库单，则编辑该出库单，否则创建
            List<CbsStoreGoodsOutExportEntity> goodsOutExportEntityList = storeGoodsOutExportDao.selectList(new QueryWrapper<CbsStoreGoodsOutExportEntity>().eq("fk_export_id", entity.getFkExportId()));
            for (CbsStoreGoodsOutExportEntity goodsOutExportEntity : goodsOutExportEntityList) {
                if (goodsOutExportEntity.getGoodsOutEntity() != null && goodsOutExportEntity.getGoodsOutEntity().getStatus() < 4) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    goodsOutExportEntity.setGoodsOutEntity(storeGoodsOutDao.selectById(goodsOutExportEntity.getFkStoreGoodsOutId()));
                    resultMap.put("code", 200);
                    resultMap.put("id", goodsOutExportEntity.getGoodsOutEntity().getId());
                    return resultMap;
                }
            }
            CbsStoreGoodsOutExportEntity goodsOutExportEntity = new CbsStoreGoodsOutExportEntity();
            goodsOutExportEntity.setFkExportId(entity.getFkExportId());
            goodsOutExportEntity.setFkStoreGoodsOutId(entity.getId());
            storeGoodsOutExportDao.insert(goodsOutExportEntity);
        } else if (entity.getType() == 3) {
            CbsSellEntity sellEntity = sellDao.detail(entity.getFkSellId());
            for (CbsImgSellDeliveryOrderEntity deliveryOrderEntity : sellEntity.getImgSellDeliveryOrderList()) {
                CbsImgStoreGoodsOutDeliveryOrderEntity storeGoodsOutDeliveryOrderEntity = deliveryOrderFromSell(deliveryOrderEntity);
                storeGoodsOutDeliveryOrderEntity.setFkStoreGoodsOutId(entity.getId());
                imgStoreGoodsOutDeliveryOrderDao.insert(storeGoodsOutDeliveryOrderEntity);
            }
            // 判断是否已有该出口单的出库单存在，检查创建规则：
            // 有出库单且有编辑中的出库单，则编辑该出库单，否则创建
            List<CbsStoreGoodsOutSellEntity> goodsOutSellEntityList = storeGoodsOutSellDao.selectList(new QueryWrapper<CbsStoreGoodsOutSellEntity>().eq("fk_sell_id", entity.getFkSellId()));
            for (CbsStoreGoodsOutSellEntity goodsOutSellEntity : goodsOutSellEntityList) {
                if (goodsOutSellEntity.getGoodsOutEntity() != null && goodsOutSellEntity.getGoodsOutEntity().getStatus() < 4) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    goodsOutSellEntity.setGoodsOutEntity(storeGoodsOutDao.selectById(goodsOutSellEntity.getFkStoreGoodsOutId()));
                    resultMap.put("code", 200);
                    resultMap.put("id", goodsOutSellEntity.getGoodsOutEntity().getId());
                    return resultMap;
                }
            }
            CbsStoreGoodsOutSellEntity goodsOutSellEntity = new CbsStoreGoodsOutSellEntity();
            goodsOutSellEntity.setFkSellId(entity.getFkSellId());
            goodsOutSellEntity.setFkStoreGoodsOutId(entity.getId());
            storeGoodsOutSellDao.insert(goodsOutSellEntity);
        }
        // 为入库创建第一个状态流
        CbsStoreGoodsOutStatusStreamEntity statusStreamEntity = new CbsStoreGoodsOutStatusStreamEntity();
        statusStreamEntity.setFkStoreGoodsOutId(entity.getId());
        statusStreamEntity.setRemark("创建");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        storeGoodsOutStatusStreamDao.insert(statusStreamEntity);
        resultMap.put("code", 200);
        resultMap.put("id", entity.getId());
        return resultMap;
    }

    @Override
    @Transactional
    public Integer updateAllById(CbsStoreGoodsOutEntity entity) {
        storeGoodsOutDao.updateById(entity);
        Long contractId = 0L;
        // entity.getId 查询  cbs_store_goods_out_export 的 id ，查询 cbs_produce 的 contractId
        CbsStoreGoodsOutExportEntity cbsStoreGoodsOutExportEntity = storeGoodsOutExportDao.selectOne(new QueryWrapper<CbsStoreGoodsOutExportEntity>().eq("fk_store_goods_out_id", entity.getId()));
        if (cbsStoreGoodsOutExportEntity != null) {
            CbsExportEntity cbsExportEntity = exportDao.selectById(cbsStoreGoodsOutExportEntity.getFkExportId());
            if (cbsExportEntity != null) {
                contractId = cbsExportEntity.getFkContractId();
            }
        }
        // entity.getId 查询  cbs_store_goods_out_produce 的 id ，查询 cbs_purchase 的 contractId
        CbsStoreGoodsOutProduceEntity cbsStoreGoodsOutProduceEntity = storeGoodsOutProduceDao.selectOne(new QueryWrapper<CbsStoreGoodsOutProduceEntity>().eq("fk_store_goods_out_id", entity.getId()));
        if (cbsStoreGoodsOutProduceEntity != null) {
            CbsProduceEntity cbsProduceEntity = produceDao.selectById(cbsStoreGoodsOutProduceEntity.getFkProduceId());
            if (cbsProduceEntity != null) {
                contractId = cbsProduceEntity.getFkContractId();
            }
        }
        // entity.getId 查询  cbs_store_goods_out_sell 的 id ，查询 cbs_import 的 contractId
        CbsStoreGoodsOutSellEntity cbsStoreGoodsOutSellEntity = storeGoodsOutSellDao.selectOne(new QueryWrapper<CbsStoreGoodsOutSellEntity>().eq("fk_store_goods_out_id", entity.getId()));
        if (cbsStoreGoodsOutSellEntity != null) {
            CbsSellEntity cbsSellEntity = sellDao.selectById(cbsStoreGoodsOutSellEntity.getFkSellId());
            if (cbsSellEntity != null) {
                contractId = cbsSellEntity.getFkContractId();
            }
        }


        storeGoodsOutItemDao.delete(new QueryWrapper<CbsStoreGoodsOutItemEntity>().eq("fk_store_goods_out_id", entity.getId()));
        if (entity.getStoreGoodsOutItemEntityList() != null) {
            for (CbsStoreGoodsOutItemEntity storeGoodsOutItemEntity : entity.getStoreGoodsOutItemEntityList()) {
                storeGoodsOutItemEntity.setFkStoreGoodsOutId(entity.getId());
                storeGoodsOutItemDao.insert(storeGoodsOutItemEntity);
            }
        }

        if (entity.getImgStoreGoodsOutDeliveryOrderEntityList() != null) {
            /*imgStoreGoodsOutDeliveryOrderDao.delete(new QueryWrapper<CbsImgStoreGoodsOutDeliveryOrderEntity>().eq("fk_store_goods_out_id", entity.getId()));
            for (CbsImgStoreGoodsOutDeliveryOrderEntity imgStoreGoodsOutDeliveryOrderEntity : entity.getImgStoreGoodsOutDeliveryOrderEntityList()) {
                imgStoreGoodsOutDeliveryOrderEntity.setFkStoreGoodsOutId(entity.getId());
                imgStoreGoodsOutDeliveryOrderDao.insert(imgStoreGoodsOutDeliveryOrderEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgStoreGoodsOutDeliveryOrderEntity imgStoreGoodsOutDeliveryOrderEntity : entity.getImgStoreGoodsOutDeliveryOrderEntityList()) {
                imgList.add(imgStoreGoodsOutDeliveryOrderEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_STORE_GOODS_OUT_DELIVERY_ORDER.getCode())
                    .contractId(contractId)
                    .field("fk_store_goods_out_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }

        if (entity.getImgStoreGoodsOutReceiptEntityList() != null) {
            /*imgStoreGoodsOutReceiptDao.delete(new QueryWrapper<CbsImgStoreGoodsOutReceiptEntity>().eq("fk_store_goods_out_id", entity.getId()));
            for (CbsImgStoreGoodsOutReceiptEntity imgStoreGoodsOutReceiptEntity : entity.getImgStoreGoodsOutReceiptEntityList()) {
                imgStoreGoodsOutReceiptEntity.setFkStoreGoodsOutId(entity.getId());
                imgStoreGoodsOutReceiptDao.insert(imgStoreGoodsOutReceiptEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgStoreGoodsOutReceiptEntity imgStoreGoodsOutReceiptEntity : entity.getImgStoreGoodsOutReceiptEntityList()) {
                imgList.add(imgStoreGoodsOutReceiptEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_STORE_GOODS_OUT_RECEIPT.getCode())
                    .contractId(contractId)
                    .field("fk_store_goods_out_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
        }
        return 1;
    }

    @Override
    public PageUtils queryIndex(CbsStoreGoodsOutEntity entity) {
        IPage<CbsStoreGoodsOutEntity> page = storeGoodsOutDao.queryIndex(new QueryPage<CbsStoreGoodsOutEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsStoreGoodsOutEntity> page = this.page(
                new Query<CbsStoreGoodsOutEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public CbsStoreGoodsOutEntity detail(Long id) {
        return storeGoodsOutDao.detail(id);
    }

    /**
     * 提审
     */
    @Override
    @Transactional
    public void submit(CbsStoreGoodsOutStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsStoreGoodsOutEntity entity = storeGoodsOutDao.detail(statusStreamEntity.getFkStoreGoodsOutId());
        String errMsg = checkStoreGoodsOutComplete(entity);
        if (errMsg != null) throw new RuntimeException(errMsg);

        CbsStoreGoodsOutStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsOutId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                try {
                    entity.setStatus(2);
                    statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                    statusStreamEntity.setStatus(2);

                    storeGoodsOutDao.updateById(entity);
                    storeGoodsOutStatusStreamDao.insert(statusStreamEntity);
                } catch (Exception e) {
                    throw new RuntimeException("未知异常，请联系管理员");
                }
            } else {
                throw new RuntimeException("出库单状态不合法，编辑中和不通过状态才能提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个出库单,请联系管理员");
        }
    }

    /**
     * @Description: 提审
     * 可以提审的状态：1、编辑中，3、不通过
     */
    @Override
    @Transactional
    public Integer submitBack(CbsStoreGoodsOutStatusStreamEntity statusStreamEntity) {
        CbsStoreGoodsOutEntity entity = storeGoodsOutDao.selectById(statusStreamEntity.getFkStoreGoodsOutId());
        CbsStoreGoodsOutStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsOutId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                try {
                    entity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                    statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                    storeGoodsOutDao.updateById(entity);
                    storeGoodsOutStatusStreamDao.insert(statusStreamEntity);
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
    public void check(CbsStoreGoodsOutStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsStoreGoodsOutEntity entity = storeGoodsOutDao.detail(statusStreamEntity.getFkStoreGoodsOutId());
        CbsStoreGoodsOutStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkStoreGoodsOutId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                // 变更状态
                entity.setStatus(statusStreamEntity.getStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                storeGoodsOutDao.updateById(entity);
                storeGoodsOutStatusStreamDao.insert(statusStreamEntity);
                // 状态变为已审核后，导入各类数据
                if (statusStreamEntity.getStatus() == 4) {
                    // 变更库存数量、变更出库源料号实体数量
                    for (CbsStoreGoodsOutItemEntity goodsOutItemEntity : entity.getStoreGoodsOutItemEntityList()) {
                        // 库存源料号实体，出库数量加、总数量减
                        CbsGoodsPartNoEntity outFromPartNoEntity = goodsPartNoDao.selectByCode(goodsOutItemEntity.getOutFromGoodsPartNo());
                        outFromPartNoEntity.setStoreOutCount(outFromPartNoEntity.getStoreOutCount().add(goodsOutItemEntity.getGoodsOutCount()));
                        outFromPartNoEntity.setStoreCount(outFromPartNoEntity.getStoreCount().subtract(goodsOutItemEntity.getGoodsOutCount()));
                        goodsPartNoDao.updateById(outFromPartNoEntity);
                        if (!goodsOutItemEntity.getOutFromGoodsPartNo().equals(goodsOutItemEntity.getGoodsPartNo())) {
                            // 被输入料号实体，入库量增加，总量增加，这种情况只在生产情况下不发生
                            CbsGoodsPartNoEntity outToPartNoEntity = goodsPartNoDao.selectByCode(goodsOutItemEntity.getGoodsPartNo());
                            outToPartNoEntity.setStoreInCount(outToPartNoEntity.getStoreInCount().add(goodsOutItemEntity.getGoodsOutCount()));
                            outToPartNoEntity.setStoreCount(outToPartNoEntity.getStoreCount().add(goodsOutItemEntity.getGoodsOutCount()));
                            goodsPartNoDao.updateById(outToPartNoEntity);
                        }

                        // 库存数量减
                        CbsStoreGoodsEntity storeGoodsEntity = storeGoodsDao.selectOne(new QueryWrapper<CbsStoreGoodsEntity>().eq("goods_part_no", goodsOutItemEntity.getOutFromGoodsPartNo()));
                        if (storeGoodsEntity != null) {
                            storeGoodsEntity.setGoodsStoreCount(storeGoodsEntity.getGoodsStoreCount().subtract(goodsOutItemEntity.getGoodsOutCount()));
                            storeGoodsDao.updateById(storeGoodsEntity);
                        }
                        // 加入出口、生产、国内发货商品表各类数量
                        if (entity.getType() == 1) {
                            // 如果是原料出库用于生产，生产缓存加
                            QueryWrapper<CbsProduceGoodsEntity> qw = new QueryWrapper<>();
                            qw.eq("goods_part_no", goodsOutItemEntity.getGoodsPartNo());
                            qw.eq("fk_produce_id", entity.getFkProduceId());
                            CbsProduceGoodsEntity produceGoodsEntity = produceGoodsDao.selectOne(qw);
                            produceGoodsEntity.setTotalCount(produceGoodsEntity.getTotalCount().add(goodsOutItemEntity.getGoodsOutCount()));
                            produceGoodsEntity.setStoreCount(produceGoodsEntity.getStoreCount().add(goodsOutItemEntity.getGoodsOutCount()));
                            produceGoodsDao.updateById(produceGoodsEntity);
                        } else if (entity.getType() == 2) {
                            // 如果是出口出库，已出库缓存增加
                            QueryWrapper<CbsExportGoodsEntity> qw = new QueryWrapper<>();
                            qw.eq("goods_part_no", goodsOutItemEntity.getGoodsPartNo());
                            qw.eq("fk_export_id", entity.getFkExportId());
                            CbsExportGoodsEntity exportGoodsEntity = exportGoodsDao.selectOne(qw);
                            exportGoodsEntity.setStoreOutCount(exportGoodsEntity.getStoreOutCount().add(goodsOutItemEntity.getGoodsOutCount()));
                            exportGoodsDao.updateById(exportGoodsEntity);
                        } else if (entity.getType() == 3) {
                            // 如果是国内发货出库，已出库缓存增加
                            QueryWrapper<CbsSellGoodsEntity> qw = new QueryWrapper<>();
                            qw.eq("goods_part_no", goodsOutItemEntity.getGoodsPartNo());
                            qw.eq("fk_sell_id", entity.getFkSellId());
                            CbsSellGoodsEntity sellGoodsEntity = sellGoodsDao.selectOne(qw);
                            sellGoodsEntity.setStoreOutCount(sellGoodsEntity.getStoreOutCount().add(goodsOutItemEntity.getGoodsOutCount()));
                            sellGoodsDao.updateById(sellGoodsEntity);
                        }
                    }
                    // 如果是出口单或国内发货单，所有所需商品数量均已出库，对应出口单或国内发货单状态自动变更14、5
                    if (entity.getType() == 2) {
                        boolean allCountComplete = true;
                        CbsExportEntity exportEntity = exportDao.detail(entity.getFkExportId());
                        for (CbsExportGoodsEntity exportGoodsEntity : exportEntity.getExportGoodsList()) {
                            if (exportGoodsEntity.getStoreOutCount().compareTo(exportGoodsEntity.getCount()) < 0) {
                                allCountComplete = false;
                                break;
                            }
                        }
                        if (allCountComplete) {
                            exportEntity.setStatus(14);
                            exportDao.updateById(exportEntity);
                        }
                    } else if (entity.getType() == 3) {
                        boolean allCountComplete = true;
                        CbsSellEntity sellEntity = sellDao.detail(entity.getFkSellId());
                        for (CbsSellGoodsEntity sellGoodsEntity : sellEntity.getSellGoodsList()) {
                            if (sellGoodsEntity.getStoreOutCount().compareTo(sellGoodsEntity.getCount()) < 0) {
                                allCountComplete = false;
                                break;
                            }
                        }
                        if (allCountComplete) {
                            sellEntity.setStatus(5);
                            sellDao.updateById(sellEntity);
                        }
                    }

                    // 国内运费支出
                    if (entity.getTransFeeMoney().compareTo(BigDecimal.ZERO) > 0) {
                        CbsMoneyOutEntity moneyOutEntity = new CbsMoneyOutEntity();
                        moneyOutEntity.setTitle("出库单号 - " + entity.getId() + " 国内运费");
                        // 1、生产出库 2、出口出库 3、国内发货出库
                        switch (entity.getType()) {
                            case 1:
                                moneyOutEntity.setFkContractId(entity.getProduceEntity().getFkContractId());
                                break;
                            case 2:
                                moneyOutEntity.setFkContractId(entity.getExportEntity().getFkContractId());
                                break;
                            case 3:
                                moneyOutEntity.setFkContractId(entity.getSellEntity().getFkContractId());
                                break;
                        }
                        moneyOutEntity.setCurrencyCode("142"/*人民币*/);
                        moneyOutEntity.setType(21);
                        moneyOutEntity.setStatus(1);
                        moneyOutEntity.setOperator(statusStreamEntity.getOperator());
                        moneyOutEntity.setMoney(entity.getTransFeeMoney());
                        moneyOutDao.insert(moneyOutEntity);
                    }
                }
            } else {
                throw new RuntimeException("出库单状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个出库单,请联系管理员");
        }

    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            CbsStoreGoodsOutEntity entity = storeGoodsOutDao.detail(id);
            if (entity != null) {
                // status小于4才能删除
                Warehousing.isStatusMoreThanThe(entity.getStatus(), 4, "出库单审核通过后无法删除");
                // 删除其下商品
                for (CbsStoreGoodsOutItemEntity itemEntity : entity.getStoreGoodsOutItemEntityList()) {
                    storeGoodsOutItemDao.deleteById(itemEntity.getId());
                }
                // 删除关联子表行
                switch (entity.getType()) {
                    case 1:
                        storeGoodsOutProduceDao.delete(new QueryWrapper<CbsStoreGoodsOutProduceEntity>().eq("fk_store_goods_out_id", id));
                        break;
                    case 2:
                        storeGoodsOutExportDao.delete(new QueryWrapper<CbsStoreGoodsOutExportEntity>().eq("fk_store_goods_out_id", id));
                        break;
                    case 3:
                        storeGoodsOutSellDao.delete(new QueryWrapper<CbsStoreGoodsOutSellEntity>().eq("fk_store_goods_out_id", id));
                        break;
                }
                // 删除出库单
                storeGoodsOutDao.deleteById(id);
            }
        }
    }

    /**
     * 创建前检查有无库存
     */
    private HashMap<String, Object> checkBeforeCreate(CbsStoreGoodsOutEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (entity.getType() == 1) {
            // 对应生产原料出库
            // 判断有无正在编辑中的出库单，有则返回id
            CbsProduceEntity produceEntity = produceDao.detail(entity.getFkProduceId());
            for (CbsStoreGoodsOutEntity storeGoodsOutEntity : produceEntity.getStoreGoodsOutEntityList()) {
                if (storeGoodsOutEntity.getStatus() == 1 || storeGoodsOutEntity.getStatus() == 3) {
                    resultMap.put("code", 200);
                    resultMap.put("id", storeGoodsOutEntity.getId());
                    return resultMap;
                } else if (storeGoodsOutEntity.getStatus() == 2) {
                    resultMap.put("code", 400);
                    resultMap.put("msg", "该生产单有待审核的原料出库单，请耐心等待上级审核完毕。");
                    return resultMap;
                }
            }
            // 检查原料库存情况
            List<CbsStoreGoodsEntity> storeGoodsList = storeGoodsDao.listForOutMaterialToProduce(entity.getFkProduceId());
            if (storeGoodsList == null || storeGoodsList.isEmpty()) {
                resultMap.put("code", 400);
                resultMap.put("msg", "相关原料库存不足，无法创建出库单，请补货入库。");
                return resultMap;
            }
            // 检查原料计划数量已全部调拨
            boolean allCountComplete = true;
            for (CbsProduceGoodsEntity produceGoodsEntity : produceEntity.getProduceGoodsEntityList()) {
                if (produceGoodsEntity.getPlanCount().compareTo(produceGoodsEntity.getTotalCount()) > 0) {
                    allCountComplete = false;
                }
            }
            if (allCountComplete) {
                resultMap.put("code", 400);
                resultMap.put("msg", "所有生产计划所需原料已完全调拨。");
                return resultMap;
            }
        } else if (entity.getType() == 2) {
            // 判断有无编辑中的出库单，有则直接返回
            CbsExportEntity exportEntity = exportDao.detail(entity.getFkExportId());
            for (CbsStoreGoodsOutEntity storeGoodsOutEntity : exportEntity.getStoreGoodsOutEntityList()) {
                if (storeGoodsOutEntity.getStatus() == 1 || storeGoodsOutEntity.getStatus() == 3) {
                    resultMap.put("code", 200);
                    resultMap.put("id", storeGoodsOutEntity.getId());
                    return resultMap;
                } else if (storeGoodsOutEntity.getStatus() == 2) {
                    resultMap.put("code", 400);
                    resultMap.put("msg", "该出口单有待审核的商品出库单，请耐心等待上级审核完毕。");
                    return resultMap;
                }
            }
            if (exportEntity.getContractEntity().getType() == 12) {
                // 对应加工合同产品出口
                List<CbsStoreGoodsEntity> storeGoodsList = storeGoodsDao.listForOutProductToExport(entity.getFkExportId());
                if (storeGoodsList == null || storeGoodsList.isEmpty()) {
                    resultMap.put("code", 400);
                    resultMap.put("msg", "相关产品库存不足，无法创建出库单，请补货入库。");
                    return resultMap;
                }
            } else {
                // 对应普通出口
                List<CbsStoreGoodsEntity> storeGoodsList = storeGoodsDao.listForOutExport(entity.getFkExportId());
                if (storeGoodsList == null || storeGoodsList.isEmpty()) {
                    resultMap.put("code", 400);
                    resultMap.put("msg", "相关商品库存不足，无法创建出库单，请补货入库。");
                    return resultMap;
                }
            }
            // 检查本次出口数量已全部调拨
            boolean allCountComplete = true;
            for (CbsExportGoodsEntity exportGoodsEntity : exportEntity.getExportGoodsList()) {
                if (exportGoodsEntity.getCount().compareTo(exportGoodsEntity.getStoreOutCount()) > 0) {
                    allCountComplete = false;
                }
            }
            if (allCountComplete) {
                resultMap.put("code", 400);
                resultMap.put("msg", "所有出口单所需原料已完全调拨。");
                return resultMap;
            }
        } else if (entity.getType() == 3) {
            // 对应国内发货出库
            // 判断有无编辑中的出库单，有则直接返回
            CbsSellEntity sellEntity = sellDao.detail(entity.getFkSellId());
            for (CbsStoreGoodsOutEntity storeGoodsOutEntity : sellEntity.getStoreGoodsOutEntityList()) {
                if (storeGoodsOutEntity.getStatus() == 1 || storeGoodsOutEntity.getStatus() == 3) {
                    resultMap.put("code", 200);
                    resultMap.put("id", storeGoodsOutEntity.getId());
                    return resultMap;
                } else if (storeGoodsOutEntity.getStatus() == 2) {
                    resultMap.put("code", 400);
                    resultMap.put("msg", "该发货单有待审核的商品出库单，请耐心等待上级审核完毕。");
                    return resultMap;
                }
            }
            List<CbsStoreGoodsEntity> storeGoodsList = storeGoodsDao.listForOutSell(entity.getFkSellId());
            if (storeGoodsList == null || storeGoodsList.isEmpty()) {
                resultMap.put("code", 400);
                resultMap.put("msg", "相关商品库存不足，无法创建出库单，请补货入库。");
                return resultMap;
            }
            // 检查本次发货数量已全部调拨
            boolean allCountComplete = true;
            for (CbsSellGoodsEntity sellGoodsEntity : sellEntity.getSellGoodsList()) {
                if (sellGoodsEntity.getCount().compareTo(sellGoodsEntity.getStoreOutCount()) > 0) {
                    allCountComplete = false;
                }
            }
            if (allCountComplete) {
                resultMap.put("code", 400);
                resultMap.put("msg", "所需发货商品数量已完全调拨。");
                return resultMap;
            }
        }
        return resultMap;
    }

    /**
     * 判断出库单完整性
     */
    private String checkStoreGoodsOutComplete(CbsStoreGoodsOutEntity entity) {
        if (entity.getStoreGoodsOutItemEntityList().size() <= 0) {
            return "出库单必须包含出库商品";
        } else {
            // 不要求每一种商品数量都大于0，但是必须有至少一种商品数量大于0
            BigDecimal totalCount = BigDecimal.ZERO;
            for (CbsStoreGoodsOutItemEntity storeGoodsOutItem : entity.getStoreGoodsOutItemEntityList()) {
                totalCount = totalCount.add(storeGoodsOutItem.getGoodsOutCount());
            }
            if (totalCount.compareTo(BigDecimal.ZERO) <= 0) {
                return "出库商品数量必须大于0";
            }
        }
        // 运输公司
        if (entity.getTransitCompanyEntity() == null) {
            return "运输公司不能为空";
        }
        // 仓库
        if (entity.getStoreEntity() == null) {
            return "出库仓库不能为空";
        }
        // 提货
        if (entity.getStartAddress() == null) {
            return "提货地址不能为空";
        }
        if (entity.getStartContact() == null) {
            return "提货联系人不能为空";
        }
        if (entity.getStartPhone() == null) {
            return "提货联系电话不能为空";
        }
        // 送达
        if (entity.getEndAddress() == null) {
            return "送达地址不能为空";
        }
        if (entity.getEndContact() == null) {
            return "送达联系人不能为空";
        }
        if (entity.getEndPhone() == null) {
            return "送达联系电话不能为空";
        }
        // 签收附件
        if (entity.getType() != 3) {
            if (entity.getImgStoreGoodsOutReceiptEntityList() == null || entity.getImgStoreGoodsOutReceiptEntityList().isEmpty())
                return "签收证明附件不能为空";
        }
        return null;
    }

    private CbsStoreGoodsOutStatusStreamEntity getLastStatusStreamEntity(Long storeGoodsOutId) {
        List<CbsStoreGoodsOutStatusStreamEntity> statusStreamEntityList = storeGoodsOutStatusStreamDao.selectList(new QueryWrapper<CbsStoreGoodsOutStatusStreamEntity>().eq("fk_store_goods_out_id", storeGoodsOutId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 转化出口提单到出库提货单
     */
    private CbsImgStoreGoodsOutDeliveryOrderEntity deliveryOrderFromExport(CbsImgExportDeliveryOrderEntity deliveryOrderEntity) {
        CbsImgStoreGoodsOutDeliveryOrderEntity storeGoodsOutDeliveryOrderEntity = new CbsImgStoreGoodsOutDeliveryOrderEntity();
        storeGoodsOutDeliveryOrderEntity.setCreateTime(deliveryOrderEntity.getCreateTime());
        storeGoodsOutDeliveryOrderEntity.setImgUrl(deliveryOrderEntity.getImgUrl());
        storeGoodsOutDeliveryOrderEntity.setIsValid(deliveryOrderEntity.getIsValid());
        storeGoodsOutDeliveryOrderEntity.setUpdateTime(new Date());
        storeGoodsOutDeliveryOrderEntity.setSort(deliveryOrderEntity.getSort());
        return storeGoodsOutDeliveryOrderEntity;
    }

    /**
     * 转化国内发货提单到出库提货单
     */
    private CbsImgStoreGoodsOutDeliveryOrderEntity deliveryOrderFromSell(CbsImgSellDeliveryOrderEntity deliveryOrderEntity) {
        CbsImgStoreGoodsOutDeliveryOrderEntity storeGoodsOutDeliveryOrderEntity = new CbsImgStoreGoodsOutDeliveryOrderEntity();
        storeGoodsOutDeliveryOrderEntity.setCreateTime(deliveryOrderEntity.getCreateTime());
        storeGoodsOutDeliveryOrderEntity.setImgUrl(deliveryOrderEntity.getImgUrl());
        storeGoodsOutDeliveryOrderEntity.setIsValid(deliveryOrderEntity.getIsValid());
        storeGoodsOutDeliveryOrderEntity.setUpdateTime(new Date());
        storeGoodsOutDeliveryOrderEntity.setSort(deliveryOrderEntity.getSort());
        return storeGoodsOutDeliveryOrderEntity;
    }
}