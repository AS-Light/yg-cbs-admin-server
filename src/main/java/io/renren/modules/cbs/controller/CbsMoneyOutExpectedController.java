package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.cbs.entity.CbsMoneyOutExpectedEntity;
import io.renren.modules.cbs.service.CbsMoneyOutExpectedService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 合同审核通过后的预计支出流水
 *
 * @author ChenNing
 * @email record_7@126.com
 * @date 2020-02-18 11:16:49
 */
@RestController
@RequestMapping("cbs/cbsmoneyoutexpected")
public class CbsMoneyOutExpectedController {
    @Autowired
    private CbsMoneyOutExpectedService cbsMoneyOutExpectedService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:cbsmoneyoutexpected:list")
    public R list(@RequestBody CbsMoneyOutExpectedEntity entity) {
        PageUtils page = cbsMoneyOutExpectedService.queryPage(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:cbsmoneyoutexpected:info")
    public R info(@PathVariable("id") Long id) {
        CbsMoneyOutExpectedEntity cbsMoneyOutExpected = cbsMoneyOutExpectedService.getById(id);

        return R.ok().put("cbsMoneyOutExpected", cbsMoneyOutExpected);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:cbsmoneyoutexpected:save")
    public R save(@RequestBody CbsMoneyOutExpectedEntity cbsMoneyOutExpected) {
        cbsMoneyOutExpectedService.save(cbsMoneyOutExpected);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:cbsmoneyoutexpected:update")
    public R update(@RequestBody CbsMoneyOutExpectedEntity cbsMoneyOutExpected) {
        ValidatorUtils.validateEntity(cbsMoneyOutExpected);
        cbsMoneyOutExpectedService.updateById(cbsMoneyOutExpected);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:cbsmoneyoutexpected:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsMoneyOutExpectedService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
