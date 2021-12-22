package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgExportInvoiceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出口发票附件图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgExportInvoiceDao extends BaseMapper<CtbImgExportInvoiceEntity> {
    List<CtbImgExportInvoiceEntity> listByExportId(Long exportId);
}
