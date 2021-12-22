package io.renren.modules.cbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.annotation.OrgCbsLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.bind.service.BindCbsCtbExportService;
import io.renren.modules.cbs.entity.CbsExportEntity;
import io.renren.modules.cbs.entity.CbsExportStatusStreamEntity;
import io.renren.modules.cbs.service.CbsExportService;
import io.renren.modules.cbs.vo.SynExportToCtbVo;
import io.renren.modules.cst.entity.CstDecHeaderEntity;
import io.renren.modules.cst.entity.CstInvtHeaderEntity;
import io.renren.modules.cst.service.CstDecHeaderService;
import io.renren.modules.cst.service.CstInvtHeaderService;
import io.renren.modules.org_cbs.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;


/**
 * 出口单（主）表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@RestController
@RequestMapping("cbs/export")
public class CbsExportController extends AbstractController {

    private CbsExportService cbsExportService;
    private CstInvtHeaderService cstInvtHeaderService;
    private CstDecHeaderService cstDecHeaderService;
    private BindCbsCtbExportService bindCbsCtbExportService;

    public CbsExportController(CbsExportService cbsExportService, CstInvtHeaderService cstInvtHeaderService, CstDecHeaderService cstDecHeaderService, BindCbsCtbExportService bindCbsCtbExportService) {
        this.cbsExportService = cbsExportService;
        this.cstInvtHeaderService = cstInvtHeaderService;
        this.cstDecHeaderService = cstDecHeaderService;
        this.bindCbsCtbExportService = bindCbsCtbExportService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:export:list")
    public R list(@RequestBody CbsExportEntity entity) {
        PageUtils page = cbsExportService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    /**
     * 获取可入库进口单列表
     */
    @RequestMapping("/listForStoreOut")
    @RequiresPermissions("cbs:export:list")
    public R listForStoreOut() {
        return R.ok().put("list", cbsExportService.queryForStoreOut());
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:export:info")
    public R info(@PathVariable("id") Long id) {
        CbsExportEntity cbsExport = cbsExportService.detail(id);
        return R.ok().put("cbsExport", cbsExport);
    }

    @ApiOperation("保存返回主键")
    @PostMapping("/saveReturnId")
    @RequiresPermissions("cbs:export:saveReturnId")
    public R saveReturnId(@RequestBody CbsExportEntity entity) {
        entity.setOperator(getUserId());
        return R.ok(cbsExportService.saveReturnId(entity));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:export:update")
    public R update(@RequestBody CbsExportEntity entity) {
        return R.ok(cbsExportService.updateAllById(entity));
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:export:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsExportService.deleteByIds(ids);
        return R.ok();
    }

    @OrgCbsLog("出口单提审")
    @ApiOperation("提审")
    @PostMapping("/submit")
    @RequiresPermissions("cbs:export:submit")
    public R submit(@RequestBody CbsExportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsExportService.submit(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("撤销出口单提审")
    @ApiOperation("撤销提审")
    @PostMapping("/submitBack")
    @RequiresPermissions("cbs:Export:submitBack")
    public R submitBack(@RequestBody CbsExportStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = cbsExportService.submitBack(statusStreamEntity);
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

    @OrgCbsLog("审核出口单")
    @ApiOperation("审核")
    @PostMapping("/check")
    @RequiresPermissions("cbs:export:check")
    public R check(@RequestBody CbsExportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsExportService.check(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("出口单关务提审")
    @ApiOperation("关务提审")
    @PostMapping("/submitCustom")
    @RequiresPermissions("cbs:export:submit")
    public R submitCustom(@RequestBody CbsExportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsExportService.submitCustom(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("撤销出口单关务提审")
    @ApiOperation("撤销关务提审")
    @PostMapping("/submitBackCustom")
    @RequiresPermissions("cbs:export:submitBack")
    public R submitBackCustom(@RequestBody CbsExportStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = cbsExportService.submitBackCustom(statusStreamEntity);
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

    @OrgCbsLog("审核出口单关务")
    @ApiOperation("关务审核")
    @PostMapping("/checkCustom")
    @RequiresPermissions("cbs:export:check")
    public R checkCustom(@RequestBody CbsExportStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsExportService.checkCustom(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @ApiOperation("反填核注清单")
    @PostMapping("/backFillChecklist")
    @RequiresPermissions("cbs:export:backFillChecklist")
    public R backFillChecklist(@RequestBody CstInvtHeaderEntity entity) {
        cstInvtHeaderService.update(entity, new QueryWrapper<CstInvtHeaderEntity>().eq("etps_inner_invt_no", entity.getId()));
        return R.ok();
    }

    @ApiOperation("反填报关单")
    @PostMapping("/backFill")
    @RequiresPermissions("cbs:export:backFill")
    public R backFill(@RequestBody CstDecHeaderEntity entity) {
        cstDecHeaderService.update(entity, new QueryWrapper<CstDecHeaderEntity>().eq("fk_order_id", entity.getId()));
        return R.ok();
    }

    @ApiOperation("进口信息同步给报关行")
    @PostMapping("/synToCtb")
    @RequiresPermissions("cbs:export:syn")
    public R synToCtb(@RequestBody SynExportToCtbVo vo) {
        return R.ok(bindCbsCtbExportService.cbsSynToCtb(vo.getExportId(), getTenantId(), vo.getCtbCompanyId()));
    }

}
