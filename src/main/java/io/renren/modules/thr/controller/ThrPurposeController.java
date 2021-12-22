package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrPurposeEntity;
import io.renren.modules.thr.service.ThrPurposeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 用途代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/purpose")
public class ThrPurposeController {
    @Autowired
    private ThrPurposeService thrPurposeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:purpose:list")
    public R list(@RequestBody ThrPurposeEntity entity) {
        PageUtils page = thrPurposeService.queryIndex(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:purpose:info")
    public R info(@PathVariable("id") Integer id) {
        ThrPurposeEntity thrPurpose = thrPurposeService.getById(id);

        return R.ok().put("thrPurpose", thrPurpose);
    }

    /**
     * 通过code获取，必须包含 purposeCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:purpose:info")
    public R infoByCode(@RequestBody ThrPurposeEntity entity) {
        if (entity.getPurposeCode() != null) {
            return R.ok().put("entity", thrPurposeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:purpose:save")
    public R save(@RequestBody ThrPurposeEntity thrPurpose) {
        thrPurposeService.save(thrPurpose);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:purpose:update")
    public R update(@RequestBody ThrPurposeEntity thrPurpose) {
        thrPurposeService.updateById(thrPurpose);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:purpose:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrPurposeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
