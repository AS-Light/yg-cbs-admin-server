package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrCiqCodeEntity;
import io.renren.modules.thr.service.ThrCiqCodeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 14:42:22
 */
@RestController
@RequestMapping("thr/ciqcode")
public class ThrCiqCodeController {
    @Autowired
    private ThrCiqCodeService thrCiqCodeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:ciqcode:list")
    public R list(@RequestBody ThrCiqCodeEntity entity) {
        PageUtils page = thrCiqCodeService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:ciqcode:info")
    public R info(@PathVariable("id") Integer id) {
        ThrCiqCodeEntity thrCiqCode = thrCiqCodeService.getById(id);

        return R.ok().put("thrCiqCode", thrCiqCode);
    }

    /**
     * 通过code获取，必须同时包含 hsCode和ciqCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:ciqcode:info")
    public R infoByCode(@RequestBody ThrCiqCodeEntity entity) {
        if (entity.getHsCode() != null && entity.getCiqCode() != null ) {
            return R.ok().put("entity", thrCiqCodeService.getOneByCode(entity));
        } else {
            return R.error("海关编码和CIQ编码必须同时满足");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:ciqcode:save")
    public R save(@RequestBody ThrCiqCodeEntity thrCiqCode) {
        thrCiqCodeService.save(thrCiqCode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:ciqcode:update")
    public R update(@RequestBody ThrCiqCodeEntity thrCiqCode) {
        thrCiqCodeService.updateById(thrCiqCode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:ciqcode:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrCiqCodeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
