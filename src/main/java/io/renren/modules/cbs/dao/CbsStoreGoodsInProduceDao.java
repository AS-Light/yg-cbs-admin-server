package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsStoreGoodsInProduceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 生产入库表，与cbs_store_goods_in表为1对1继承关系
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Mapper
@Repository
public interface CbsStoreGoodsInProduceDao extends BaseMapper<CbsStoreGoodsInProduceEntity> {
    CbsStoreGoodsInProduceEntity selectByProduceId(Long purchaseId);

    CbsStoreGoodsInProduceEntity selectByStoreInId(Long storeInId);

    List<CbsStoreGoodsInProduceEntity> listByContract(Long contractId);

    List<CbsStoreGoodsInProduceEntity> listByOutToProduceId(@Param("produceId") Long produceId);
}
