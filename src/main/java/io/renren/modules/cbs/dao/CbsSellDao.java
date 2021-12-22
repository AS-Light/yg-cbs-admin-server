package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsSellEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品入库表，
 * 继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，
 * 原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@Mapper
public interface CbsSellDao extends BaseMapper<CbsSellEntity> {
    IPage<CbsSellEntity> queryIndex(IPage<CbsSellEntity> page, @Param(Constants.WRAPPER) CbsSellEntity entity);

    List<CbsSellEntity> queryByContractIdWithGoods(Long contractId);

    List<CbsSellEntity> queryForStoreOut();

    CbsSellEntity simpleDetail(Long id);

    CbsSellEntity simpleDetailWithGoodsItems(Long id);

    CbsSellEntity detail(Long id);

    List<CbsSellEntity> listByContractId(Long contractId);
}
