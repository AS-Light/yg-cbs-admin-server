package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsStoreGoodsInEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品入库表，
 * 继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，
 * 原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Mapper
@Repository
public interface CbsStoreGoodsInDao extends BaseMapper<CbsStoreGoodsInEntity> {

    IPage<CbsStoreGoodsInEntity> queryIndex(IPage<CbsStoreGoodsInEntity> page, @Param(Constants.WRAPPER) CbsStoreGoodsInEntity entity);

    CbsStoreGoodsInEntity detail(Long id);

    List<CbsStoreGoodsInEntity> listSimpleDetailByProduceId(Long produceId);

    List<CbsStoreGoodsInEntity> listSimpleDetailByProduceBackId(Long produceId);

    Long selectImportId(@Param("id") Long id);

    Long selectPurchaseId(@Param("id") Long id);
}
