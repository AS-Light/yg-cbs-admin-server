package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsStoreGoodsByNoEntity;
import io.renren.modules.cbs.entity.CbsStoreGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 产品库存表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Mapper
@Repository
public interface CbsStoreGoodsDao extends BaseMapper<CbsStoreGoodsEntity> {
    IPage<CbsStoreGoodsByNoEntity> queryIndex(IPage<CbsStoreGoodsByNoEntity> page, @Param(Constants.WRAPPER) CbsStoreGoodsByNoEntity entity);

    List<CbsStoreGoodsEntity> listForOutMaterialToProduce(Long produceId);

    List<CbsStoreGoodsEntity> listForOutProductToExport(Long exportId);

    List<CbsStoreGoodsEntity> listForOutExport(Long exportId);

    List<CbsStoreGoodsEntity> listForOutSell(Long sellId);

    List<CbsStoreGoodsEntity> listByGoodsPartNo(Long goodsPartNo);
}
