package io.renren.modules.ctb.dao;

import io.renren.modules.ctb.entity.CtbServiceContractTempleteEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 报关行货运代理合同模板，原则上报关行只有一个模板，有一个ctb_tenant_id=-1的模板作为公共模板
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbServiceContractTempleteDao extends BaseMapper<CtbServiceContractTempleteEntity> {
	
}
