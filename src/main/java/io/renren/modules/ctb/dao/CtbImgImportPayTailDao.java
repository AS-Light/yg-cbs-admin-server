package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgImportPayTailEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 尾款附件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgImportPayTailDao extends BaseMapper<CtbImgImportPayTailEntity> {
    List<CtbImgImportPayTailEntity> listByImportId(Long importId);
}
