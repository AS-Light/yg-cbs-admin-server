package io.renren.modules.thr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.thr.entity.ThrTrafModeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运输方式表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@Mapper
public interface ThrTrafModeDao extends BaseMapper<ThrTrafModeEntity> {
	
}
