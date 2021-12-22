package io.renren.modules.cbs.controller;

import io.renren.common.annotation.OrgCbsLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutEntity;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutStatusStreamEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsOutService;
import io.renren.modules.org_cbs.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 商品出库表，
 * 继承表包含cbs_store_goods_out_produce、cbs_store_goods_out_export，
 * 原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@RestController
@RequestMapping("cbs/storegoodsout")
public class CbsStoreGoodsOutController extends AbstractController {

    private CbsStoreGoodsOutService storeGoodsOutService;

    @Autowired
    public void setCbsStoreGoodsOutService(CbsStoreGoodsOutService storeGoodsOutService) {
        this.storeGoodsOutService = storeGoodsOutService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:storegoodsout:list")
    public R list(@RequestBody CbsStoreGoodsOutEntity entity) {
        PageUtils page = storeGoodsOutService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    /**
     * 创建
     */
    @RequestMapping("/saveReturnId")
    @RequiresPermissions("cbs:storegoodsout:saveReturnId")
    public R saveReturnId(@RequestBody CbsStoreGoodsOutEntity entity) {
        return R.ok(storeGoodsOutService.saveReturnId(entity));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoodsout:info")
    public R info(@PathVariable("id") Long id) {
        CbsStoreGoodsOutEntity cbsStoreGoodsOut = storeGoodsOutService.detail(id);
        return R.ok().put("entity", cbsStoreGoodsOut);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoodsout:update")
    public R update(@RequestBody CbsStoreGoodsOutEntity cbsStoreGoodsOut) {
        storeGoodsOutService.updateAllById(cbsStoreGoodsOut);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoodsout:delete")
    public R delete(@RequestBody Long[] ids) {
        storeGoodsOutService.deleteByIds(ids);
        return R.ok();
    }

    /**
     * 提审
     */
    @OrgCbsLog("出库单提审")
    @ApiOperation("提审")
    @PostMapping("/submit")
    @RequiresPermissions("cbs:storegoodsout:submit")
    public R submit(@RequestBody CbsStoreGoodsOutStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            storeGoodsOutService.submit(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    /**
     * 撤销提审
     */
    @OrgCbsLog("撤销出库单提审")
    @ApiOperation("撤销提审")
    @PostMapping("/submitBack")
    @RequiresPermissions("cbs:storegoodsout:submitBack")
    public R submitBack(@RequestBody CbsStoreGoodsOutStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = storeGoodsOutService.submitBack(statusStreamEntity);
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
    @OrgCbsLog("审核出库单")
    @ApiOperation("审核")
    @PostMapping("/check")
    @RequiresPermissions("cbs:storegoodsout:check")
    public R check(@RequestBody CbsStoreGoodsOutStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            storeGoodsOutService.check(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

}
