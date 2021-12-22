package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrHsCodeEntity;
import io.renren.modules.thr.service.ThrHsCodeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 14:42:21
 */
@RestController
@RequestMapping("thr/hscode")
public class ThrHsCodeController {
    @Autowired
    private ThrHsCodeService thrHsCodeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:hscode:list")
    public R list(@RequestBody ThrHsCodeEntity entity) {
        PageUtils page = thrHsCodeService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    /**
     * 列表全部
     */
    @RequestMapping("/listAll")
    @RequiresPermissions("thr:hscode:list")
    public R listAll() {
        List<ThrHsCodeEntity> list = thrHsCodeService.list();
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:hscode:info")
    public R info(@PathVariable("id") Integer id) {
        ThrHsCodeEntity thrHsCode = thrHsCodeService.getById(id);

        return R.ok().put("thrHsCode", thrHsCode);
    }

    /**
     * 通过code获取，必须包含 hsCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:hscode:info")
    public R infoByCode(@RequestBody ThrHsCodeEntity entity) {
        if (entity.getHsCode() != null) {
            return R.ok().put("entity", thrHsCodeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:hscode:save")
    public R save(@RequestBody ThrHsCodeEntity thrHsCode) {
        thrHsCodeService.save(thrHsCode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:hscode:update")
    public R update(@RequestBody ThrHsCodeEntity thrHsCode) {
        thrHsCodeService.updateById(thrHsCode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:hscode:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrHsCodeService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
