package io.renren.modules.org_ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.org_ctb.entity.OrgCtbCompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户公司表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-17 16:41:40
 */
@Mapper
@Repository
public interface OrgCtbCompanyDao extends BaseMapper<OrgCtbCompanyEntity> {
    List<OrgCtbCompanyEntity> listWithCbsBind(@Param("cbsCompanyId") Long cbsCompanyId, @Param("ctbName") String ctbName);
}
