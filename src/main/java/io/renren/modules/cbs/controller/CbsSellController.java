package io.renren.modules.cbs.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.renren.common.annotation.OrgCbsLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsSellEntity;
import io.renren.modules.cbs.entity.CbsSellStatusStreamEntity;
import io.renren.modules.cbs.service.CbsSellService;
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
@RequestMapping("cbs/sell")
public class CbsSellController extends AbstractController {
    private CbsSellService cbsSellService;

    @Autowired
    public void setCbsSellService(CbsSellService cbsSellService) {
        this.cbsSellService = cbsSellService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:sell:list")
    public R list(@RequestBody CbsSellEntity entity) {
        PageUtils page = cbsSellService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:sell:info")
    public R info(@PathVariable("id") Long id) {
        CbsSellEntity cbsSell = cbsSellService.detail(id);
        return R.ok().put("cbsSell", cbsSell);
    }

    @ApiOperation("保存返回主键")
    @PostMapping("/saveReturnId")
    @RequiresPermissions("cbs:sell:save")
    public R saveReturnId(@RequestBody CbsSellEntity entity) {
        try {
            entity.setOperator(getUserId());
            Long id = cbsSellService.saveReturnId(entity);
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
    @RequiresPermissions("cbs:sell:update")
    public R update(@RequestBody CbsSellEntity entity) {
        return R.ok(cbsSellService.updateAllById(entity));
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:sell:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsSellService.deleteByIds(ids);
        return R.ok();
    }

    @OrgCbsLog("国内发货单提审")
    @ApiOperation("提审")
    @PostMapping("/submit")
    @RequiresPermissions("cbs:sell:submit")
    public R submit(@RequestBody CbsSellStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsSellService.submit(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("撤销国内发货单提审")
    @ApiOperation("撤销提审")
    @PostMapping("/submitBack")
    @RequiresPermissions("cbs:Sell:submitBack")
    public R submitBack(@RequestBody CbsSellStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = cbsSellService.submitBack(statusStreamEntity);
        switch (result) {
            case -1:
                return R.error("国内发货单状态不合法，待审核状态国内发货单才能撤销提审");
            case -2:
                return R.error("未知异常，请联系管理员");
            case -3:
                return R.error("没有找到这个国内发货单,请联系管理员");
            default:
                return R.ok(result);
        }
    }

    @OrgCbsLog("审核国内发货单")
    @ApiOperation("审核")
    @PostMapping("/check")
    @RequiresPermissions("cbs:sell:check")
    public R check(@RequestBody CbsSellStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsSellService.check(statusStreamEntity, getUserId());
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("补充国内发货单（对方）签收证明")
    @ApiOperation("补充国内发货单（对方）签收证明")
    @PostMapping("/updateReceipt")
    @RequiresPermissions("cbs:sell:update")
    public R updateReceipt(@RequestBody CbsSellEntity sellEntity) {
        try {
            cbsSellService.updateReceipt(sellEntity.getId(), sellEntity.getImgSellReceiptList(), getUserId());
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("审核国内发货单（对方）签收证明")
    @ApiOperation("审核国内发货单（对方）签收证明")
    @PostMapping("/checkReceipt")
    @RequiresPermissions("cbs:sell:check")
    public R checkReceipt(@RequestBody CbsSellEntity sellEntity) {
        try {
            cbsSellService.checkReceipt(sellEntity.getId(), getUserId());
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

}
