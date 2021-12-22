package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutExportEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsOutExportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 出口出库表，与cbs_store_goods_out表为1对1继承关系
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@RestController
@RequestMapping("cbs/storegoodsoutexport")
public class CbsStoreGoodsOutExportController {

    private CbsStoreGoodsOutExportService cbsStoreGoodsOutExportService;

    @Autowired
    public void setCbsStoreGoodsOutExportService(CbsStoreGoodsOutExportService cbsStoreGoodsOutExportService) {
        this.cbsStoreGoodsOutExportService = cbsStoreGoodsOutExportService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:storegoodsoutexport:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsStoreGoodsOutExportService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoodsoutexport:info")
    public R info(@PathVariable("id") Long id) {
        CbsStoreGoodsOutExportEntity cbsStoreGoodsOutExport = cbsStoreGoodsOutExportService.getById(id);

        return R.ok().put("cbsStoreGoodsOutExport", cbsStoreGoodsOutExport);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:storegoodsoutexport:save")
    public R save(@RequestBody CbsStoreGoodsOutExportEntity cbsStoreGoodsOutExport) {
        cbsStoreGoodsOutExportService.save(cbsStoreGoodsOutExport);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoodsoutexport:update")
    public R update(@RequestBody CbsStoreGoodsOutExportEntity cbsStoreGoodsOutExport) {
        cbsStoreGoodsOutExportService.updateById(cbsStoreGoodsOutExport);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoodsoutexport:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsStoreGoodsOutExportService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
