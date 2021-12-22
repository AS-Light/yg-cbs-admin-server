package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrTransModeEntity;
import io.renren.modules.thr.service.ThrTransModeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 成交方式
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/transmode")
public class ThrTransModeController {
    @Autowired
    private ThrTransModeService thrTransModeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:transmode:list")
    public R list(@RequestBody ThrTransModeEntity entity) {
        PageUtils page = thrTransModeService.queryIndex(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:transmode:info")
    public R info(@PathVariable("id") Integer id) {
        ThrTransModeEntity thrTransMode = thrTransModeService.getById(id);

        return R.ok().put("thrTransMode", thrTransMode);
    }

    /**
     * 通过code获取，必须包含 transModeCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:transmode:info")
    public R infoByCode(@RequestBody ThrTransModeEntity entity) {
        if (entity.getTransModeCode() != null) {
            return R.ok().put("entity", thrTransModeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:transmode:save")
    public R save(@RequestBody ThrTransModeEntity thrTransMode) {
        thrTransModeService.save(thrTransMode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:transmode:update")
    public R update(@RequestBody ThrTransModeEntity thrTransMode) {
        thrTransModeService.updateById(thrTransMode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:transmode:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrTransModeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
