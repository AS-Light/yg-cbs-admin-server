package io.renren.modules.bind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bind.entity.BindCbsContractCtbProcessingTradeEntity;

import java.util.HashMap;

/**
 * 关联表：cbs_contract | ctb_order_prodcessing_trade
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:34:21
 */
public interface BindCbsContractCtbProcessingTradeService extends IService<BindCbsContractCtbProcessingTradeEntity> {
    /**
     * 列表（queryIndex）：获取列表时获取关联的列表详情，即关联出cbs_contract和ctb_order_processing_trade相关信息
     **/
    PageUtils queryIndex(BindCbsContractCtbProcessingTradeEntity entity);

    /**
     * 同步1（cbsSynToCtb）：从cbs_contract导入ctb_order_processing_trade
     **/
    HashMap<String, Object> cbsSynToCtb(Long cbsContractId, Long cbsCompanyId, Long ctbCompanyId, Long operator);

    /**
     * 同步2（ctbSynToCbs）：从ctb_order_processing_trade导入cbs_contract
     **/
    Long ctbSynToCbs(Long ctbOrderProcessingTradeId);

    /**
     * 详情（detail）：同时获取cbs_contract和ctb_order_processing_trade详情，即两个detail
     **/
    BindCbsContractCtbProcessingTradeEntity detail(Long id);
}

