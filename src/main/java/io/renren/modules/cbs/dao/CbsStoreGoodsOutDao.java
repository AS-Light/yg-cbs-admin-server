package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品出库表，
 * 继承表包含cbs_store_goods_out_produce、cbs_store_goods_out_export，
 * 原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Mapper
@Repository
public interface CbsStoreGoodsOutDao extends BaseMapper<CbsStoreGoodsOutEntity> {

    IPage<CbsStoreGoodsOutEntity> queryIndex(IPage<CbsStoreGoodsOutEntity> page, @Param(Constants.WRAPPER) CbsStoreGoodsOutEntity entity);

    CbsStoreGoodsOutEntity detail(Long id);

    List<CbsStoreGoodsOutEntity> listSimpleDetailByProduceId(Long produceId);

    List<CbsStoreGoodsOutEntity> listSimpleDetailByExportId(Long exportId);

    List<CbsStoreGoodsOutEntity> listSimpleDetailBySellId(Long sellId);
}
