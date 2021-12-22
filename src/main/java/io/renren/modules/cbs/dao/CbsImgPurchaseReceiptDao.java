package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsImgPurchaseReceiptEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 国内收货签收证明图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@Mapper
public interface CbsImgPurchaseReceiptDao extends BaseMapper<CbsImgPurchaseReceiptEntity> {
    List<CbsImgPurchaseReceiptEntity> listByPurchaseId(Long purchaseId);
}
