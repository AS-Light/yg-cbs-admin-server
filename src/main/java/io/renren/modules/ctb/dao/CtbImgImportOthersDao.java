package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgImportOthersEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 进口其他附件图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgImportOthersDao extends BaseMapper<CtbImgImportOthersEntity> {
    List<CtbImgImportOthersEntity> listByImportId(Long importId);
}
