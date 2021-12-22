package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.ctb.entity.CtbStoreGoodsOutEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品出库表，
继承表包含cbs_store_goods_out_produce、cbs_store_goods_out_export，
原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@Mapper
public interface CtbStoreGoodsOutDao extends BaseMapper<CtbStoreGoodsOutEntity> {
    IPage<CtbStoreGoodsOutEntity> queryIndex(IPage<CtbStoreGoodsOutEntity> page, @Param(Constants.WRAPPER) CtbStoreGoodsOutEntity entity);

    CtbStoreGoodsOutEntity detail(Long id);
}
