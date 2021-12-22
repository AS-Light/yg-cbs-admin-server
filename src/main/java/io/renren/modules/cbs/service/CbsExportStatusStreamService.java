package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsExportStatusStreamEntity;

import java.util.Map;

/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-01-04 17:59:55
 */
public interface CbsExportStatusStreamService extends IService<CbsExportStatusStreamEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

