package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrCntnrModeEntity;
import io.renren.modules.thr.service.ThrCntnrModeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 集装箱规格代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/cntnrmode")
public class ThrCntnrModeController {
    @Autowired
    private ThrCntnrModeService thrCntnrModeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:cntnrmode:list")
    public R list(@RequestBody ThrCntnrModeEntity entity) {
        PageUtils page = thrCntnrModeService.queryIndex(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:cntnrmode:info")
    public R info(@PathVariable("id") Integer id) {
        ThrCntnrModeEntity thrCntnrMode = thrCntnrModeService.getById(id);

        return R.ok().put("thrCntnrMode", thrCntnrMode);
    }

    /**
     * 通过code获取，必须包含 cntnrModeCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:cntnrmode:info")
    public R infoByCode(@RequestBody ThrCntnrModeEntity entity) {
        if (entity.getCntnrModeCode() != null) {
            return R.ok().put("entity", thrCntnrModeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:cntnrmode:save")
    public R save(@RequestBody ThrCntnrModeEntity thrCntnrMode) {
        thrCntnrModeService.save(thrCntnrMode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:cntnrmode:update")
    public R update(@RequestBody ThrCntnrModeEntity thrCntnrMode) {
        thrCntnrModeService.updateById(thrCntnrMode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:cntnrmode:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrCntnrModeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
