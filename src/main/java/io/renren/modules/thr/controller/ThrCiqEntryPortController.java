package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrCiqEntryPortEntity;
import io.renren.modules.thr.service.ThrCiqEntryPortService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 国内口岸（入境口岸）代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/ciqentryport")
public class ThrCiqEntryPortController {
    @Autowired
    private ThrCiqEntryPortService thrCiqEntryPortService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:ciqentryport:list")
    public R list(@RequestBody ThrCiqEntryPortEntity entity) {
        PageUtils page = thrCiqEntryPortService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:ciqentryport:info")
    public R info(@PathVariable("id") Integer id) {
        ThrCiqEntryPortEntity thrCiqEntryPort = thrCiqEntryPortService.getById(id);

        return R.ok().put("thrCiqEntryPort", thrCiqEntryPort);
    }

    /**
     * 通过code获取，必须包含 ciqEntyPortCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:ciqentryport:info")
    public R infoByCode(@RequestBody ThrCiqEntryPortEntity entity) {
        if (entity.getCiqEntyPortCode() != null) {
            return R.ok().put("entity", thrCiqEntryPortService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:ciqentryport:save")
    public R save(@RequestBody ThrCiqEntryPortEntity thrCiqEntryPort) {
        thrCiqEntryPortService.save(thrCiqEntryPort);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:ciqentryport:update")
    public R update(@RequestBody ThrCiqEntryPortEntity thrCiqEntryPort) {
        thrCiqEntryPortService.updateById(thrCiqEntryPort);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:ciqentryport:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrCiqEntryPortService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
