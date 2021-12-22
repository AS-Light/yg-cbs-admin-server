package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsSellGoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface CbsSellGoodsDao extends BaseMapper<CbsSellGoodsEntity> {
    List<CbsSellGoodsEntity> listBySellId(Long sellId);

    CbsSellGoodsEntity simpleDetail(Long id);

    List<Long> listIdsBySellIds(@Param("sellIds") Long[] sellIds);
}
