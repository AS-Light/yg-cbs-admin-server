package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrLicenseDocusEntity;
import io.renren.modules.thr.service.ThrLicenseDocusService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 随附单证类型代码表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-06 18:13:02
 */
@RestController
@RequestMapping("thr/licensedocus")
public class ThrLicenseDocusController {
    @Autowired
    private ThrLicenseDocusService thrLicenseDocusService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:licensedocus:list")
    public R list(@RequestBody ThrLicenseDocusEntity entity) {
        PageUtils page = thrLicenseDocusService.queryIndex(entity);

        return R.ok().put("page", page);
    }

    /**
     * 通过code获取，必须包含 unitCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:licensedocus:info")
    public R infoByCode(@RequestBody ThrLicenseDocusEntity entity) {
        if (entity.getCode() != null) {
            return R.ok().put("entity", thrLicenseDocusService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:licensedocus:info")
    public R info(@PathVariable("id") Long id) {
        ThrLicenseDocusEntity thrLicenseDocus = thrLicenseDocusService.getById(id);

        return R.ok().put("thrLicenseDocus", thrLicenseDocus);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:licensedocus:save")
    public R save(@RequestBody ThrLicenseDocusEntity thrLicenseDocus) {
        thrLicenseDocusService.save(thrLicenseDocus);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:licensedocus:update")
    public R update(@RequestBody ThrLicenseDocusEntity thrLicenseDocus) {
        thrLicenseDocusService.updateById(thrLicenseDocus);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:licensedocus:delete")
    public R delete(@RequestBody Long[] ids) {
        thrLicenseDocusService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
