package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsPurchaseGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出口货物清单
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@Mapper
public interface CbsPurchaseGoodsDao extends BaseMapper<CbsPurchaseGoodsEntity> {
    List<CbsPurchaseGoodsEntity> listByPurchaseId(Long purchaseId);

    CbsPurchaseGoodsEntity simpleDetail(Long id);

    List<Long> listIdsByPurchaseIds(@Param("purchaseIds") Long[] purchaseIds);
}
