package io.renren.modules.cbs.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.cbs.entity.CbsPurchaseGoodsEntity;
import io.renren.modules.cbs.service.CbsPurchaseGoodsService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 出口货物清单
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@RestController
@RequestMapping("cbs/purchasegoods")
public class CbsPurchaseGoodsController {
    @Autowired
    private CbsPurchaseGoodsService cbsPurchaseGoodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:purchasegoods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsPurchaseGoodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:purchasegoods:info")
    public R info(@PathVariable("id") Long id){
		CbsPurchaseGoodsEntity cbsPurchaseGoods = cbsPurchaseGoodsService.getById(id);

        return R.ok().put("cbsPurchaseGoods", cbsPurchaseGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:purchasegoods:save")
    public R save(@RequestBody CbsPurchaseGoodsEntity cbsPurchaseGoods){
		cbsPurchaseGoodsService.save(cbsPurchaseGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:purchasegoods:update")
    public R update(@RequestBody CbsPurchaseGoodsEntity cbsPurchaseGoods){
		cbsPurchaseGoodsService.updateById(cbsPurchaseGoods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:purchasegoods:delete")
    public R delete(@RequestBody Long[] ids){
		cbsPurchaseGoodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
