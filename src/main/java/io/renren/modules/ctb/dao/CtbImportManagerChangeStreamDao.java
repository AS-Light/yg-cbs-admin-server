package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImportManagerChangeStreamEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 合同父表单，继承表单如加工贸易合同ctb_contract_processing、国内采购合同单ctb_contract_purchase等
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-09 15:13:08
 */
@Mapper
@Repository
public interface CtbImportManagerChangeStreamDao extends BaseMapper<CtbImportManagerChangeStreamEntity> {
	
}
