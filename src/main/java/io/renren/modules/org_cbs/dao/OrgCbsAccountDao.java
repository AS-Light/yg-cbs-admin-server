package io.renren.modules.org_cbs.dao;

import io.renren.modules.org_cbs.entity.OrgCbsAccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 结算账户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
@Mapper
public interface OrgCbsAccountDao extends BaseMapper<OrgCbsAccountEntity> {
	
}
