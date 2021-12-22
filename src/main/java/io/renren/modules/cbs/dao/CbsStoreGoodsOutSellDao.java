package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutSellEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 国内采购入库表，与cbs_store_goods_in表为1对1继承关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@Mapper
@Repository
public interface CbsStoreGoodsOutSellDao extends BaseMapper<CbsStoreGoodsOutSellEntity> {
    CbsStoreGoodsOutSellEntity selectBySellId(Long sellId);

    CbsStoreGoodsOutSellEntity selectByStoreOutId(Long storeOutId);

    List<CbsStoreGoodsOutSellEntity> listByContract(Long contractId);
}
