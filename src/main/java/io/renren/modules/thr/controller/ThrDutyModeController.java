package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrDutyModeEntity;
import io.renren.modules.thr.service.ThrDutyModeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 征免方式
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/dutymode")
public class ThrDutyModeController {
    @Autowired
    private ThrDutyModeService thrDutyModeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:dutymode:list")
    public R list(@RequestBody ThrDutyModeEntity entity){
        PageUtils page = thrDutyModeService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:dutymode:info")
    public R info(@PathVariable("id") Integer id){
		ThrDutyModeEntity thrDutyMode = thrDutyModeService.getById(id);

        return R.ok().put("thrDutyMode", thrDutyMode);
    }

    /**
     * 通过code获取，必须包含 dutyMode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:dutymode:info")
    public R infoByCode(@RequestBody ThrDutyModeEntity entity) {
        if (entity.getDutyMode() != null) {
            return R.ok().put("entity", thrDutyModeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:dutymode:save")
    public R save(@RequestBody ThrDutyModeEntity thrDutyMode){
		thrDutyModeService.save(thrDutyMode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:dutymode:update")
    public R update(@RequestBody ThrDutyModeEntity thrDutyMode){
		thrDutyModeService.updateById(thrDutyMode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:dutymode:delete")
    public R delete(@RequestBody Integer[] ids){
		thrDutyModeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
