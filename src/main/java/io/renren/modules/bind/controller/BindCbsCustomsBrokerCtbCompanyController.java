package io.renren.modules.bind.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.renren.modules.bind.service.BindCbsCustomsBrokerCtbCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 17:06:26
 */
@RestController
@RequestMapping("bind/cbsCustomsBrokerCtbCompany")
public class BindCbsCustomsBrokerCtbCompanyController {
    @Autowired
    private BindCbsCustomsBrokerCtbCompanyService bindCbsCustomsBrokerCtbCompanyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bind:cbscustomsbrokerctbcompany:list")
    public R list(@RequestBody BindCbsCustomsBrokerCtbCompanyEntity entity){
        PageUtils page = bindCbsCustomsBrokerCtbCompanyService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bind:cbscustomsbrokerctbcompany:info")
    public R info(@PathVariable("id") Long id){
		BindCbsCustomsBrokerCtbCompanyEntity bindCbsCustomsBrokerCtbCompany = bindCbsCustomsBrokerCtbCompanyService.getById(id);
        return R.ok().put("bindCbsCustomsBrokerCtbCompany", bindCbsCustomsBrokerCtbCompany);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bind:cbscustomsbrokerctbcompany:save")
    public R save(@RequestBody BindCbsCustomsBrokerCtbCompanyEntity bindCbsCustomsBrokerCtbCompany){
		bindCbsCustomsBrokerCtbCompanyService.save(bindCbsCustomsBrokerCtbCompany);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bind:cbscustomsbrokerctbcompany:update")
    public R update(@RequestBody BindCbsCustomsBrokerCtbCompanyEntity bindCbsCustomsBrokerCtbCompany){
		bindCbsCustomsBrokerCtbCompanyService.updateById(bindCbsCustomsBrokerCtbCompany);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bind:cbscustomsbrokerctbcompany:delete")
    public R delete(@RequestBody Long[] ids){
		bindCbsCustomsBrokerCtbCompanyService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
