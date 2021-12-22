package io.renren.modules.bind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bind.entity.BindCbsCtbExportEntity;

import java.util.HashMap;

/**
 * 关联表：cbs_contract | ctb_order_prodcessing_trade
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:34:21
 */
public interface BindCbsCtbExportService extends IService<BindCbsCtbExportEntity> {

    /**
     * 列表（queryIndex）：获取列表时获取关联的列表详情，即关联出cbs_export和ctb_export相关信息
     **/
    PageUtils queryIndex(BindCbsCtbExportEntity entity);

    /**
     * 同步1（cbsSynToCtb）：从cbs_export导入ctb_export
     **/
    HashMap<String, Object> cbsSynToCtb(Long cbsExportId, Long cbsCompanyId, Long ctbCompanyId);

    /**
     * 同步2（ctbSynToCbs）：从ctb_export导入cbs_export
     **/
    Long ctbSynToCbs(Long ctbExportId);

    /**
     * 详情（detail）：同时获取cbs_export和ctb_export详情，即两个detail
     **/
    BindCbsCtbExportEntity detail(Long id);
}

