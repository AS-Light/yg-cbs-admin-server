package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbExportStatusStreamEntity;

import java.util.Map;

/**
 * 合同父表单，继承表单如加工贸易合同ctb_contract_processing、国内采购合同单ctb_contract_purchase等
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbExportStatusStreamService extends IService<CtbExportStatusStreamEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

