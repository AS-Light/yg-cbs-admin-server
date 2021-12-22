package io.renren.modules.ctb.dao;

import io.renren.modules.ctb.entity.CtbImgExportEntryBillEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出口报关单图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgExportEntryBillDao extends BaseMapper<CtbImgExportEntryBillEntity> {
    List<CtbImgExportEntryBillEntity> listByExportId(Long exportId);
}
