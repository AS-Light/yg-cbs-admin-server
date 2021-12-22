package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgImportInvoiceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 进口发票附件图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgImportInvoiceDao extends BaseMapper<CtbImgImportInvoiceEntity> {
    List<CtbImgImportInvoiceEntity> listByImportId(Long importId);
}
