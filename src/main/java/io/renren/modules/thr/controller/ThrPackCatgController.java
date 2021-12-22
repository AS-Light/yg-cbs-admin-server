package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrPackCatgEntity;
import io.renren.modules.thr.service.ThrPackCatgService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 包装种类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/packcatg")
public class ThrPackCatgController {
    @Autowired
    private ThrPackCatgService thrPackCatgService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:packcatg:list")
    public R list(@RequestBody ThrPackCatgEntity entity) {
        PageUtils page = thrPackCatgService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:packcatg:info")
    public R info(@PathVariable("id") Integer id) {
        ThrPackCatgEntity thrPackCatg = thrPackCatgService.getById(id);

        return R.ok().put("thrPackCatg", thrPackCatg);
    }

    /**
     * 通过code获取，必须包含 packCatgCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:packcatg:info")
    public R infoByCode(@RequestBody ThrPackCatgEntity entity) {
        if (entity.getPackCatgCode() != null) {
            return R.ok().put("entity", thrPackCatgService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:packcatg:save")
    public R save(@RequestBody ThrPackCatgEntity thrPackCatg) {
        thrPackCatgService.save(thrPackCatg);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:packcatg:update")
    public R update(@RequestBody ThrPackCatgEntity thrPackCatg) {
        thrPackCatgService.updateById(thrPackCatg);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:packcatg:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrPackCatgService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
