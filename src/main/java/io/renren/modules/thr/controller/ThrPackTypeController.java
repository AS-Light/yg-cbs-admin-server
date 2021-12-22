package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrPackTypeEntity;
import io.renren.modules.thr.service.ThrPackTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 危包规格代码、辅助包装代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/packtype")
public class ThrPackTypeController {
    @Autowired
    private ThrPackTypeService thrPackTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:packtype:list")
    public R list(@RequestBody ThrPackTypeEntity entity){
        PageUtils page = thrPackTypeService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:packtype:info")
    public R info(@PathVariable("id") Integer id){
		ThrPackTypeEntity thrPackType = thrPackTypeService.getById(id);

        return R.ok().put("thrPackType", thrPackType);
    }

    /**
     * 通过code获取，必须包含 packTypeCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:packtype:info")
    public R infoByCode(@RequestBody ThrPackTypeEntity entity) {
        if (entity.getPackTypeCode() != null) {
            return R.ok().put("entity", thrPackTypeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:packtype:save")
    public R save(@RequestBody ThrPackTypeEntity thrPackType){
		thrPackTypeService.save(thrPackType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:packtype:update")
    public R update(@RequestBody ThrPackTypeEntity thrPackType){
		thrPackTypeService.updateById(thrPackType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:packtype:delete")
    public R delete(@RequestBody Integer[] ids){
		thrPackTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
