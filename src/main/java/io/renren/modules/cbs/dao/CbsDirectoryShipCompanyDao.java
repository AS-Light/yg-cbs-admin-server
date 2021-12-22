package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import io.renren.modules.cbs.entity.CbsDirectoryShipCompanyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbDirectoryShipCompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 船务公司名录表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@Mapper
@Repository
public interface CbsDirectoryShipCompanyDao extends BaseMapper<CbsDirectoryShipCompanyEntity> {
	@SqlParser(filter = true)
    void insertWithoutTenant(CbsDirectoryShipCompanyEntity entity);

    @SqlParser(filter = true)
    CbsDirectoryShipCompanyEntity findSameToCtb(@Param("ctbEntity") CtbDirectoryShipCompanyEntity ctbEntity, @Param("tenantId") Long tenantId);
}
