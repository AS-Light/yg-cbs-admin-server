package io.renren.modules.ctb.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbMoneyTicketsEntity;
import io.renren.modules.ctb.service.CtbMoneyTicketsService;
import io.renren.modules.org_ctb.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


/**
 * 发票管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
@RestController
@RequestMapping("ctb/moneytickets")
public class CtbMoneyTicketsController extends AbstractController {

    private CtbMoneyTicketsService ctbMoneyTicketsService;

    public CtbMoneyTicketsController(CtbMoneyTicketsService ctbMoneyTicketsService) {
        this.ctbMoneyTicketsService = ctbMoneyTicketsService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:moneytickets:list")
    public R list(@RequestBody CtbMoneyTicketsEntity entity) {
        PageUtils page = ctbMoneyTicketsService.queryPage(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:moneytickets:info")
    public R info(@PathVariable("id") Long id) {
        CtbMoneyTicketsEntity ctbMoneyTickets = ctbMoneyTicketsService.getById(id);

        return R.ok().put("ctbMoneyTickets", ctbMoneyTickets);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:moneytickets:save")
    public R save(@RequestBody CtbMoneyTicketsEntity ctbMoneyTickets) {
        ctbMoneyTicketsService.save(ctbMoneyTickets);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:moneytickets:update")
    public R update(@RequestBody CtbMoneyTicketsEntity ctbMoneyTickets) {
        ctbMoneyTicketsService.updateById(ctbMoneyTickets);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:moneytickets:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbMoneyTicketsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 补充附件
     */
    @PostMapping("/updateMoneyImg")
    @RequiresPermissions("ctb:moneytickets:updateMoneyImg")
    public R updateMoneyImg(@RequestBody CtbMoneyTicketsEntity entity) {
        ctbMoneyTicketsService.updateMoneyImg(entity);
        return R.ok();
    }

    /**
     * 确核附件
     */
    @PostMapping("/confirmAnnex")
    @RequiresPermissions("ctb:moneytickets:confirmAnnex")
    public R confirmAnnex(@RequestBody CtbMoneyTicketsEntity entity) {
        ctbMoneyTicketsService.confirmAnnex(entity);
        return R.ok();
    }


    /**
     * 开发票
     */
    @PostMapping("/uploadInvoice")
    @RequiresPermissions("ctb:moneytickets:uploadInvoice")
    public R uploadInvoice(@RequestBody CtbMoneyTicketsEntity entity) {
        entity.setOperator(getUserId());
        ctbMoneyTicketsService.uploadInvoice(entity);
        return R.ok();
    }

    /**
     * 确核发票
     */
    @PostMapping("/confirmInvoice")
    @RequiresPermissions("ctb:moneytickets:confirmInvoice")
    public R confirmInvoice(@RequestBody CtbMoneyTicketsEntity entity) {
        ctbMoneyTicketsService.confirmInvoice(entity);
        return R.ok();
    }

    /**
     * 开发票 excel
     */
    @PostMapping("/uploadInvoiceExcel")
    @RequiresPermissions("ctb:moneytickets:list")
    public R uploadInvoiceExcel(HttpServletResponse response, @RequestBody CtbMoneyTicketsEntity entity) {
        entity.setOperator(getUserId());
        return R.ok(ctbMoneyTicketsService.uploadInvoiceExcel(response, entity));
    }
}
