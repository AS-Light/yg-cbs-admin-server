package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrTradeModeEntity;
import io.renren.modules.thr.service.ThrTradeModeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 监管方式代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/trademode")
public class ThrTradeModeController {
    @Autowired
    private ThrTradeModeService thrTradeModeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:trademode:list")
    public R list(@RequestBody ThrTradeModeEntity entity) {
        PageUtils page = thrTradeModeService.queryIndex(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:trademode:info")
    public R info(@PathVariable("id") Integer id) {
        ThrTradeModeEntity thrTradeMode = thrTradeModeService.getById(id);

        return R.ok().put("thrTradeMode", thrTradeMode);
    }

    /**
     * 通过code获取，必须包含 tradeModeCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:purpose:info")
    public R infoByCode(@RequestBody ThrTradeModeEntity entity) {
        if (entity.getTradeModeCode() != null) {
            return R.ok().put("entity", thrTradeModeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:trademode:save")
    public R save(@RequestBody ThrTradeModeEntity thrTradeMode) {
        thrTradeModeService.save(thrTradeMode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:trademode:update")
    public R update(@RequestBody ThrTradeModeEntity thrTradeMode) {
        thrTradeModeService.updateById(thrTradeMode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:trademode:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrTradeModeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
