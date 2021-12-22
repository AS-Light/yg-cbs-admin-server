package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgImportEntryBillEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 进口报关单图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgImportEntryBillDao extends BaseMapper<CtbImgImportEntryBillEntity> {
    List<CtbImgImportEntryBillEntity> listByImportId(Long importId);
}
