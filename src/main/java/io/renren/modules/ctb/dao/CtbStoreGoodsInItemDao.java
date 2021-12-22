package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbStoreGoodsInItemEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品入库表，
 * 继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，
 * 原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@Mapper
public interface CtbStoreGoodsInItemDao extends BaseMapper<CtbStoreGoodsInItemEntity> {
    List<CtbStoreGoodsInItemEntity> listByStoreInId(Long storeInId);
}
