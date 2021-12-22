package io.renren.modules.ctb.controller;

import io.renren.common.annotation.OrgCtbLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.bind.service.BindCbsContractCtbProcessingTradeService;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeEntity;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradePriceItemEntity;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeStatusStreamEntity;
import io.renren.modules.ctb.service.CtbOrderProcessingTradePriceItemService;
import io.renren.modules.ctb.service.CtbOrderProcessingTradeService;
import io.renren.modules.org_ctb.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


/**
 * 报关行加贸（手册）业务表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/orderProcessingTrade")
public class CtbOrderProcessingTradeController extends AbstractController {
    private CtbOrderProcessingTradeService ctbOrderProcessingTradeService;
    private BindCbsContractCtbProcessingTradeService bindCbsContractCtbProcessingTradeService;
    private CtbOrderProcessingTradePriceItemService ctbOrderProcessingTradePriceItemService;

    public CtbOrderProcessingTradeController(
            CtbOrderProcessingTradeService ctbOrderProcessingTradeService,
            BindCbsContractCtbProcessingTradeService bindCbsContractCtbProcessingTradeService,
            CtbOrderProcessingTradePriceItemService ctbOrderProcessingTradePriceItemService
    ) {
        this.ctbOrderProcessingTradeService = ctbOrderProcessingTradeService;
        this.bindCbsContractCtbProcessingTradeService = bindCbsContractCtbProcessingTradeService;
        this.ctbOrderProcessingTradePriceItemService = ctbOrderProcessingTradePriceItemService;
    }

    @OrgCtbLog("创建加贸单")
    @ApiOperation("创建并返回主键")
    @PostMapping("/saveReturnId")
    @RequiresPermissions("ctb:orderprocessingtrade:saveReturnId")
    public R saveReturnId(@RequestBody CtbOrderProcessingTradeEntity entity) {
        entity.setOperator(getUserId());
        entity.setManager(getUserId());
        Long id = ctbOrderProcessingTradeService.saveReturnId(entity);
        return R.ok(id);
    }

    @ApiOperation("加贸单列表")
    @PostMapping("/list")
    @RequiresPermissions("ctb:orderprocessingtrade:list")
    public R list(@RequestBody CtbOrderProcessingTradeEntity entity) {
        PageUtils page = ctbOrderProcessingTradeService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    @OrgCtbLog("查看加贸单详情")
    @ApiOperation("详情")
    @GetMapping("/info/{id}")
    @RequiresPermissions("ctb:orderprocessingtrade:info")
    public R info(@PathVariable("id") Long id) {
        CtbOrderProcessingTradeEntity entity = ctbOrderProcessingTradeService.detail(id);
        return R.ok().put("entity", entity);
    }

    @OrgCtbLog("编辑加贸单")
    @ApiOperation("修改")
    @PostMapping("/update")
    @RequiresPermissions("ctb:orderprocessingtrade:update")
    public R update(@RequestBody CtbOrderProcessingTradeEntity entity) {
        entity.setOperator(getUserId());
        return R.ok(ctbOrderProcessingTradeService.updateAllById(entity));
    }

    @OrgCtbLog("反填手册编号")
    @ApiOperation("修改")
    @PostMapping("/updateEmlCode")
    @RequiresPermissions("ctb:orderprocessingtrade:update")
    public R updateEmlCode(@RequestBody CtbOrderProcessingTradeEntity entity) {
        entity.setOperator(getUserId());
        ctbOrderProcessingTradeService.updateEmlCode(entity);
        return R.ok();
    }

    @OrgCtbLog("加贸单提审")
    @ApiOperation("提审")
    @PostMapping("/submit")
    @RequiresPermissions("ctb:orderprocessingtrade:submit")
    public R submit(@RequestBody CtbOrderProcessingTradeStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            ctbOrderProcessingTradeService.submit(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCtbLog("撤销加贸单提审")
    @ApiOperation("撤销提审")
    @PostMapping("/submitBack")
    @RequiresPermissions("ctb:orderprocessingtrade:submitBack")
    public R submitBack(@RequestBody CtbOrderProcessingTradeStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = ctbOrderProcessingTradeService.submitBack(statusStreamEntity);
        switch (result) {
            case -1:
                return R.error("加贸单状态不合法，待审核状态加贸单才能撤销提审");
            case -2:
                return R.error("未知异常，请联系管理员");
            case -3:
                return R.error("没有找到这个加贸单,请联系管理员");
            default:
                return R.ok(result);
        }
    }

    @OrgCtbLog("审核加贸单")
    @ApiOperation("审核")
    @PostMapping("/check")
    @RequiresPermissions("ctb:orderprocessingtrade:check")
    public R check(@RequestBody CtbOrderProcessingTradeStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            ctbOrderProcessingTradeService.check(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCtbLog("删除加贸单")
    @ApiOperation("删除")
    @PostMapping("/delete")
    @RequiresPermissions("ctb:orderprocessingtrade:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbOrderProcessingTradeService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @ApiOperation("状态变更")
    @PostMapping("/status")
    @RequiresPermissions("ctb:orderprocessingtrade:status")
    public R status(@RequestBody CtbOrderProcessingTradeEntity entity) {
        CtbOrderProcessingTradeEntity e = new CtbOrderProcessingTradeEntity();
        e.setId(entity.getId());
        e.setStatus(entity.getStatus());
        e.setOperator(getUserId());
        ctbOrderProcessingTradeService.updateAllById(e);
        return R.ok();
    }

    @ApiOperation("删除")
    @PostMapping("/deleteOne")
    @RequiresPermissions("ctb:orderprocessingtrade:deleteOne")
    public R deleteOne(@RequestBody CtbOrderProcessingTradeEntity entity) {
        return R.ok(ctbOrderProcessingTradeService.deleteOne(entity));
    }

    @ApiOperation("同步到Cbs")
    @PostMapping("/synToCbs")
    @RequiresPermissions("ctb:orderprocessingtrade:syn")
    public R synToCbs(@RequestBody CtbOrderProcessingTradeEntity entity) {
        return R.ok(bindCbsContractCtbProcessingTradeService.ctbSynToCbs(entity.getId()));
    }

    @ApiOperation("AEO-导出Excel")
    @PostMapping("/AEOExportExcel")
    @RequiresPermissions("ctb:orderprocessingtrade:AEOExportExcel")
    public R AEOExportExcel(HttpServletResponse response, @RequestBody CtbOrderProcessingTradeEntity entity) {
        String url = ctbOrderProcessingTradeService.AEOExportExcel(response, entity);
        return R.ok().put("url", url);
    }

    @ApiOperation("保存收费明细（条目）")
    @PostMapping("/savePriceItem")
    @RequiresPermissions("ctb:orderprocessingtrade:editPrice")
    public R savePriceItem(@RequestBody CtbOrderProcessingTradePriceItemEntity priceItemEntity) {
        ctbOrderProcessingTradePriceItemService.saveOrUpdate(priceItemEntity);
        return R.ok().put("id", priceItemEntity.getId());
    }

    @ApiOperation("保存收费明细（条目）")
    @PostMapping("/deletePriceItem")
    @RequiresPermissions("ctb:orderprocessingtrade:editPrice")
    public R deletePriceItem(@RequestBody Long[] ids) {
        ctbOrderProcessingTradePriceItemService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @ApiOperation("分配负责人")
    @PostMapping("/distributionManager")
    @RequiresPermissions("ctb:orderprocessingtrade:update")
    public R distributionManager(@RequestBody CtbOrderProcessingTradeEntity entity) {
        if (getPermissions().contains("ctb:export:distributionManager") || getUserId().equals(entity.getManager())) {
            ctbOrderProcessingTradeService.distributionManager(entity.getId(), entity.getManager(), getUserId());
            return R.ok();
        } else {
            return R.error("无权分配负责人，只有管理员和当前负责人可以变更负责人");
        }
    }
}
