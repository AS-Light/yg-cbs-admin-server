package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsDirectoryProduceCompanyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 生产公司名录表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@Mapper
@Repository
public interface CbsDirectoryProduceCompanyDao extends BaseMapper<CbsDirectoryProduceCompanyEntity> {
	
}
