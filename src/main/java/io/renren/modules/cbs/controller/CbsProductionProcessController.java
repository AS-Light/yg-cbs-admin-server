package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsProductionProcessEntity;
import io.renren.modules.cbs.service.CbsProductionProcessService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 生产工艺表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-28 15:32:13
 */
@RestController
@RequestMapping("cbs/productionprocess")
public class CbsProductionProcessController {
    private CbsProductionProcessService cbsProductionProcessService;

    public CbsProductionProcessController(
            CbsProductionProcessService cbsProductionProcessService
    ) {
        this.cbsProductionProcessService = cbsProductionProcessService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:productionprocess:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsProductionProcessService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:productionprocess:info")
    public R info(@PathVariable("id") Long id) {
        return R.ok().put("entity", cbsProductionProcessService.detail(id));
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:productionprocess:save")
    public R save(@RequestBody CbsProductionProcessEntity cbsProductionProcess) {
        cbsProductionProcessService.save(cbsProductionProcess);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:productionprocess:update")
    public R update(@RequestBody CbsProductionProcessEntity cbsProductionProcess) {
        cbsProductionProcessService.updateById(cbsProductionProcess);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:productionprocess:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsProductionProcessService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
