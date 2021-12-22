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

import io.renren.modules.cst.entity.CstDecUserEntity;
import io.renren.modules.cst.service.CstDecUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 进出口报关单 使用人信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@RestController
@RequestMapping("cst/dec/user")
public class CstDecUserController {
    @Autowired
    private CstDecUserService cstDecUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:decuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstDecUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:decuser:info")
    public R info(@PathVariable("id") Long id){
		CstDecUserEntity cstDecUser = cstDecUserService.getById(id);

        return R.ok().put("cstDecUser", cstDecUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:decuser:save")
    public R save(@RequestBody CstDecUserEntity cstDecUser){
		cstDecUserService.save(cstDecUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:decuser:update")
    public R update(@RequestBody CstDecUserEntity cstDecUser){
		cstDecUserService.updateById(cstDecUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:decuser:delete")
    public R delete(@RequestBody Long[] ids){
		cstDecUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
