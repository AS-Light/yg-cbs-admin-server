package io.renren.modules.ctb.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsMoneyInEntity;
import io.renren.modules.ctb.entity.CtbMoneyInEntity;
import io.renren.modules.ctb.service.CtbMoneyInService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 出口单产生的收入流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
@RestController
@RequestMapping("ctb/moneyin")
public class CtbMoneyInController {

    private CtbMoneyInService ctbMoneyInService;

    public CtbMoneyInController(CtbMoneyInService ctbMoneyInService) {
        this.ctbMoneyInService = ctbMoneyInService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:moneyin:list")
    public R list(@RequestBody CtbMoneyInEntity entity) {
        PageUtils page = ctbMoneyInService.queryPage(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:moneyin:info")
    public R info(@PathVariable("id") Long id) {
        CtbMoneyInEntity ctbMoneyIn = ctbMoneyInService.getById(id);

        return R.ok().put("ctbMoneyIn", ctbMoneyIn);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:moneyin:save")
    public R save(@RequestBody CtbMoneyInEntity ctbMoneyIn) {
        ctbMoneyInService.save(ctbMoneyIn);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:moneyin:update")
    public R update(@RequestBody CtbMoneyInEntity ctbMoneyIn) {
        ctbMoneyInService.updateById(ctbMoneyIn);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:moneyin:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbMoneyInService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 补充附件
     */
    @PostMapping("/updateMoneyImg")
    @RequiresPermissions("ctb:moneyin:updateMoneyImg")
    public R updateMoneyImg(@RequestBody CtbMoneyInEntity entity) {
        ctbMoneyInService.updateMoneyImg(entity);
        return R.ok();
    }

    /**
     * 确核附件
     */
    @PostMapping("/confirmAnnex")
    @RequiresPermissions("ctb:moneyin:confirmAnnex")
    public R confirmAnnex(@RequestBody CtbMoneyInEntity entity) {
        ctbMoneyInService.confirmAnnex(entity);
        return R.ok();
    }

}
