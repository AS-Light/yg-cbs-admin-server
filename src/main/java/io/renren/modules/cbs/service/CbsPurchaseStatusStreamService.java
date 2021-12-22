package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsPurchaseStatusStreamEntity;

import java.util.Map;

/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
public interface CbsPurchaseStatusStreamService extends IService<CbsPurchaseStatusStreamEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

