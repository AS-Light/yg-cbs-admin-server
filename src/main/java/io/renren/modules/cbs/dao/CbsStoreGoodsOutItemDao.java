package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 原料出库表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Mapper
@Repository
public interface CbsStoreGoodsOutItemDao extends BaseMapper<CbsStoreGoodsOutItemEntity> {
    List<CbsStoreGoodsOutItemEntity> listByStoreOutId(Long storeOutId);

    CbsStoreGoodsOutItemEntity selectById(Long id);

    List<Long> listIdsByStoreGoodsOutIds(@Param("storeGoodsOutIds") Long[] storeGoodsOutIds);
}
