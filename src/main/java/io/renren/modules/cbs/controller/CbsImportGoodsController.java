package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsImportGoodsEntity;
import io.renren.modules.cbs.service.CbsImportGoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 原材进货记录
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-18 15:18:27
 */
@RestController
@RequestMapping("cbs/importgoods")
public class CbsImportGoodsController {

    private CbsImportGoodsService cbsImportGoodsService;

    @Autowired
    public void setCbsImportGoodsService(CbsImportGoodsService cbsImportGoodsService) {
        this.cbsImportGoodsService = cbsImportGoodsService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:importgoods:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsImportGoodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:importgoods:info")
    public R info(@PathVariable("id") Long id) {
        CbsImportGoodsEntity cbsImportGoods = cbsImportGoodsService.selectById(id);
        return R.ok().put("cbsImportGoods", cbsImportGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:importgoods:save")
    public R save(@RequestBody CbsImportGoodsEntity cbsImportGoods) {
        cbsImportGoodsService.save(cbsImportGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:importgoods:update")
    public R update(@RequestBody CbsImportGoodsEntity cbsImportGoods) {
        cbsImportGoodsService.updateById(cbsImportGoods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:importgoods:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsImportGoodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
