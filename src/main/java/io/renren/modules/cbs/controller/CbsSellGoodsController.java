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

import io.renren.modules.cbs.entity.CbsSellGoodsEntity;
import io.renren.modules.cbs.service.CbsSellGoodsService;
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
@RequestMapping("cbs/sellgoods")
public class CbsSellGoodsController {
    @Autowired
    private CbsSellGoodsService cbsSellGoodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:sellgoods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsSellGoodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:sellgoods:info")
    public R info(@PathVariable("id") Long id){
		CbsSellGoodsEntity cbsSellGoods = cbsSellGoodsService.getById(id);

        return R.ok().put("cbsSellGoods", cbsSellGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:sellgoods:save")
    public R save(@RequestBody CbsSellGoodsEntity cbsSellGoods){
		cbsSellGoodsService.save(cbsSellGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:sellgoods:update")
    public R update(@RequestBody CbsSellGoodsEntity cbsSellGoods){
		cbsSellGoodsService.updateById(cbsSellGoods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:sellgoods:delete")
    public R delete(@RequestBody Long[] ids){
		cbsSellGoodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
