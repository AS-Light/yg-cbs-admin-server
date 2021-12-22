package io.renren.modules.ctb.dao;

import io.renren.modules.ctb.entity.CtbDirectoryTransitCompanyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 国内运输公司名录表（报关行副本）
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbDirectoryTransitCompanyDao extends BaseMapper<CtbDirectoryTransitCompanyEntity> {
	
}
