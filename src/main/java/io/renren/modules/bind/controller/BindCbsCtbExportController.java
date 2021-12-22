package io.renren.modules.bind.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.bind.entity.BindCbsCtbExportEntity;
import io.renren.modules.bind.service.BindCbsCtbExportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;



/**
 * 关联表：cbs_contract | ctb_order_prodcessing_trade
 * 1、列表（list）：获取列表时获取关联的列表详情，即关联出cbs_export和ctb_export相关信息
 * 2、导入1（cbsSynToCtb）：从cbs_export导入ctb_export
 * 3、导入2（ctbSynToCbs）：从ctb_export导入cbs_export
 * 4、详情（detail）：同时获取cbs_export和ctb_export详情，即两个detail
 * 5、删除（delete）：可以在列表里删除相关绑定关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:34:21
 */
@RestController
@RequestMapping("bind/cbsCtbExport")
public class BindCbsCtbExportController {
    @Autowired
    private BindCbsCtbExportService bindCbsCtbExportService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bind:cbsctbexport:list")
    public R list(@RequestBody BindCbsCtbExportEntity entity) {
        PageUtils page = bindCbsCtbExportService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息（详情）
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bind:cbsctbexport:info")
    public R info(@PathVariable("id") Long id) {
        BindCbsCtbExportEntity bindCbsCtbExport = bindCbsCtbExportService.detail(id);
        return R.ok().put("entity", bindCbsCtbExport);
    }
    
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bind:cbsctbexport:delete")
    public R delete(@RequestBody Long[] ids) {
        bindCbsCtbExportService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
