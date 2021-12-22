package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsPurchaseEntity;
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
public interface CbsPurchaseDao extends BaseMapper<CbsPurchaseEntity> {
    IPage<CbsPurchaseEntity> queryIndex(IPage<CbsPurchaseEntity> page, @Param(Constants.WRAPPER) CbsPurchaseEntity entity);

    List<CbsPurchaseEntity> queryByContractIdWithGoods(Long contractId);

    List<CbsPurchaseEntity> queryForStoreIn();

    CbsPurchaseEntity simpleDetail(Long id);

    CbsPurchaseEntity simpleDetailWithGoodsItems(Long id);

    CbsPurchaseEntity detail(Long id);

    List<CbsPurchaseEntity> listByContractId(Long contractId);
}
