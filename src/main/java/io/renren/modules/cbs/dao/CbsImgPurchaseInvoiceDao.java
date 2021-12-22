package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsImgPurchaseInvoiceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出口发票附件图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@Mapper
public interface CbsImgPurchaseInvoiceDao extends BaseMapper<CbsImgPurchaseInvoiceEntity> {
    List<CbsImgPurchaseInvoiceEntity> listByPurchaseId(Long purchaseId);
}
