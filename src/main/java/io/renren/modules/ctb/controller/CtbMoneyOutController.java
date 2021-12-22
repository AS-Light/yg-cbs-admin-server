package io.renren.modules.ctb.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbMoneyOutEntity;
import io.renren.modules.ctb.service.CtbMoneyOutService;
import io.renren.modules.org_ctb.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


/**
 * 进口单产生的支出流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/moneyout")
public class CtbMoneyOutController extends AbstractController {
    private CtbMoneyOutService ctbMoneyOutService;

    public CtbMoneyOutController(CtbMoneyOutService ctbMoneyOutService) {
        this.ctbMoneyOutService = ctbMoneyOutService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:moneyout:list")
    public R list(@RequestBody CtbMoneyOutEntity entity) {
        PageUtils page = ctbMoneyOutService.queryPage(entity);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:moneyout:info")
    public R info(@PathVariable("id") Long id) {
        CtbMoneyOutEntity ctbMoneyOut = ctbMoneyOutService.getById(id);

        return R.ok().put("ctbMoneyOut", ctbMoneyOut);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:moneyout:save")
    public R save(@RequestBody CtbMoneyOutEntity ctbMoneyOut) {
        ctbMoneyOutService.save(ctbMoneyOut);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:moneyout:update")
    public R update(@RequestBody CtbMoneyOutEntity ctbMoneyOut) {
        ctbMoneyOutService.updateById(ctbMoneyOut);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:moneyout:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbMoneyOutService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 报销
     */
    @RequestMapping("/reimbursement")
    @RequiresPermissions("ctb:moneyout:reimbursement")
    public R reimbursement(@RequestBody Long[] ids) {
        ctbMoneyOutService.reimbursement(Arrays.asList(ids), getUserId());
        return R.ok();
    }

    /**
     * 补充附件
     */
    @PostMapping("/updateMoneyImg")
    @RequiresPermissions("ctb:moneyout:updateMoneyImg")
    public R updateMoneyImg(@RequestBody CtbMoneyOutEntity entity) {
        ctbMoneyOutService.updateMoneyImg(entity);
        return R.ok();
    }

    /**
     * 确核附件
     */
    @PostMapping("/confirmAnnex")
    @RequiresPermissions("ctb:moneyout:confirmAnnex")
    public R confirmAnnex(@RequestBody CtbMoneyOutEntity entity) {
        ctbMoneyOutService.confirmAnnex(entity);
        return R.ok();
    }


    /**
     * 报销 excel
     */
    @RequestMapping("/reimbursementExcel")
    @RequiresPermissions("ctb:moneyout:list")
    public R reimbursementExcel(HttpServletResponse response, @RequestBody Long[] ids) {
        return R.ok(ctbMoneyOutService.reimbursementExcel(response, Arrays.asList(ids)));
    }

}
