package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrCutModeEntity;
import io.renren.modules.thr.service.ThrCutModeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 征免性质表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/cutmode")
public class ThrCutModeController {
    @Autowired
    private ThrCutModeService thrCutModeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:cutmode:list")
    public R list(@RequestBody ThrCutModeEntity entity){
        PageUtils page = thrCutModeService.queryIndex(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:cutmode:info")
    public R info(@PathVariable("id") Integer id){
		ThrCutModeEntity thrCutMode = thrCutModeService.getById(id);

        return R.ok().put("thrCutMode", thrCutMode);
    }

    /**
     * 通过code获取，必须包含 cutModeCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:cutmode:info")
    public R infoByCode(@RequestBody ThrCutModeEntity entity) {
        if (entity.getCutModeCode() != null) {
            return R.ok().put("entity", thrCutModeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:cutmode:save")
    public R save(@RequestBody ThrCutModeEntity thrCutMode){
		thrCutModeService.save(thrCutMode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:cutmode:update")
    public R update(@RequestBody ThrCutModeEntity thrCutMode){
		thrCutModeService.updateById(thrCutMode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:cutmode:delete")
    public R delete(@RequestBody Integer[] ids){
		thrCutModeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
