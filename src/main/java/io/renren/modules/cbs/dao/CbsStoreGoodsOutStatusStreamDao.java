package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsStoreGoodsOutStatusStreamEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-17 12:51:23
 */
@Mapper
@Repository
public interface CbsStoreGoodsOutStatusStreamDao extends BaseMapper<CbsStoreGoodsOutStatusStreamEntity> {
	
}
