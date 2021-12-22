package io.renren.modules.cst.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.cst.entity.CstDecCopLimitEntity;
import io.renren.modules.cst.service.CstDecCopLimitService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 进出口报关单 企业资质信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@RestController
@RequestMapping("cst/dec/coplimit")
public class CstDecCopLimitController {
    @Autowired
    private CstDecCopLimitService cstDecCopLimitService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:deccoplimit:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstDecCopLimitService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:deccoplimit:info")
    public R info(@PathVariable("id") Long id){
		CstDecCopLimitEntity cstDecCopLimit = cstDecCopLimitService.getById(id);

        return R.ok().put("cstDecCopLimit", cstDecCopLimit);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:deccoplimit:save")
    public R save(@RequestBody CstDecCopLimitEntity cstDecCopLimit){
		cstDecCopLimitService.save(cstDecCopLimit);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:deccoplimit:update")
    public R update(@RequestBody CstDecCopLimitEntity cstDecCopLimit){
		cstDecCopLimitService.updateById(cstDecCopLimit);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:deccoplimit:delete")
    public R delete(@RequestBody Long[] ids){
		cstDecCopLimitService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
