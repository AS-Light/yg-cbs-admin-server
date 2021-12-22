package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsContractStatusStreamEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-16 16:45:20
 */
@Mapper
@Repository
public interface CbsContractStatusStreamDao extends BaseMapper<CbsContractStatusStreamEntity> {
	
}
