package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsExportStatusStreamEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-14 12:50:46
 */
@Mapper
public interface CbsExportStatusStreamDao extends BaseMapper<CbsExportStatusStreamEntity> {

}
