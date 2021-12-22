package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsContractStatusStreamEntity;

import java.util.Map;

/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-16 16:45:20
 */
public interface CbsContractStatusStreamService extends IService<CbsContractStatusStreamEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

