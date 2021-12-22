package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsMoneyTypeEntity;
import io.renren.modules.cbs.service.CbsMoneyTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 收入/支出类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
@RestController
@RequestMapping("cbs/moneytype")
public class CbsMoneyTypeController {
    @Autowired
    private CbsMoneyTypeService cbsMoneyTypeService;

    /**
     * 列表包含自建
     */
    @RequestMapping("/includeSelfBuilt")
    @RequiresPermissions("cbs:moneytype:includeSelfBuilt")
    public R includeSelfBuilt() {
        return R.ok().put("list", cbsMoneyTypeService.includeSelfBuilt());
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:moneytype:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsMoneyTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:moneytype:info")
    public R info(@PathVariable("id") Long id) {
        CbsMoneyTypeEntity cbsMoneyType = cbsMoneyTypeService.getById(id);

        return R.ok().put("cbsMoneyType", cbsMoneyType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:moneytype:save")
    public R save(@RequestBody CbsMoneyTypeEntity cbsMoneyType) {
        cbsMoneyTypeService.save(cbsMoneyType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:moneytype:update")
    public R update(@RequestBody CbsMoneyTypeEntity cbsMoneyType) {
        cbsMoneyTypeService.updateById(cbsMoneyType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:moneytype:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsMoneyTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
