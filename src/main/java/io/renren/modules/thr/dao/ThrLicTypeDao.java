package io.renren.modules.thr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.thr.entity.ThrLicTypeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 企业产品许可类别代码表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@Mapper
public interface ThrLicTypeDao extends BaseMapper<ThrLicTypeEntity> {
	
}
