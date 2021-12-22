package io.renren.modules.org_ctb.dao;

import io.renren.modules.org_ctb.entity.OrgCtbAccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 结算账户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
@Mapper
@Repository
public interface OrgCtbAccountDao extends BaseMapper<OrgCtbAccountEntity> {
	
}
