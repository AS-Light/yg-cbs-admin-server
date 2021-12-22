package io.renren.modules.ctb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.annotation.OrgCtbLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.bind.service.BindCbsCtbExportService;
import io.renren.modules.cst.entity.CstDecHeaderEntity;
import io.renren.modules.cst.entity.CstInvtHeaderEntity;
import io.renren.modules.cst.service.CstDecHeaderService;
import io.renren.modules.cst.service.CstInvtHeaderService;
import io.renren.modules.ctb.entity.CtbExportEntity;
import io.renren.modules.ctb.entity.CtbExportPriceItemEntity;
import io.renren.modules.ctb.entity.CtbExportStatusStreamEntity;
import io.renren.modules.ctb.service.CtbExportPriceItemService;
import io.renren.modules.ctb.service.CtbExportService;
import io.renren.modules.org_ctb.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 进口单（主）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/export")
public class CtbExportController extends AbstractController {
    private BindCbsCtbExportService bindCbsCtbExportService;
    private CtbExportService ctbExportService;
    private CstInvtHeaderService cstInvtHeaderService;
    private CstDecHeaderService cstDecHeaderService;
    private CtbExportPriceItemService ctbExportPriceItemService;

    public CtbExportController(
            BindCbsCtbExportService bindCbsCtbExportService,
            CtbExportService ctbExportService,
            CstInvtHeaderService cstInvtHeaderService,
            CstDecHeaderService cstDecHeaderService,
            CtbExportPriceItemService ctbExportPriceItemService
    ) {
        this.bindCbsCtbExportService = bindCbsCtbExportService;
        this.ctbExportService = ctbExportService;
        this.cstInvtHeaderService = cstInvtHeaderService;
        this.cstDecHeaderService = cstDecHeaderService;
        this.ctbExportPriceItemService = ctbExportPriceItemService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:export:list")
    public R list(@RequestBody CtbExportEntity entity) {
        PageUtils page = ctbExportService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:export:info")
    public R info(@PathVariable("id") Long id) {
        CtbExportEntity ctbExport = ctbExportService.detail(id);
        return R.ok().put("ctbExport", ctbExport);
    }

    @ApiOperation("保存返回主键")
    @PostMapping("/saveReturnId")
    @RequiresPermissions("ctb:export:saveReturnId")
    public R saveReturnId(@RequestBody CtbExportEntity entity) {
        entity.setOperator(getUserId());
        entity.setManager(getUserId());
        return R.ok(ctbExportService.saveReturnId(entity));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:export:update")
    public R update(@RequestBody CtbExportEntity entity) {
        return R.ok(ctbExportService.updateAllById(entity));
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:export:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbExportService.deleteByIds(ids);
        return R.ok();
    }

    @OrgCtbLog("出口单提审")
    @ApiOperation("提审")
    @PostMapping("/submit")
    @RequiresPermissions("ctb:export:submit")
    public R submit(@RequestBody CtbExportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            ctbExportService.submit(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCtbLog("撤销出口单提审")
    @ApiOperation("撤销提审")
    @PostMapping("/submitBack")
    @RequiresPermissions("ctb:Export:submitBack")
    public R submitBack(@RequestBody CtbExportStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = ctbExportService.submitBack(statusStreamEntity);
        switch (result) {
            case -1:
                return R.error("出口单状态不合法，待审核状态出口单才能撤销提审");
            case -2:
                return R.error("未知异常，请联系管理员");
            case -3:
                return R.error("没有找到这个出口单,请联系管理员");
            default:
                return R.ok(result);
        }
    }

    @OrgCtbLog("审核出口单")
    @ApiOperation("审核")
    @PostMapping("/check")
    @RequiresPermissions("ctb:export:check")
    public R check(@RequestBody CtbExportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            ctbExportService.check(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @ApiOperation("反填核注清单")
    @PostMapping("/backFillChecklist")
    @RequiresPermissions("ctb:export:backFillChecklist")
    public R backFillChecklist(@RequestBody CstInvtHeaderEntity entity) {
        cstInvtHeaderService.update(entity, new QueryWrapper<CstInvtHeaderEntity>().eq("etps_inner_invt_no", entity.getId()));
        return R.ok();
    }

    @ApiOperation("反填报关单")
    @PostMapping("/backFill")
    @RequiresPermissions("ctb:export:backFill")
    public R backFill(@RequestBody CstDecHeaderEntity entity) {
        cstDecHeaderService.update(entity, new QueryWrapper<CstDecHeaderEntity>().eq("fk_order_id", entity.getId()));
        return R.ok();
    }

    @ApiOperation("出口信息同步给企业")
    @PostMapping("/synToCbs")
    @RequiresPermissions("ctb:export:syn")
    public R synToCbs(@RequestBody CtbExportEntity entity) {
        bindCbsCtbExportService.ctbSynToCbs(entity.getId());
        return R.ok();
    }

    @ApiOperation("保存收费明细（条目）")
    @PostMapping("/savePriceItem")
    @RequiresPermissions("ctb:export:editPrice")
    public R savePriceItem(@RequestBody CtbExportPriceItemEntity priceItemEntity) {
        ctbExportPriceItemService.saveOrUpdate(priceItemEntity);
        return R.ok().put("id", priceItemEntity.getId());
    }

    @ApiOperation("保存收费明细（条目）")
    @PostMapping("/deletePriceItem")
    @RequiresPermissions("ctb:export:editPrice")
    public R deletePriceItem(@RequestBody Long[] ids) {
        ctbExportPriceItemService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @ApiOperation("分配负责人")
    @PostMapping("/distributionManager")
    @RequiresPermissions("ctb:export:update")
    public R distributionManager(@RequestBody CtbExportEntity entity) {
        if (getPermissions().contains("ctb:export:distributionManager") || getUserId().equals(entity.getManager())) {
            ctbExportService.distributionManager(entity.getId(), entity.getManager(), getUserId());
            return R.ok();
        } else {
            return R.error("无权分配负责人，只有管理员和当前负责人可以变更负责人");
        }
    }
}
