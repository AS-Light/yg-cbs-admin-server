package io.renren.modules.bind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bind.entity.BindCbsCtbImportEntity;

import java.util.HashMap;

/**
 * 关联表：cbs_contract | ctb_order_prodcessing_trade
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:34:21
 */
public interface BindCbsCtbImportService extends IService<BindCbsCtbImportEntity> {

    /**
     * 列表（queryIndex）：获取列表时获取关联的列表详情，即关联出cbs_import和ctb_import相关信息
     **/
    PageUtils queryIndex(BindCbsCtbImportEntity entity);

    /**
     * 同步1（cbsSynToCtb）：从cbs_import导入ctb_import
     **/
    HashMap<String, Object> cbsSynToCtb(Long cbsImportId, Long cbsCompanyId, Long ctbCompanyId);

    /**
     * 同步2（ctbSynToCbs）：从ctb_import导入cbs_import
     **/
    Long ctbSynToCbs(Long ctbImportId);

    /**
     * 详情（detail）：同时获取cbs_import和ctb_import详情，即两个detail
     **/
    BindCbsCtbImportEntity detail(Long id);
}

