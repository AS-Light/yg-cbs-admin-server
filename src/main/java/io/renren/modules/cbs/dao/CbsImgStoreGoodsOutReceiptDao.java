package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsImgStoreGoodsOutReceiptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 入库签收证明图片表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-24 09:10:51
 */
@Mapper
@Repository
public interface CbsImgStoreGoodsOutReceiptDao extends BaseMapper<CbsImgStoreGoodsOutReceiptEntity> {
    List<CbsImgStoreGoodsOutReceiptEntity> listByStoreOutId(Long storeGoodsOutId);
}
