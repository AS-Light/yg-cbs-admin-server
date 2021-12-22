package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrTrafModeEntity;
import io.renren.modules.thr.service.ThrTrafModeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 运输方式表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/trafmode")
public class ThrTrafModeController {
    @Autowired
    private ThrTrafModeService thrTrafModeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:trafmode:list")
    public R list(@RequestBody ThrTrafModeEntity entity){
        PageUtils page = thrTrafModeService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:trafmode:info")
    public R info(@PathVariable("id") Integer id){
		ThrTrafModeEntity thrTrafMode = thrTrafModeService.getById(id);

        return R.ok().put("thrTrafMode", thrTrafMode);
    }

    /**
     * 通过code获取，必须包含 trafModeCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:trafmode:info")
    public R infoByCode(@RequestBody ThrTrafModeEntity entity) {
        if (entity.getTrafModeCode() != null) {
            return R.ok().put("entity", thrTrafModeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:trafmode:save")
    public R save(@RequestBody ThrTrafModeEntity thrTrafMode){
		thrTrafModeService.save(thrTrafMode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:trafmode:update")
    public R update(@RequestBody ThrTrafModeEntity thrTrafMode){
		thrTrafModeService.updateById(thrTrafMode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:trafmode:delete")
    public R delete(@RequestBody Integer[] ids){
		thrTrafModeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
