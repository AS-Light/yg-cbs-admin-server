package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgImportPowerOfAttorneyEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 授权书附件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgImportPowerOfAttorneyDao extends BaseMapper<CtbImgImportPowerOfAttorneyEntity> {
    List<CtbImgImportPowerOfAttorneyEntity> listByImportId(Long importId);
}
