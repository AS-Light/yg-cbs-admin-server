package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgExportOthersEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出口其他附件图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgExportOthersDao extends BaseMapper<CtbImgExportOthersEntity> {
    List<CtbImgExportOthersEntity> listByExportId(Long exportId);
}
