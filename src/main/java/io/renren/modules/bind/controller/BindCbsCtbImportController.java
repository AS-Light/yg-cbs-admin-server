package io.renren.modules.bind.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.bind.entity.BindCbsCtbImportEntity;
import io.renren.modules.bind.service.BindCbsCtbImportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 关联表：cbs_contract | ctb_order_prodcessing_trade
 * 接口概要：
 * 1、列表（list）：获取列表时获取关联的列表详情，即关联出cbs_import和ctb_import相关信息
 * 2、导入1（cbsSynToCtb）：从cbs_import导入ctb_import
 * 3、导入2（ctbSynToCbs）：从ctb_import导入cbs_import
 * 4、详情（detail）：同时获取cbs_import和ctb_import详情，即两个detail
 * 5、删除（delete）：可以在列表里删除相关绑定关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:34:21
 */
@RestController
@RequestMapping("bind/cbsCtbImport")
public class BindCbsCtbImportController {
    @Autowired
    private BindCbsCtbImportService bindCbsCtbImportService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bind:cbsctbimport:list")
    public R list(@RequestBody BindCbsCtbImportEntity entity) {
        PageUtils page = bindCbsCtbImportService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息（详情）
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bind:cbsctbimport:info")
    public R info(@PathVariable("id") Long id) {
        BindCbsCtbImportEntity bindCbsCtbImport = bindCbsCtbImportService.detail(id);
        return R.ok().put("entity", bindCbsCtbImport);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bind:cbsctbimport:delete")
    public R delete(@RequestBody Long[] ids) {
        bindCbsCtbImportService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
