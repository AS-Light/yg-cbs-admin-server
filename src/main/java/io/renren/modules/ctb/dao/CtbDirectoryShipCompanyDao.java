package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import io.renren.modules.cbs.entity.CbsDirectoryShipCompanyEntity;
import io.renren.modules.ctb.entity.CtbDirectoryShipCompanyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 船务公司名录表（报关行副本）
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbDirectoryShipCompanyDao extends BaseMapper<CtbDirectoryShipCompanyEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CtbDirectoryShipCompanyEntity entity);

    @SqlParser(filter = true)
    CtbDirectoryShipCompanyEntity findSameToCbs(@Param("cbsEntity") CbsDirectoryShipCompanyEntity ctbEntity, @Param("ctbTenantId") Long ctbTenantId);
}
