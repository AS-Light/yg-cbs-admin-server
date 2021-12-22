package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgStoreGoodsOutReceiptEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出库签收证明图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@Mapper
public interface CtbImgStoreGoodsOutReceiptDao extends BaseMapper<CtbImgStoreGoodsOutReceiptEntity> {
	List<CtbImgStoreGoodsOutReceiptEntity> listByStoreOutId(Long storeOutId);
}
