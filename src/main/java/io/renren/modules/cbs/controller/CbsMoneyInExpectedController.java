package io.renren.modules.cbs.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.cbs.entity.CbsMoneyInExpectedEntity;
import io.renren.modules.cbs.service.CbsMoneyInExpectedService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 合同审核通过后的预计收入流水
 *
 * @author ChenNing
 * @email record_7@126.com
 * @date 2020-02-18 11:16:49
 */
@RestController
@RequestMapping("cbs/cbsmoneyinexpected")
public class CbsMoneyInExpectedController {
    @Autowired
    private CbsMoneyInExpectedService cbsMoneyInExpectedService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:cbsmoneyinexpected:list")
    public R list(@RequestBody CbsMoneyInExpectedEntity entity){
        PageUtils page = cbsMoneyInExpectedService.queryPage(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:cbsmoneyinexpected:info")
    public R info(@PathVariable("id") Long id){
        CbsMoneyInExpectedEntity cbsMoneyInExpected = cbsMoneyInExpectedService.getById(id);

        return R.ok().put("cbsMoneyInExpected", cbsMoneyInExpected);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:cbsmoneyinexpected:save")
    public R save(@RequestBody CbsMoneyInExpectedEntity cbsMoneyInExpected){
        cbsMoneyInExpectedService.save(cbsMoneyInExpected);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:cbsmoneyinexpected:update")
    public R update(@RequestBody CbsMoneyInExpectedEntity cbsMoneyInExpected){
        ValidatorUtils.validateEntity(cbsMoneyInExpected);
        cbsMoneyInExpectedService.updateById(cbsMoneyInExpected);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:cbsmoneyinexpected:delete")
    public R delete(@RequestBody Long[] ids){
        cbsMoneyInExpectedService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
