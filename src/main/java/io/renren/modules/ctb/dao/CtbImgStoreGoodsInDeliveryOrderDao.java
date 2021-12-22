package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgStoreGoodsInDeliveryOrderEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 入库提货单图片表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@Mapper
public interface CtbImgStoreGoodsInDeliveryOrderDao extends BaseMapper<CtbImgStoreGoodsInDeliveryOrderEntity> {
    List<CtbImgStoreGoodsInDeliveryOrderEntity> listByStoreInId(Long storeInId);
}
