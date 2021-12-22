package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrLicTypeEntity;
import io.renren.modules.thr.service.ThrLicTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 企业产品许可类别代码表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/lictype")
public class ThrLicTypeController {
    @Autowired
    private ThrLicTypeService thrLicTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:lictype:list")
    public R list(@RequestBody ThrLicTypeEntity entity) {
        PageUtils page = thrLicTypeService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:lictype:info")
    public R info(@PathVariable("id") Integer id) {
        ThrLicTypeEntity thrLicType = thrLicTypeService.getById(id);

        return R.ok().put("thrLicType", thrLicType);
    }

    /**
     * 通过code获取，必须包含 licTypeCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:lictype:info")
    public R infoByCode(@RequestBody ThrLicTypeEntity entity) {
        if (entity.getLicTypeCode() != null) {
            return R.ok().put("entity", thrLicTypeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:lictype:save")
    public R save(@RequestBody ThrLicTypeEntity thrLicType) {
        thrLicTypeService.save(thrLicType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:lictype:update")
    public R update(@RequestBody ThrLicTypeEntity thrLicType) {
        thrLicTypeService.updateById(thrLicType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:lictype:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrLicTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
