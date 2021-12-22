package io.renren.modules.ctb.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbPriceListTempleteEntity;
import io.renren.modules.ctb.service.CtbPriceListTempleteService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 报关行报价单模板，原则上报关行只有一个模板，有一个ctb_tenant_id=-1的模板作为公共模板
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/pricelisttemplete")
public class CtbPriceListTempleteController {
    private CtbPriceListTempleteService ctbPriceListTempleteService;

    public CtbPriceListTempleteController(
            CtbPriceListTempleteService ctbPriceListTempleteService
    ) {
        this.ctbPriceListTempleteService = ctbPriceListTempleteService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:pricelisttemplete:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = ctbPriceListTempleteService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:pricelisttemplete:info")
    public R info(@PathVariable("id") Long id) {
        CtbPriceListTempleteEntity ctbPriceListTemplete = ctbPriceListTempleteService.getById(id);
        return R.ok().put("ctbPriceListTemplete", ctbPriceListTemplete);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:pricelisttemplete:save")
    public R save(@RequestBody CtbPriceListTempleteEntity ctbPriceListTemplete) {
        ctbPriceListTempleteService.save(ctbPriceListTemplete);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:pricelisttemplete:update")
    public R update(@RequestBody CtbPriceListTempleteEntity ctbPriceListTemplete) {
        ctbPriceListTempleteService.updateById(ctbPriceListTemplete);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:pricelisttemplete:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbPriceListTempleteService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
