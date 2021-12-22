package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbStoreGoodsOutItemEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 原料出库表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@Mapper
public interface CtbStoreGoodsOutItemDao extends BaseMapper<CtbStoreGoodsOutItemEntity> {
    List<CtbStoreGoodsOutItemEntity> listByStoreOutId(Long storeOutId);
}
