package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrEmlTypeEntity;
import io.renren.modules.thr.service.ThrEmlTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;



/**
 * 加工贸易手册类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-16 11:36:01
 */
@RestController
@RequestMapping("thr/emltype")
public class ThrEmlTypeController {
    @Autowired
    private ThrEmlTypeService thrEmlTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:emltype:list")
    public R list(@RequestBody ThrEmlTypeEntity entity){
        PageUtils page = thrEmlTypeService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:emltype:info")
    public R info(@PathVariable("id") Long id){
		ThrEmlTypeEntity thrEmlType = thrEmlTypeService.getById(id);

        return R.ok().put("thrEmlType", thrEmlType);
    }

    /**
     * 通过code获取，必须包含 code
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:emltype:info")
    public R infoByCode(@RequestBody ThrEmlTypeEntity entity) {
        if (entity.getCode() != null) {
            return R.ok().put("entity", thrEmlTypeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:emltype:save")
    public R save(@RequestBody ThrEmlTypeEntity thrEmlType){
		thrEmlTypeService.save(thrEmlType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:emltype:update")
    public R update(@RequestBody ThrEmlTypeEntity thrEmlType){
		thrEmlTypeService.updateById(thrEmlType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:emltype:delete")
    public R delete(@RequestBody Long[] ids){
		thrEmlTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
