package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutItemEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsOutItemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 原料出库表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@RestController
@RequestMapping("cbs/storegoodsoutitem")
public class CbsStoreGoodsOutItemController {

    private CbsStoreGoodsOutItemService cbsStoreGoodsOutItemService;

    @Autowired
    public void setCbsStoreGoodsOutItemService(CbsStoreGoodsOutItemService cbsStoreGoodsOutItemService) {
        this.cbsStoreGoodsOutItemService = cbsStoreGoodsOutItemService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:storegoodsoutitem:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsStoreGoodsOutItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoodsoutitem:info")
    public R info(@PathVariable("id") Long id) {
        CbsStoreGoodsOutItemEntity cbsStoreGoodsOutItem = cbsStoreGoodsOutItemService.getById(id);

        return R.ok().put("cbsStoreGoodsOutItem", cbsStoreGoodsOutItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:storegoodsoutitem:save")
    public R save(@RequestBody CbsStoreGoodsOutItemEntity cbsStoreGoodsOutItem) {
        cbsStoreGoodsOutItemService.save(cbsStoreGoodsOutItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoodsoutitem:update")
    public R update(@RequestBody CbsStoreGoodsOutItemEntity cbsStoreGoodsOutItem) {
        cbsStoreGoodsOutItemService.updateById(cbsStoreGoodsOutItem);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoodsoutitem:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsStoreGoodsOutItemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
