package io.renren.modules.ctb.controller;

import io.renren.common.annotation.OrgCtbLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbStoreGoodsInEntity;
import io.renren.modules.ctb.entity.CtbStoreGoodsInStatusStreamEntity;
import io.renren.modules.ctb.service.CtbStoreGoodsInService;
import io.renren.modules.org_ctb.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;


/**
 * 商品入库表
 */
@RestController
@RequestMapping("ctb/storegoodsin")
public class CtbStoreGoodsInController extends AbstractController {
    private CtbStoreGoodsInService storeGoodsInService;

    public CtbStoreGoodsInController(
            CtbStoreGoodsInService storeGoodsInService
    ) {
        this.storeGoodsInService = storeGoodsInService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:storegoodsout:list")
    public R list(@RequestBody CtbStoreGoodsInEntity entity) {
        PageUtils page = storeGoodsInService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    /**
     * 创建
     */
    @RequestMapping("/saveReturnId")
    @RequiresPermissions("ctb:storegoodsout:saveReturnId")
    public R saveReturnId(@RequestBody CtbStoreGoodsInEntity entity) {
        return R.ok(storeGoodsInService.saveReturnId(entity));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:storegoodsout:info")
    public R info(@PathVariable("id") Long id) {
        CtbStoreGoodsInEntity ctbStoreGoodsOut = storeGoodsInService.detail(id);
        return R.ok().put("entity", ctbStoreGoodsOut);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:storegoodsout:update")
    public R update(@RequestBody CtbStoreGoodsInEntity ctbStoreGoodsOut) {
        storeGoodsInService.updateAllById(ctbStoreGoodsOut);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:storegoodsout:delete")
    public R delete(@RequestBody Long[] ids) {
        storeGoodsInService.deleteByIds(ids);
        return R.ok();
    }

    /**
     * 提审
     */
    @OrgCtbLog("出库单提审")
    @ApiOperation("提审")
    @PostMapping("/submit")
    @RequiresPermissions("ctb:storegoodsout:submit")
    public R submit(@RequestBody CtbStoreGoodsInStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            storeGoodsInService.submit(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    /**
     * 撤销提审
     */
    @OrgCtbLog("撤销出库单提审")
    @ApiOperation("撤销提审")
    @PostMapping("/submitBack")
    @RequiresPermissions("ctb:storegoodsout:submitBack")
    public R submitBack(@RequestBody CtbStoreGoodsInStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = storeGoodsInService.submitBack(statusStreamEntity);
        switch (result) {
            case -1:
                return R.error("出库单状态不合法，待审核状态出库单才能撤销提审");
            case -2:
                return R.error("未知异常，请联系管理员");
            case -3:
                return R.error("没有找到这个出库单,请联系管理员");
            default:
                return R.ok(result);
        }
    }

    /**
     * 审核
     */
    @OrgCtbLog("审核出库单")
    @ApiOperation("审核")
    @PostMapping("/check")
    @RequiresPermissions("ctb:storegoodsout:check")
    public R check(@RequestBody CtbStoreGoodsInStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            storeGoodsInService.check(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }
}
