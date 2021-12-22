package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrCiqDomeOriginEntity;
import io.renren.modules.thr.service.ThrCiqDomeOriginService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 国内原产地编码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/ciqdomeorigin")
public class ThrCiqDomeOriginController {
    @Autowired
    private ThrCiqDomeOriginService thrCiqDomeOriginService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:ciqdomeorigin:list")
    public R list(@RequestBody ThrCiqDomeOriginEntity entity) {
        PageUtils page = thrCiqDomeOriginService.queryIndex(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:ciqdomeorigin:info")
    public R info(@PathVariable("id") Integer id) {
        ThrCiqDomeOriginEntity thrCiqDomeOrigin = thrCiqDomeOriginService.getById(id);
        return R.ok().put("thrCiqDomeOrigin", thrCiqDomeOrigin);
    }

    /**
     * 通过code获取，必须包含 ciqDomeOriginCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:ciqdomeorigin:info")
    public R infoByCode(@RequestBody ThrCiqDomeOriginEntity entity) {
        if (entity.getCiqDomeOriginCode() != null) {
            return R.ok().put("entity", thrCiqDomeOriginService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:ciqdomeorigin:save")
    public R save(@RequestBody ThrCiqDomeOriginEntity thrCiqDomeOrigin) {
        thrCiqDomeOriginService.save(thrCiqDomeOrigin);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:ciqdomeorigin:update")
    public R update(@RequestBody ThrCiqDomeOriginEntity thrCiqDomeOrigin) {
        thrCiqDomeOriginService.updateById(thrCiqDomeOrigin);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:ciqdomeorigin:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrCiqDomeOriginService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
