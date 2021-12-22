package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.*;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.cbs.service.CbsProduceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service("cbsProduceService")
public class CbsProduceServiceImpl extends ServiceImpl<CbsProduceDao, CbsProduceEntity> implements CbsProduceService {

    private CbsProduceDao produceDao;
    private CbsContractDao contractDao;
    private CbsProduceGoodsDao produceGoodsDao;
    private CbsProduceStatusStreamDao produceStatusStreamDao;
    private CbsGoodsPartNoDao goodsPartNoDao;

    @Autowired
    public void setProduceDao(CbsProduceDao produceDao) {
        this.produceDao = produceDao;
    }

    @Autowired
    public void setContractDao(CbsContractDao contractDao) {
        this.contractDao = contractDao;
    }

    @Autowired
    public void setProduceGoodsDao(CbsProduceGoodsDao produceGoodsDao) {
        this.produceGoodsDao = produceGoodsDao;
    }

    @Autowired
    public void setProduceStatusStreamDao(CbsProduceStatusStreamDao produceStatusStreamDao) {
        this.produceStatusStreamDao = produceStatusStreamDao;
    }

    @Autowired
    public void setGoodsPartNoDao(CbsGoodsPartNoDao goodsPartNoDao) {
        this.goodsPartNoDao = goodsPartNoDao;
    }

    @Override
    public PageUtils queryIndex(CbsProduceEntity entity) {
        IPage<CbsProduceEntity> page = produceDao.queryIndex(new QueryPage<CbsProduceEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public CbsProduceEntity detail(Long id) {
        return produceDao.detail(id);
    }

    @Override
    public HashMap<String, Object> preSave(CbsProduceEntity entity) {
        // 1、无合同对应的生产单，直接创建
        // 2、有合同对应的生产单
        // ① 有未启动的生产计划（状态1、3），直接返回生产计划Id
        // ② 有正在启动审核的生产计划（状态2），提示【有即将启动的生产计划，请耐心等待上级审核】
        // ③ 所有生产计划对应合同料号商品的计划数量总和大于等于合同数量，提示【所有合同需要的生产计划都已创建，请完成当前计划】
        // ④ 以上三点之外直接创建
        CbsContractEntity contract = contractDao.detail(entity.getFkContractId());
        contract.setContractGoodsList(new ArrayList<>());
        for (CbsContractEntity childContract : contract.getChildren()) {
            contract.getContractGoodsList().addAll(childContract.getContractGoodsList());
        }
        List<CbsProduceEntity> contractProduceList = produceDao.queryByContractIdWithGoods(entity.getFkContractId());
        HashMap<String, Object> resultMap = new HashMap<>();
        if (contractProduceList == null || contractProduceList.isEmpty()) {
            resultMap.put("code", 200);
            return resultMap;
        } else {
            for (CbsProduceEntity contractProduce : contractProduceList) {
                if (Arrays.asList(1, 3).contains(contractProduce.getStatus())) {
                    resultMap.put("code", 200);
                    resultMap.put("id", contractProduce.getId());
                    return resultMap;
                } else if (contractProduce.getStatus() == 2) {
                    resultMap.put("code", 300);
                    resultMap.put("msg", "该合同有即将启动的生产计划，请耐心等待上级审核。");
                    return resultMap;
                }
            }
            boolean allGoodsCountComplete = true;
            for (CbsContractGoodsEntity contractGoods : contract.getContractGoodsList()) {
                CbsGoodsPartNoEntity goodsPartNoEntity = contractGoods.getPartNoEntity();
                if (goodsPartNoEntity.getContractCount().compareTo(goodsPartNoEntity.getPlanCount()) > 0) {
                    allGoodsCountComplete = false;
                }
            }
            if (allGoodsCountComplete) {
                resultMap.put("code", 300);
                resultMap.put("msg", "所有合同中料件的使用数量以及产品的生产数量都已加入生产计划，请完成当前的生产计划。");
                return resultMap;
            }
        }
        resultMap.put("code", 200);
        return resultMap;
    }

    @Override
    @Transactional
    public Long saveReturnId(CbsProduceEntity entity) {
        String errMsg = checkBeforeCreate(entity);
        if (!StringUtils.isEmpty(errMsg)) {
            throw new RuntimeException(errMsg);
        }

        produceDao.insert(entity);
        Long produceId = entity.getId();
        for (CbsProduceGoodsEntity produceGoodsEntity : entity.getProduceGoodsEntityList()) {
            produceGoodsEntity.setFkProduceId(produceId);
            produceGoodsDao.insert(produceGoodsEntity);
        }
        // 为进口单创建第一个状态流
        CbsProduceStatusStreamEntity statusStreamEntity = new CbsProduceStatusStreamEntity();
        statusStreamEntity.setFkProduceId(produceId);
        statusStreamEntity.setRemark("创建");
        statusStreamEntity.setLastStatus(0);
        statusStreamEntity.setStatus(1);
        statusStreamEntity.setOperator(entity.getOperator());
        produceStatusStreamDao.insert(statusStreamEntity);
        return produceId;
    }

    /**
     * 生产开始提审
     */
    @Override
    @Transactional
    public void submitStart(CbsProduceStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsProduceEntity entity = produceDao.detail(statusStreamEntity.getFkProduceId());
        CbsProduceStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkProduceId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 1 || lastStatusStreamEntity.getStatus() == 3) {
                entity.setStatus(2);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(2);

                produceDao.updateById(entity);
                produceStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("生产计划状态不合法，未启动或启动退审状态才能提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个生产计划,请联系管理员");
        }
    }

    /**
     * @Description: 生产开始撤审
     * 可以撤审的状态：2、计划提审
     */
    @Override
    @Transactional
    public Integer submitStartBack(CbsProduceStatusStreamEntity statusStreamEntity) {
        CbsProduceEntity entity = produceDao.selectById(statusStreamEntity.getFkProduceId());
        CbsProduceStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkProduceId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 2) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                produceDao.updateById(entity);
                produceStatusStreamDao.insert(statusStreamEntity);
                return 1;
            } else {
                return -1;
            }
        } else {
            return -3;
        }
    }

    /**
     * @Description: 生产开始审核
     * 可以审核的状态：2、提审
     */
    @Override
    @Transactional
    public void checkStart(CbsProduceStatusStreamEntity statusStreamEntity) throws RuntimeException {
        CbsProduceEntity entity = produceDao.detail(statusStreamEntity.getFkProduceId());
        CbsProduceStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkProduceId());
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 2) {
                entity.setStatus(statusStreamEntity.getStatus());
                if (statusStreamEntity.getStatus() == 4) {
                    // 审核启动通过，加入开始时间
                    entity.setStartTime(new Date());
                    for (CbsProduceGoodsEntity produceGoodsEntity : entity.getProduceGoodsEntityList()) {
                        CbsGoodsPartNoEntity goodsPartNoEntity = produceGoodsEntity.getPartNoEntity();
                        goodsPartNoEntity.setPlanCount(goodsPartNoEntity.getPlanCount().add(produceGoodsEntity.getPlanCount()));
                        goodsPartNoDao.updateById(goodsPartNoEntity);
                    }
                }
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                produceDao.updateById(entity);
                produceStatusStreamDao.insert(statusStreamEntity);
            } else {
                throw new RuntimeException("生产计划状态不合法，请先提交审核");
            }
        } else {
            throw new RuntimeException("没有找到这个生产单,请联系管理员");
        }
    }

    /**
     * @Description: 生产完成提审
     * 可以提审的状态：4、生产中 6：生产完成退审
     */
    @Override
    @Transactional
    public HashMap<String, Object> submitComplete(CbsProduceStatusStreamEntity statusStreamEntity) {
        CbsProduceEntity entity = produceDao.detail(statusStreamEntity.getFkProduceId());
        HashMap<String, Object> resultMap = checkBeforeSubmitProduceComplete(entity);
        if (resultMap.containsKey("code")) {
            if ((int) resultMap.get("code") == 500 || (statusStreamEntity.getForceToChange() == null || !statusStreamEntity.getForceToChange())) {
                return resultMap;
            }
        }

        CbsProduceStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkProduceId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 4 || lastStatusStreamEntity.getStatus() == 6) {
                entity.setStatus(5);
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());
                statusStreamEntity.setStatus(5);

                produceDao.updateById(entity);
                produceStatusStreamDao.insert(statusStreamEntity);
                resultMap.put("code", 200);
                return resultMap;
            } else {
                resultMap.put("code", 500);
                resultMap.put("msg", "生产计划状态不合法，已启动或结案退审状态才能提交审核");
                return resultMap;
            }
        } else {
            resultMap.put("code", 500);
            resultMap.put("msg", "没有找到这个生产计划,请联系管理员");
            return resultMap;
        }
    }

    /**
     * @Description: 生产完成撤审
     * 可以撤审的状态：5、生产完成提审
     */
    @Override
    @Transactional
    public Integer submitBackComplete(CbsProduceStatusStreamEntity statusStreamEntity) {
        CbsProduceEntity entity = produceDao.selectById(statusStreamEntity.getFkProduceId());
        CbsProduceStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkProduceId());
        if (lastStatusStreamEntity != null) {
            if (lastStatusStreamEntity.getStatus() == 5) {
                entity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setStatus(lastStatusStreamEntity.getLastStatus());
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                produceDao.updateById(entity);
                produceStatusStreamDao.insert(statusStreamEntity);
                return 1;
            } else {
                return -1;
            }
        } else {
            return -3;
        }
    }

    /**
     * @Description: 生产完成审核
     * 可以审核的状态：5、生产完成提审
     */
    @Override
    @Transactional
    public HashMap<String, Object> checkComplete(CbsProduceStatusStreamEntity statusStreamEntity, Long operator) {
        CbsProduceEntity entity = produceDao.detail(statusStreamEntity.getFkProduceId());
        CbsProduceStatusStreamEntity lastStatusStreamEntity = getLastStatusStreamEntity(statusStreamEntity.getFkProduceId());
        HashMap<String, Object> resultMap = checkBeforeConfirmProduceComplete(entity);
        if (lastStatusStreamEntity != null) {
            if (entity.getStatus() == 5) {
                entity.setStatus(statusStreamEntity.getStatus());
                if (statusStreamEntity.getStatus() == 7) {
                    if (resultMap.containsKey("code") && (statusStreamEntity.getForceToChange() == null || !statusStreamEntity.getForceToChange())) {
                        return resultMap;
                    }
                    // 审核完成通过
                    // 未完成计划数量设为已完成计划数量，料件计划量同步减
                    for (CbsProduceGoodsEntity produceGoodsEntity : entity.getProduceGoodsEntityList()) {
                        if (produceGoodsEntity.getPlanCount().compareTo(produceGoodsEntity.getTotalCount()) > 0) {
                            CbsGoodsPartNoEntity goodsPartNoEntity = produceGoodsEntity.getPartNoEntity();
                            goodsPartNoEntity.setPlanCount(goodsPartNoEntity.getPlanCount().subtract(produceGoodsEntity.getPlanCount().subtract(produceGoodsEntity.getTotalCount())));
                            goodsPartNoDao.updateById(goodsPartNoEntity);
//                            produceGoodsEntity.setPlanCount(produceGoodsEntity.getTotalCount());
//                            produceGoodsDao.updateById(produceGoodsEntity);
                        }
                    }
                    // 加入完结时间
                    entity.setEndTime(new Date());
                }
                statusStreamEntity.setLastStatus(lastStatusStreamEntity.getStatus());

                produceDao.updateById(entity);
                produceStatusStreamDao.insert(statusStreamEntity);
                resultMap.put("code", 200);
                return resultMap;
            } else {
                resultMap.put("code", 500);
                resultMap.put("msg", "生产计划状态不合法，请先提交审核");
                return resultMap;
            }
        } else {
            resultMap.put("code", 500);
            resultMap.put("msg", "没有找到这个生产计划,请联系管理员");
            return resultMap;
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Long[] ids) {
        List<Long> goodsIds = produceGoodsDao.listIdsByProduceIds(ids);
        if (goodsIds != null && !goodsIds.isEmpty()) {
            produceGoodsDao.deleteBatchIds(goodsIds);
        }
        removeByIds(Arrays.asList(ids));
    }

    @Override
    @Transactional
    public List<CbsProduceEntity> listForRawMaterialBack() {
        return produceDao.listForRawMaterialBack();
    }

    /**
     * 获取上一个状态
     */
    private CbsProduceStatusStreamEntity getLastStatusStreamEntity(Long produceId) {
        List<CbsProduceStatusStreamEntity> statusStreamEntityList = produceStatusStreamDao.selectList(new QueryWrapper<CbsProduceStatusStreamEntity>().eq("fk_produce_id", produceId).orderByAsc("id"));
        if (statusStreamEntityList != null && !statusStreamEntityList.isEmpty()) {
            return statusStreamEntityList.get(statusStreamEntityList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 判断生产计划完整性
     */
    private HashMap<String, Object> checkBeforeSubmitProduceComplete(CbsProduceEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        if (entity.getProduceGoodsStreamEntityList() != null) {
            for (CbsProduceGoodsStreamEntity produceStream : entity.getProduceGoodsStreamEntityList()) {
                if (produceStream.getStatus() == 1) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "有未确核的生产汇报，请先请示上级对生产汇报进行确核，或删除无用的生产汇报。");
                    return resultMap;
                }
            }
        }
        if (entity.getProduceGoodsEntityList() != null) {
            // 分成两部进行判断，优先返回产品未入库的情况
            for (CbsProduceGoodsEntity cbsProduceGoodsEntity : entity.getProduceGoodsEntityList()) {
                if (cbsProduceGoodsEntity.getGoodsType() == 2) {
                    if (cbsProduceGoodsEntity.getStoreCount().compareTo(BigDecimal.ZERO) > 0) {
                        resultMap.put("code", 500);
                        resultMap.put("msg", "有已生产未入库的产品，请先确保产品已入库。");
                        return resultMap;
                    }
                }
            }
            for (CbsProduceGoodsEntity cbsProduceGoodsEntity : entity.getProduceGoodsEntityList()) {
                if (cbsProduceGoodsEntity.getGoodsType() == 1) {
                    if (cbsProduceGoodsEntity.getStoreCount().compareTo(BigDecimal.ZERO) > 0) {
                        resultMap.put("code", 500);
                        resultMap.put("msg", "有已调拨未使用的原料，请先确保原料已还库或被使用。");
                        return resultMap;
                    }
                }
            }
            for (CbsProduceGoodsEntity cbsProduceGoodsEntity : entity.getProduceGoodsEntityList()) {
                if (cbsProduceGoodsEntity.getGoodsType() == 2) {
                    if (cbsProduceGoodsEntity.getPlanCount().compareTo(cbsProduceGoodsEntity.getTotalCount()) > 0) {
                        resultMap.put("code", 300);
                        resultMap.put("msg", "计划生产的商品数量并未完成，是否确定提审。");
                        return resultMap;
                    }
                }
                if (cbsProduceGoodsEntity.getGoodsType() == 1) {
                    if (cbsProduceGoodsEntity.getPlanCount().compareTo(cbsProduceGoodsEntity.getTotalCount()) > 0) {
                        resultMap.put("code", 300);
                        resultMap.put("msg", "计划使用的原料数量并未全部使用，是否确定提审。");
                        return resultMap;
                    }
                }
            }
        }
        return resultMap;
    }

    private HashMap<String, Object> checkBeforeConfirmProduceComplete(CbsProduceEntity entity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        for (CbsProduceGoodsEntity cbsProduceGoodsEntity : entity.getProduceGoodsEntityList()) {
            if (cbsProduceGoodsEntity.getGoodsType() == 2) {
                if (cbsProduceGoodsEntity.getPlanCount().compareTo(cbsProduceGoodsEntity.getTotalCount()) > 0) {
                    resultMap.put("code", 300);
                    resultMap.put("msg", "计划生产的商品数量并未完成，是否依然确定通过审核。");
                    return resultMap;
                }
            }
            if (cbsProduceGoodsEntity.getGoodsType() == 1) {
                if (cbsProduceGoodsEntity.getPlanCount().compareTo(cbsProduceGoodsEntity.getTotalCount()) > 0) {
                    resultMap.put("code", 300);
                    resultMap.put("msg", "计划使用的原料数量并未全部使用，是否依然确定通过审核。");
                    return resultMap;
                }
            }
        }
        return resultMap;
    }

    /**
     * 创建检查
     */
    private String checkBeforeCreate(CbsProduceEntity entity) {
        if (entity.getFkContractId() == null) {
            return "请选择一个从属生产加工合同";
        } else if (entity.getFkProduceCompanyId() == null) {
            return "请选择一个生产公司";
        } else {
            boolean hasMaterial = false;
            boolean hasProduct = false;
            for (CbsProduceGoodsEntity produceGoodsEntity : entity.getProduceGoodsEntityList()) {
                if (produceGoodsEntity.getGoodsType() == 1 &&
                        produceGoodsEntity.getPlanCount() != null &&
                        produceGoodsEntity.getPlanCount().compareTo(BigDecimal.ZERO) > 0) {
                    hasMaterial = true;
                } else if (produceGoodsEntity.getGoodsType() == 2 &&
                        produceGoodsEntity.getPlanCount() != null &&
                        produceGoodsEntity.getPlanCount().compareTo(BigDecimal.ZERO) > 0) {
                    hasProduct = true;
                }
            }
            if (!hasMaterial) {
                return "没有选择生产原料";
            } else if (!hasProduct) {
                return "没有选择产出产品";
            }
        }
        return null;
    }
}