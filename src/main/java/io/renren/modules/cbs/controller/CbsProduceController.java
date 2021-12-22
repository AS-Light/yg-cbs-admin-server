package io.renren.modules.cbs.controller;

import io.renren.common.annotation.OrgCbsLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsProduceEntity;
import io.renren.modules.cbs.entity.CbsProduceStatusStreamEntity;
import io.renren.modules.cbs.service.CbsProduceService;
import io.renren.modules.org_cbs.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


/**
 * 生产单表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@RestController
@RequestMapping("cbs/produce")
public class CbsProduceController extends AbstractController {

    private CbsProduceService cbsProduceService;

    @Autowired
    public void setCbsProduceService(CbsProduceService cbsProduceService) {
        this.cbsProduceService = cbsProduceService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:produce:list")
    public R list(@RequestBody CbsProduceEntity entity) {
        PageUtils page = cbsProduceService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/listForRawMaterialBack")
    @RequiresPermissions("cbs:produce:list")
    public R listForRawMaterialBack() {
        return R.ok().put("list", cbsProduceService.listForRawMaterialBack());
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:produce:info")
    public R info(@PathVariable("id") Long id) {
        CbsProduceEntity cbsProduce = cbsProduceService.detail(id);
        return R.ok().put("cbsProduce", cbsProduce);
    }

    @ApiOperation("创建前置：检查是否可以创建生产计划")
    @PostMapping("/preSave")
    @RequiresPermissions("cbs:produce:saveReturnId")
    public R preSave(@RequestBody CbsProduceEntity entity) {
        HashMap<String, Object> resultMap = cbsProduceService.preSave(entity);
        return R.ok(resultMap);
    }

    @ApiOperation("保存返回主键")
    @PostMapping("/saveReturnId")
    @RequiresPermissions("cbs:produce:saveReturnId")
    public R saveReturnId(@RequestBody CbsProduceEntity entity) {
        try {
            Long id = cbsProduceService.saveReturnId(entity);
            return R.ok(id);
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:produce:update")
    public R update(@RequestBody CbsProduceEntity entity) {
        return R.ok(cbsProduceService.updateById(entity));
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:produce:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsProduceService.deleteByIds(ids);
        return R.ok();
    }


    @OrgCbsLog("生产计划[开始]提审")
    @ApiOperation("[开始]提审")
    @PostMapping("/submitStart")
    @RequiresPermissions("cbs:produce:submit")
    public R submit(@RequestBody CbsProduceStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsProduceService.submitStart(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("生产计划[开始]撤审")
    @ApiOperation("[开始]撤销提审")
    @PostMapping("/submitStartBack")
    @RequiresPermissions("cbs:produce:submitBack")
    public R submitBack(@RequestBody CbsProduceStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = cbsProduceService.submitStartBack(statusStreamEntity);
        switch (result) {
            case -1:
                return R.error("生产计划状态不合法，待审核状态进口单才能撤销提审");
            case -2:
                return R.error("未知异常，请联系管理员");
            case -3:
                return R.error("没有找到这个生产计划,请联系管理员");
            default:
                return R.ok(result);
        }
    }

    @OrgCbsLog("审核生产计划[开始]")
    @ApiOperation("审核[开始]")
    @PostMapping("/checkStart")
    @RequiresPermissions("cbs:produce:check")
    public R checkStart(@RequestBody CbsProduceStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsProduceService.checkStart(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("生产计划[完成]提审")
    @ApiOperation("[完成]提审")
    @PostMapping("/submitComplete")
    @RequiresPermissions("cbs:produce:submit")
    public R submitComplete(@RequestBody CbsProduceStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            HashMap<String, Object> resultMap = cbsProduceService.submitComplete(statusStreamEntity);
            return R.ok(resultMap);
        } catch (Exception e) {
            return R.error("未知错误，请联系管理员，错误信息:" + e.getMessage());
        }
    }

    @OrgCbsLog("生产计划[完成]撤审")
    @ApiOperation("[完成]撤销提审")
    @PostMapping("/submitBackComplete")
    @RequiresPermissions("cbs:produce:submitBack")
    public R submitBackComplete(@RequestBody CbsProduceStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = cbsProduceService.submitBackComplete(statusStreamEntity);
        switch (result) {
            case -1:
                return R.error("生产计划状态不合法，待审核状态生产计划才能撤销提审");
            case -2:
                return R.error("未知异常，请联系管理员");
            case -3:
                return R.error("没有找到这个生产计划,请联系管理员");
            default:
                return R.ok(result);
        }
    }

    @OrgCbsLog("生产计划[完成]审核")
    @ApiOperation("[完成]审核")
    @PostMapping("/checkComplete")
    @RequiresPermissions("cbs:produce:check")
    public R checkComplete(@RequestBody CbsProduceStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            HashMap<String, Object> resultMap = cbsProduceService.checkComplete(statusStreamEntity, getUserId());
            return R.ok(resultMap);
        } catch (Exception e) {
            return R.error("未知错误，请联系管理员，错误信息:" + e.getMessage());
        }
    }
}
