package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrHsUnitEntity;
import io.renren.modules.thr.service.ThrHsUnitService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * HS标准计量单位
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/hsunit")
public class ThrHsUnitController {
    @Autowired
    private ThrHsUnitService thrHsUnitService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:hsunit:list")
    public R list(@RequestBody ThrHsUnitEntity entity){
        PageUtils page = thrHsUnitService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:hsunit:info")
    public R info(@PathVariable("id") Integer id){
		ThrHsUnitEntity thrHsUnit = thrHsUnitService.getById(id);

        return R.ok().put("thrHsUnit", thrHsUnit);
    }

    /**
     * 通过code获取，必须包含 unitCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:hsunit:info")
    public R infoByCode(@RequestBody ThrHsUnitEntity entity) {
        if (entity.getUnitCode() != null) {
            return R.ok().put("entity", thrHsUnitService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:hsunit:save")
    public R save(@RequestBody ThrHsUnitEntity thrHsUnit){
		thrHsUnitService.save(thrHsUnit);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:hsunit:update")
    public R update(@RequestBody ThrHsUnitEntity thrHsUnit){
		thrHsUnitService.updateById(thrHsUnit);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:hsunit:delete")
    public R delete(@RequestBody Integer[] ids){
		thrHsUnitService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
