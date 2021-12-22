package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsDirectoryIntermediateProductEntity;
import io.renren.modules.cbs.service.CbsDirectoryIntermediateProductService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-28 15:32:13
 */
@RestController
@RequestMapping("cbs/directory/intermediateproduct")
public class CbsDirectoryIntermediateProductController {
    private CbsDirectoryIntermediateProductService cbsDirectoryIntermediateProductService;

    public CbsDirectoryIntermediateProductController(
            CbsDirectoryIntermediateProductService cbsDirectoryIntermediateProductService
    ) {
        this.cbsDirectoryIntermediateProductService = cbsDirectoryIntermediateProductService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:directory:intermediateproduct:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsDirectoryIntermediateProductService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:directory:intermediateproduct:info")
    public R info(@PathVariable("id") Long id) {
        CbsDirectoryIntermediateProductEntity cbsDirectoryIntermediateProduct = cbsDirectoryIntermediateProductService.getById(id);

        return R.ok().put("cbsDirectoryIntermediateProduct", cbsDirectoryIntermediateProduct);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:directory:intermediateproduct:save")
    public R save(@RequestBody CbsDirectoryIntermediateProductEntity cbsDirectoryIntermediateProduct) {
        cbsDirectoryIntermediateProductService.save(cbsDirectoryIntermediateProduct);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:directory:intermediateproduct:update")
    public R update(@RequestBody CbsDirectoryIntermediateProductEntity cbsDirectoryIntermediateProduct) {
        cbsDirectoryIntermediateProductService.updateById(cbsDirectoryIntermediateProduct);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:directoryintermediateproduct:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsDirectoryIntermediateProductService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
