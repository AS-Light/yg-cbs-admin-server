package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.ctb.entity.CtbStoreGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 产品库存表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@Mapper
public interface CtbStoreGoodsDao extends BaseMapper<CtbStoreGoodsEntity> {
    IPage<CtbStoreGoodsEntity> queryIndex(IPage<CtbStoreGoodsEntity> page, @Param(Constants.WRAPPER) CtbStoreGoodsEntity entity);

    CtbStoreGoodsEntity detail(Long id);
}
