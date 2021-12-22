package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsDirectoryTransitCompanyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 国内运输公司名录表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@Mapper
@Repository
public interface CbsDirectoryTransitCompanyDao extends BaseMapper<CbsDirectoryTransitCompanyEntity> {
	
}
