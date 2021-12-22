package io.renren.modules.thr.controller;

import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrDeclPortEntity;
import io.renren.modules.thr.service.ThrDeclPortService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 国内关区（关别）代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/declport")
public class ThrDeclPortController {
    @Autowired
    private ThrDeclPortService thrDeclPortService;

    /**
     * All列表
     */
    @RequestMapping("/listAll")
    @RequiresPermissions("thr:declport:list")
    public R listAll(){
        return R.ok().put("list", thrDeclPortService.list());
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:declport:list")
    public R list(@RequestBody ThrDeclPortEntity entity){
        return R.ok().put("page", thrDeclPortService.queryIndex(entity));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:declport:info")
    public R info(@PathVariable("id") Integer id){
		ThrDeclPortEntity thrDeclPort = thrDeclPortService.getById(id);

        return R.ok().put("thrDeclPort", thrDeclPort);
    }

    /**
     * 通过code获取，必须包含 declPort
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:declport:info")
    public R infoByCode(@RequestBody ThrDeclPortEntity entity) {
        if (entity.getDeclPort() != null) {
            return R.ok().put("entity", thrDeclPortService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:declport:save")
    public R save(@RequestBody ThrDeclPortEntity thrDeclPort){
		thrDeclPortService.save(thrDeclPort);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:declport:update")
    public R update(@RequestBody ThrDeclPortEntity thrDeclPort){
		thrDeclPortService.updateById(thrDeclPort);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:declport:delete")
    public R delete(@RequestBody Integer[] ids){
		thrDeclPortService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
