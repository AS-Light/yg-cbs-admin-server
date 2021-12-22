package io.renren.modules.cbs.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.renren.common.annotation.OrgCbsLog;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsPurchaseEntity;
import io.renren.modules.cbs.entity.CbsPurchaseStatusStreamEntity;
import io.renren.modules.cbs.service.CbsPurchaseService;
import io.renren.modules.org_cbs.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@RestController
@RequestMapping("cbs/purchase")
public class CbsPurchaseController extends AbstractController {
    private CbsPurchaseService cbsPurchaseService;

    @Autowired
    public void setCbsPurchaseService(CbsPurchaseService cbsPurchaseService) {
        this.cbsPurchaseService = cbsPurchaseService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:purchase:list")
    public R list(@RequestBody CbsPurchaseEntity entity) {
        return R.ok().put("page", cbsPurchaseService.queryIndex(entity));
    }

    /**
     * 获取可入库国内收货单列表
     */
    @RequestMapping("/listForStoreIn")
    @RequiresPermissions("cbs:purchase:list")
    public R listForStoreIn() {
        return R.ok().put("list", cbsPurchaseService.queryForStoreIn());
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:purchase:info")
    public R info(@PathVariable("id") Long id) {
        CbsPurchaseEntity cbsPurchase = cbsPurchaseService.detail(id);
        return R.ok().put("cbsPurchase", cbsPurchase);
    }

    @ApiOperation("保存返回主键")
    @PostMapping("/saveReturnId")
    @RequiresPermissions("cbs:purchase:save")
    public R saveReturnId(@RequestBody CbsPurchaseEntity entity) {
        try {
            entity.setOperator(getUserId());
            Long id = cbsPurchaseService.saveReturnId(entity);
            return R.ok(id);
        } catch (Exception e) {
            try {
                // 加一层try/catch因为可能在Gson解析时报错
                HashMap<String, Object> msgMap = new Gson().fromJson(e.getMessage(), new TypeToken<HashMap<String, Object>>() {
                }.getType());
                return msgMap != null ? R.ok(msgMap) : R.error(e.getMessage() == null ? "未知错误，请联系管理员" : e.getMessage());
            } catch (Exception ex) {
                return R.error(e.getMessage() == null ? "未知错误，请联系管理员" : e.getMessage());
            }
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:purchase:update")
    public R update(@RequestBody CbsPurchaseEntity entity) {
        entity.setOperator(getUserId());
        return R.ok(cbsPurchaseService.updateAllById(entity));
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:purchase:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsPurchaseService.deleteByIds(ids);
        return R.ok();
    }

    @ApiOperation("状态变更")
    @PostMapping("/status")
    @RequiresPermissions("cbs:purchase:status")
    public R status(@RequestBody CbsPurchaseEntity entity) {
        CbsPurchaseEntity e = new CbsPurchaseEntity();
        e.setId(entity.getId());
        e.setStatus(entity.getStatus());
        e.setOperator(getUserId());
        return R.ok(cbsPurchaseService.updateAllById(e));
    }

    @OrgCbsLog("国内收货单提审")
    @ApiOperation("提审")
    @PostMapping("/submit")
    @RequiresPermissions("cbs:purchase:submit")
    public R submit(@RequestBody CbsPurchaseStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsPurchaseService.submit(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("撤销国内收货单提审")
    @ApiOperation("撤销提审")
    @PostMapping("/submitBack")
    @RequiresPermissions("cbs:purchase:submitBack")
    public R submitBack(@RequestBody CbsPurchaseStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = cbsPurchaseService.submitBack(statusStreamEntity);
        switch (result) {
            case -1:
                return R.error("国内收货单状态不合法，待审核状态国内收货单才能撤销提审");
            case -2:
                return R.error("未知异常，请联系管理员");
            case -3:
                return R.error("没有找到这个国内收货单,请联系管理员");
            default:
                return R.ok(result);
        }
    }

    @OrgCbsLog("审核国内收货单")
    @ApiOperation("审核")
    @PostMapping("/check")
    @RequiresPermissions("cbs:purchase:check")
    public R check(@RequestBody CbsPurchaseStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        return R.ok(cbsPurchaseService.check(statusStreamEntity, getUserId()));
    }
}
