package io.renren.modules.thr.controller;

import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
import io.renren.modules.thr.service.ThrCurrencyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 16:55:11
 */
@RestController
@RequestMapping("thr/currency")
public class ThrCurrencyController {
    @Autowired
    private ThrCurrencyService thrCurrencyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:currency:list")
    public R list(@RequestBody ThrCurrencyEntity entity) {
        return R.ok().put("currencyList", thrCurrencyService.queryIndex(entity));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:currency:info")
    public R info(@PathVariable("id") Integer id) {
        ThrCurrencyEntity thrCurrency = thrCurrencyService.getById(id);

        return R.ok().put("thrCurrency", thrCurrency);
    }

    /**
     * 通过code获取，必须包含 code
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:currency:info")
    public R infoByCode(@RequestBody ThrCurrencyEntity entity) {
        if (entity.getCode() != null) {
            return R.ok().put("entity", thrCurrencyService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:currency:save")
    public R save(@RequestBody ThrCurrencyEntity thrCurrency) {
        thrCurrencyService.save(thrCurrency);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:currency:update")
    public R update(@RequestBody ThrCurrencyEntity thrCurrency) {
        thrCurrencyService.updateById(thrCurrency);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:currency:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrCurrencyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
