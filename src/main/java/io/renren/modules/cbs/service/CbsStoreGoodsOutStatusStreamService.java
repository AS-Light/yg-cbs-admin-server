package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutStatusStreamEntity;

import java.util.Map;

/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-17 12:51:23
 */
public interface CbsStoreGoodsOutStatusStreamService extends IService<CbsStoreGoodsOutStatusStreamEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

