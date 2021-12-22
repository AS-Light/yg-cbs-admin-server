package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgImportPayInAdvanceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 首付款附件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgImportPayInAdvanceDao extends BaseMapper<CtbImgImportPayInAdvanceEntity> {
    List<CtbImgImportPayInAdvanceEntity> listByImportId(Long importId);
}
