package io.renren.modules.cbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.annotation.OrgCbsLog;
import io.renren.common.utils.R;
import io.renren.modules.bind.service.BindCbsCtbImportService;
import io.renren.modules.cbs.entity.CbsImportEntity;
import io.renren.modules.cbs.entity.CbsImportStatusStreamEntity;
import io.renren.modules.cbs.entity.CbsImportVoyageEntity;
import io.renren.modules.cbs.service.CbsImportService;
import io.renren.modules.cbs.vo.SynImportToCtbVo;
import io.renren.modules.cst.entity.CstDecHeaderEntity;
import io.renren.modules.cst.entity.CstInvtHeaderEntity;
import io.renren.modules.cst.service.CstDecHeaderService;
import io.renren.modules.cst.service.CstInvtHeaderService;
import io.renren.modules.org_cbs.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;


/**
 * 进口单（主）表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@RestController
@RequestMapping("cbs/import")
public class CbsImportController extends AbstractController {

    private BindCbsCtbImportService bindCbsCtbImportService;
    private CbsImportService cbsImportService;
    private CstInvtHeaderService cstInvtHeaderService;
    private CstDecHeaderService cstDecHeaderService;

    public CbsImportController(
            BindCbsCtbImportService bindCbsCtbImportService,
            CbsImportService cbsImportService,
            CstInvtHeaderService cstInvtHeaderService,
            CstDecHeaderService cstDecHeaderService
    ) {
        this.bindCbsCtbImportService = bindCbsCtbImportService;
        this.cbsImportService = cbsImportService;
        this.cstInvtHeaderService = cstInvtHeaderService;
        this.cstDecHeaderService = cstDecHeaderService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:import:list")
    public R list(@RequestBody CbsImportEntity entity) {
        return R.ok().put("page", cbsImportService.queryIndex(entity));
    }

    /**
     * 获取可入库进口单列表
     */
    @RequestMapping("/listForStoreIn")
    @RequiresPermissions("cbs:import:list")
    public R listForStoreIn() {
        return R.ok().put("list", cbsImportService.queryForStoreIn());
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:import:info")
    public R info(@PathVariable("id") Long id) {
        CbsImportEntity cbsImport = cbsImportService.detail(id);
        return R.ok().put("cbsImport", cbsImport);
    }

    @ApiOperation("保存返回主键")
    @PostMapping("/saveReturnId")
    @RequiresPermissions("cbs:import:saveReturnId")
    public R saveReturnId(@RequestBody CbsImportEntity entity) {
        entity.setOperator(getUserId());
        return R.ok(cbsImportService.saveReturnId(entity));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:import:update")
    public R update(@RequestBody CbsImportEntity entity) {
        entity.setOperator(getUserId());
        return R.ok(cbsImportService.updateAllById(entity));
    }

    /**
     * 追加航次变更
     */
    @RequestMapping("/updateVoyageChange")
    @RequiresPermissions("cbs:import:update")
    public R appendVoyage(@RequestBody CbsImportVoyageEntity voyageEntity) {
        return R.ok(cbsImportService.updateVoyageChange(voyageEntity));
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:import:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsImportService.deleteByIds(ids);
        return R.ok();
    }

    @ApiOperation("状态变更")
    @PostMapping("/status")
    @RequiresPermissions("cbs:import:status")
    public R status(@RequestBody CbsImportEntity entity) {
        CbsImportEntity e = new CbsImportEntity();
        e.setId(entity.getId());
        e.setStatus(entity.getStatus());
        e.setOperator(getUserId());
        return R.ok(cbsImportService.updateAllById(e));
    }

    @OrgCbsLog("进口单提审")
    @ApiOperation("提审")
    @PostMapping("/submit")
    @RequiresPermissions("cbs:import:submit")
    public R submit(@RequestBody CbsImportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsImportService.submit(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("撤销进口单提审")
    @ApiOperation("撤销提审")
    @PostMapping("/submitBack")
    @RequiresPermissions("cbs:import:submitBack")
    public R submitBack(@RequestBody CbsImportStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = cbsImportService.submitBack(statusStreamEntity);
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

    @OrgCbsLog("审核进口单")
    @ApiOperation("审核")
    @PostMapping("/check")
    @RequiresPermissions("cbs:import:check")
    public R check(@RequestBody CbsImportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsImportService.check(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("变更航次")
    @ApiOperation("变更航次")
    @PostMapping("/updateVoyageChange")
    @RequiresPermissions("cbs:import:update")
    public R updateVoyageChange(@RequestBody CbsImportVoyageEntity voyageEntity) {
        try {
            cbsImportService.updateVoyageChange(voyageEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("进口单关务提审")
    @ApiOperation("关务提审")
    @PostMapping("/submitCustom")
    @RequiresPermissions("cbs:import:submit")
    public R submitCustom(@RequestBody CbsImportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsImportService.submitCustom(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("撤销进口单关务提审")
    @ApiOperation("撤销关务提审")
    @PostMapping("/submitBackCustom")
    @RequiresPermissions("cbs:import:submitBack")
    public R submitBackCustom(@RequestBody CbsImportStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = cbsImportService.submitBackCustom(statusStreamEntity);
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

    @OrgCbsLog("审核进口单关务")
    @ApiOperation("关务审核")
    @PostMapping("/checkCustom")
    @RequiresPermissions("cbs:import:check")
    public R checkCustom(@RequestBody CbsImportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsImportService.checkCustom(statusStreamEntity, getUserId());
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @ApiOperation("反填核注清单")
    @PostMapping("/backFillChecklist")
    @RequiresPermissions("cbs:import:backFillChecklist")
    public R backFillChecklist(@RequestBody CstInvtHeaderEntity entity) {
        cstInvtHeaderService.update(entity, new QueryWrapper<CstInvtHeaderEntity>().eq("etps_inner_invt_no", entity.getId()));
        return R.ok();
    }

    @ApiOperation("反填报关单")
    @PostMapping("/backFill")
    @RequiresPermissions("cbs:import:backFill")
    public R backFill(@RequestBody CstDecHeaderEntity entity) {
        cstDecHeaderService.update(entity, new QueryWrapper<CstDecHeaderEntity>().eq("fk_order_id", entity.getId()));
        return R.ok();
    }


    @ApiOperation("进口信息同步给报关行")
    @PostMapping("/synToCtb")
    @RequiresPermissions("cbs:import:syn")
    public R synToCtb(@RequestBody SynImportToCtbVo vo) {
        return R.ok(bindCbsCtbImportService.cbsSynToCtb(vo.getImportId(), getTenantId(), vo.getCtbCompanyId()));
    }

}
