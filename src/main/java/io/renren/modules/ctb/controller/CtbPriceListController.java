package io.renren.modules.ctb.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ctb.entity.CtbPriceListEntity;
import io.renren.modules.ctb.service.CtbPriceListService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 报关行报价单表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/pricelist")
public class CtbPriceListController {
    @Autowired
    private CtbPriceListService ctbPriceListService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:pricelist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ctbPriceListService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:pricelist:info")
    public R info(@PathVariable("id") Long id){
		CtbPriceListEntity ctbPriceList = ctbPriceListService.getById(id);

        return R.ok().put("ctbPriceList", ctbPriceList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:pricelist:save")
    public R save(@RequestBody CtbPriceListEntity ctbPriceList){
		ctbPriceListService.save(ctbPriceList);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:pricelist:update")
    public R update(@RequestBody CtbPriceListEntity ctbPriceList){
		ctbPriceListService.updateById(ctbPriceList);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:pricelist:delete")
    public R delete(@RequestBody Long[] ids){
		ctbPriceListService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
