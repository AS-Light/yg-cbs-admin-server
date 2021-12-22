package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbExportManagerChangeStreamEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同父表单，继承表单如加工贸易合同ctb_contract_processing、国内采购合同单ctb_contract_purchase等
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-09 15:13:08
 */
@Mapper
public interface CtbExportManagerChangeStreamDao extends BaseMapper<CtbExportManagerChangeStreamEntity> {
	
}
