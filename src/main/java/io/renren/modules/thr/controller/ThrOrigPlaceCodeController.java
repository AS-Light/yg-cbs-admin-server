package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrOrigPlaceCodeEntity;
import io.renren.modules.thr.service.ThrOrigPlaceCodeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * 原产地区代码表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/origplacecode")
public class ThrOrigPlaceCodeController {
    @Autowired
    private ThrOrigPlaceCodeService thrOrigPlaceCodeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:origplacecode:list")
    public R list(@RequestBody ThrOrigPlaceCodeEntity entity) {
        PageUtils page = thrOrigPlaceCodeService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    /**
     * 列表（All）
     */
    @RequestMapping("/listAll")
    @RequiresPermissions("thr:origplacecode:list")
    public R list() {
        List<ThrOrigPlaceCodeEntity> list = thrOrigPlaceCodeService.list();
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:origplacecode:info")
    public R info(@PathVariable("id") Integer id) {
        ThrOrigPlaceCodeEntity thrOrigPlaceCode = thrOrigPlaceCodeService.getById(id);

        return R.ok().put("thrOrigPlaceCode", thrOrigPlaceCode);
    }

    /**
     * 通过code获取，必须包含 origPlaceCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:origplacecode:info")
    public R infoByCode(@RequestBody ThrOrigPlaceCodeEntity entity) {
        if (entity.getOrigPlaceCode() != null) {
            return R.ok().put("entity", thrOrigPlaceCodeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:origplacecode:save")
    public R save(@RequestBody ThrOrigPlaceCodeEntity thrOrigPlaceCode) {
        thrOrigPlaceCodeService.save(thrOrigPlaceCode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:origplacecode:update")
    public R update(@RequestBody ThrOrigPlaceCodeEntity thrOrigPlaceCode) {
        thrOrigPlaceCodeService.updateById(thrOrigPlaceCode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:origplacecode:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrOrigPlaceCodeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
