package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrPortEntity;
import io.renren.modules.thr.service.ThrPortService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * 国际口岸（亦表示：港口、启运口岸、经停口岸、国际抵达口岸等）
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/port")
public class ThrPortController {
    @Autowired
    private ThrPortService thrPortService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:port:list")
    public R list(@RequestBody ThrPortEntity entity) {
        PageUtils page = thrPortService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    /**
     * 列表（全部）
     */
    @RequestMapping("/listAll")
    @RequiresPermissions("thr:port:list")
    public R listAll() {
        List<ThrPortEntity> list = thrPortService.list();
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:port:info")
    public R info(@PathVariable("id") Integer id) {
        ThrPortEntity thrPort = thrPortService.getById(id);

        return R.ok().put("thrPort", thrPort);
    }

    /**
     * 通过code获取，必须包含 portCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:port:info")
    public R infoByCode(@RequestBody ThrPortEntity entity) {
        if (entity.getPortCode() != null) {
            return R.ok().put("entity", thrPortService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:port:save")
    public R save(@RequestBody ThrPortEntity thrPort) {
        thrPortService.save(thrPort);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:port:update")
    public R update(@RequestBody ThrPortEntity thrPort) {
        thrPortService.updateById(thrPort);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:port:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrPortService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
