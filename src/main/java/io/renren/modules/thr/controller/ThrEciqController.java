package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrEciqEntity;
import io.renren.modules.thr.service.ThrEciqService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 中华人民共和国行政区划代码表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/eciq")
public class ThrEciqController {
    @Autowired
    private ThrEciqService thrEciqService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:eciq:list")
    public R list(@RequestBody ThrEciqEntity entity){
        PageUtils page = thrEciqService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:eciq:info")
    public R info(@PathVariable("id") Integer id){
		ThrEciqEntity thrEciq = thrEciqService.getById(id);

        return R.ok().put("thrEciq", thrEciq);
    }

    /**
     * 通过code获取，必须包含 eciqCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:eciq:info")
    public R infoByCode(@RequestBody ThrEciqEntity entity) {
        if (entity.getEciqCode() != null) {
            return R.ok().put("entity", thrEciqService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:eciq:save")
    public R save(@RequestBody ThrEciqEntity thrEciq){
		thrEciqService.save(thrEciq);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:eciq:update")
    public R update(@RequestBody ThrEciqEntity thrEciq){
		thrEciqService.updateById(thrEciq);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:eciq:delete")
    public R delete(@RequestBody Integer[] ids){
		thrEciqService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
