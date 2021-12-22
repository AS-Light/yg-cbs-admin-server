package io.renren.modules.ctb.controller;

import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbMoneyTypeEntity;
import io.renren.modules.ctb.service.CtbMoneyTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * 收入/支出类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/moneytype")
public class CtbMoneyTypeController {
    @Autowired
    private CtbMoneyTypeService ctbMoneyTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:moneytype:list")
    public R list(@RequestBody CtbMoneyTypeEntity entity) {
        return R.ok().put("chargeTypeList", ctbMoneyTypeService.queryIndex(entity));
    }

    /**
     * 删除
     */
    @RequestMapping("/listByServiceCompanyId")
    @RequiresPermissions("ctb:moneytype:list")
    public R listByServiceCompanyId(@RequestBody CtbMoneyTypeEntity entity) {
        List<CtbMoneyTypeEntity> entityList = ctbMoneyTypeService.listByServiceCompanyId(entity.getFkServiceCompanyId());
        if (entityList == null || entityList.isEmpty()) {
            entityList = ctbMoneyTypeService.listDef();
        }
        return R.ok().put("list", entityList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:moneytype:info")
    public R info(@PathVariable("id") Long id) {
        CtbMoneyTypeEntity ctbMoneyType = ctbMoneyTypeService.getById(id);

        return R.ok().put("ctbMoneyType", ctbMoneyType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:moneytype:save")
    public R save(@RequestBody CtbMoneyTypeEntity ctbMoneyType) {
        ctbMoneyTypeService.save(ctbMoneyType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:moneytype:update")
    public R update(@RequestBody CtbMoneyTypeEntity ctbMoneyType) {
        ctbMoneyTypeService.updateById(ctbMoneyType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:moneytype:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbMoneyTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
