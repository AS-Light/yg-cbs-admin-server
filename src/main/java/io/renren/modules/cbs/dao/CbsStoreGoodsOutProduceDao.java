package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutProduceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 生产出库表，与cbs_store_goods_out表为1对1继承关系
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Mapper
@Repository
public interface CbsStoreGoodsOutProduceDao extends BaseMapper<CbsStoreGoodsOutProduceEntity> {
    CbsStoreGoodsOutProduceEntity selectByProduceId(Long produceId);

    CbsStoreGoodsOutProduceEntity selectByStoreOutId(Long storeOutId);

    List<CbsStoreGoodsOutProduceEntity> listByContract(Long contractId);
}
