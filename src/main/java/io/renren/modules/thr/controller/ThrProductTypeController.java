package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrProductTypeEntity;
import io.renren.modules.thr.service.ThrProductTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;



/**
 * 海关加工种类代码表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-16 12:59:16
 */
@RestController
@RequestMapping("thr/producttype")
public class ThrProductTypeController {
    @Autowired
    private ThrProductTypeService thrProductTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:producttype:list")
    public R list(@RequestBody ThrProductTypeEntity entity){
        PageUtils page = thrProductTypeService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:producttype:info")
    public R info(@PathVariable("id") Long id){
		ThrProductTypeEntity thrProductType = thrProductTypeService.getById(id);

        return R.ok().put("thrProductType", thrProductType);
    }

    /**
     * 通过code获取，必须包含 code
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:producttype:info")
    public R infoByCode(@RequestBody ThrProductTypeEntity entity) {
        if (entity.getCode() != null) {
            return R.ok().put("entity", thrProductTypeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:producttype:save")
    public R save(@RequestBody ThrProductTypeEntity thrProductType){
		thrProductTypeService.save(thrProductType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:producttype:update")
    public R update(@RequestBody ThrProductTypeEntity thrProductType){
		thrProductTypeService.updateById(thrProductType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:producttype:delete")
    public R delete(@RequestBody Long[] ids){
		thrProductTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
