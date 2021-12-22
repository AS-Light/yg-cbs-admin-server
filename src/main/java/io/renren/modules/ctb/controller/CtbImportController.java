package io.renren.modules.ctb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.annotation.OrgCtbLog;
import io.renren.common.utils.R;
import io.renren.modules.bind.service.BindCbsCtbImportService;
import io.renren.modules.cst.entity.CstDecHeaderEntity;
import io.renren.modules.cst.entity.CstInvtHeaderEntity;
import io.renren.modules.cst.service.CstDecHeaderService;
import io.renren.modules.cst.service.CstInvtHeaderService;
import io.renren.modules.ctb.entity.*;
import io.renren.modules.ctb.service.CtbImportPriceItemService;
import io.renren.modules.ctb.service.CtbImportService;
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
@RequestMapping("ctb/import")
public class CtbImportController extends AbstractController {
    private BindCbsCtbImportService bindCbsCtbImportService;
    private CtbImportService ctbImportService;
    private CtbImportPriceItemService ctbImportPriceItemService;
    private CstInvtHeaderService cstInvtHeaderService;
    private CstDecHeaderService cstDecHeaderService;

    public CtbImportController(
            BindCbsCtbImportService bindCbsCtbImportService,
            CtbImportService ctbImportService,
            CstInvtHeaderService cstInvtHeaderService,
            CstDecHeaderService cstDecHeaderService,
            CtbImportPriceItemService ctbImportPriceItemService
    ) {
        this.bindCbsCtbImportService = bindCbsCtbImportService;
        this.ctbImportService = ctbImportService;
        this.cstInvtHeaderService = cstInvtHeaderService;
        this.cstDecHeaderService = cstDecHeaderService;
        this.ctbImportPriceItemService = ctbImportPriceItemService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:import:list")
    public R list(@RequestBody CtbImportEntity entity) {
        return R.ok().put("page", ctbImportService.queryIndex(entity));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:import:info")
    public R info(@PathVariable("id") Long id) {
        CtbImportEntity ctbImport = ctbImportService.detail(id);
        return R.ok().put("ctbImport", ctbImport);
    }

    @ApiOperation("保存返回主键")
    @PostMapping("/saveReturnId")
    @RequiresPermissions("ctb:import:saveReturnId")
    public R saveReturnId(@RequestBody CtbImportEntity entity) {
        entity.setOperator(getUserId());
        entity.setManager(getUserId());
        return R.ok(ctbImportService.saveReturnId(entity));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:import:update")
    public R update(@RequestBody CtbImportEntity entity) {
        entity.setOperator(getUserId());
        return R.ok(ctbImportService.updateAllById(entity));
    }

    /**
     * 追加航次变更
     */
    @RequestMapping("/updateVoyageChange")
    @RequiresPermissions("ctb:import:update")
    public R appendVoyage(@RequestBody CtbImportVoyageEntity voyageEntity) {
        return R.ok(ctbImportService.updateVoyageChange(voyageEntity));
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:import:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbImportService.deleteByIds(ids);
        return R.ok();
    }

    @ApiOperation("状态变更")
    @PostMapping("/status")
    @RequiresPermissions("ctb:import:status")
    public R status(@RequestBody CtbImportEntity entity) {
        CtbImportEntity e = new CtbImportEntity();
        e.setId(entity.getId());
        e.setStatus(entity.getStatus());
        e.setOperator(getUserId());
        return R.ok(ctbImportService.updateAllById(e));
    }

    @OrgCtbLog("进口单提审")
    @ApiOperation("提审")
    @PostMapping("/submit")
    @RequiresPermissions("ctb:import:submit")
    public R submit(@RequestBody CtbImportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            ctbImportService.submit(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCtbLog("撤销进口单提审")
    @ApiOperation("撤销提审")
    @PostMapping("/submitBack")
    @RequiresPermissions("ctb:import:submitBack")
    public R submitBack(@RequestBody CtbImportStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = ctbImportService.submitBack(statusStreamEntity);
        switch (result) {
            case -1:
                return R.error("进口单状态不合法，待审核状态进口单才能撤销提审");
            case -2:
                return R.error("未知异常，请联系管理员");
            case -3:
                return R.error("没有找到这个进口单,请联系管理员");
            default:
                return R.ok(result);
        }
    }

    @OrgCtbLog("审核进口单")
    @ApiOperation("审核")
    @PostMapping("/check")
    @RequiresPermissions("ctb:import:check")
    public R check(@RequestBody CtbImportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            ctbImportService.check(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCtbLog("变更航次")
    @ApiOperation("变更航次")
    @PostMapping("/updateVoyageChange")
    @RequiresPermissions("ctb:import:update")
    public R updateVoyageChange(@RequestBody CtbImportVoyageEntity voyageEntity) {
        try {
            ctbImportService.updateVoyageChange(voyageEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @ApiOperation("反填核注清单")
    @PostMapping("/backFillChecklist")
    @RequiresPermissions("ctb:import:backFillChecklist")
    public R backFillChecklist(@RequestBody CstInvtHeaderEntity entity) {
        cstInvtHeaderService.update(entity, new QueryWrapper<CstInvtHeaderEntity>().eq("etps_inner_invt_no", entity.getId()));
        return R.ok();
    }

    @ApiOperation("反填报关单")
    @PostMapping("/backFill")
    @RequiresPermissions("ctb:import:backFill")
    public R backFill(@RequestBody CstDecHeaderEntity entity) {
        cstDecHeaderService.update(entity, new QueryWrapper<CstDecHeaderEntity>().eq("fk_order_id", entity.getId()));
        return R.ok();
    }

    @ApiOperation("进口信息同步给企业")
    @PostMapping("/synToCbs")
    @RequiresPermissions("ctb:import:syn")
    public R synToCbs(@RequestBody CtbImportEntity entity) {
        bindCbsCtbImportService.ctbSynToCbs(entity.getId());
        return R.ok();
    }

    @ApiOperation("保存收费明细（条目）")
    @PostMapping("/savePriceItem")
    @RequiresPermissions("ctb:import:editPrice")
    public R savePriceItem(@RequestBody CtbImportPriceItemEntity priceItemEntity) {
        ctbImportPriceItemService.saveOrUpdate(priceItemEntity);
        return R.ok().put("id", priceItemEntity.getId());
    }

    @ApiOperation("保存收费明细（条目）")
    @PostMapping("/deletePriceItem")
    @RequiresPermissions("ctb:import:editPrice")
    public R deletePriceItem(@RequestBody Long[] ids) {
        ctbImportPriceItemService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @ApiOperation("分配负责人")
    @PostMapping("/distributionManager")
    @RequiresPermissions("ctb:import:update")
    public R distributionManager(@RequestBody CtbImportEntity entity) {
        if (getPermissions().contains("ctb:export:distributionManager") || getUserId().equals(entity.getManager())) {
            ctbImportService.distributionManager(entity.getId(), entity.getManager(), getUserId());
            return R.ok();
        } else {
            return R.error("无权分配负责人，只有管理员和当前负责人可以变更负责人");
        }
    }
}
