package io.renren.modules.cbs.controller;

import io.renren.common.annotation.OrgCbsLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsInEntity;
import io.renren.modules.cbs.entity.CbsStoreGoodsInImportEntity;
import io.renren.modules.cbs.entity.CbsStoreGoodsInStatusStreamEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsInService;
import io.renren.modules.org_cbs.controller.AbstractController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商品入库表，
 * 继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，
 * 原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@RestController
@RequestMapping("cbs/storegoodsin")
public class CbsStoreGoodsInController extends AbstractController {

    private CbsStoreGoodsInService storeGoodsInService;

    @Autowired
    public void setStoreGoodsInService(CbsStoreGoodsInService storeGoodsInService) {
        this.storeGoodsInService = storeGoodsInService;
    }

    @ApiOperation("创建返回主键")
    @PostMapping("/saveReturnId")
    @RequiresPermissions("cbs:storegoodsin:saveReturnId")
    public R saveReturnId(@RequestBody CbsStoreGoodsInEntity entity) {
        entity.setOperator(getUserId());
        return R.ok(storeGoodsInService.saveReturnId(entity));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:storegoodsin:list")
    public R list(@RequestBody CbsStoreGoodsInEntity entity) {
        PageUtils page = storeGoodsInService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoodsin:info")
    public R info(@PathVariable("id") Long id) {
        CbsStoreGoodsInEntity entity = storeGoodsInService.detail(id);
        return R.ok().put("entity", entity);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoodsin:update")
    public R update(@RequestBody CbsStoreGoodsInEntity cbsStoreGoodsIn) {
        storeGoodsInService.updateAllById(cbsStoreGoodsIn);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoodsin:delete")
    public R delete(@RequestBody Long[] ids) {
        storeGoodsInService.deleteByIds(ids);
        return R.ok();
    }

    /**
     * 获取生产出库用入库入库列表
     */
    @RequestMapping("/listImportInByOutToProduceId")
    @RequiresPermissions("cbs:storegoodsin:listImportInByOutToProduceId")
    public R listImportInByOutToProduceId(@RequestBody Long produceId) {
        List<CbsStoreGoodsInImportEntity> list = storeGoodsInService.listImportInByOutToProduceId(produceId);
        return R.ok().put("list", list);
    }

    /**
     * 提审
     */
    @OrgCbsLog("入库单提审")
    @ApiOperation("提审")
    @PostMapping("/submit")
    @RequiresPermissions("cbs:storegoodsin:submit")
    public R submit(@RequestBody CbsStoreGoodsInStatusStreamEntity statusStreamEntity) {
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
    @OrgCbsLog("撤销入库单提审")
    @ApiOperation("撤销提审")
    @PostMapping("/submitBack")
    @RequiresPermissions("cbs:storegoodsin:submitBack")
    public R submitBack(@RequestBody CbsStoreGoodsInStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = storeGoodsInService.submitBack(statusStreamEntity);
        switch (result) {
            case -1:
                return R.error("入库单状态不合法，待审核状态入库单才能撤销提审");
            case -2:
                return R.error("未知异常，请联系管理员");
            case -3:
                return R.error("没有找到这个入库单,请联系管理员");
            default:
                return R.ok(result);
        }
    }

    /**
     * 审核
     */
    @OrgCbsLog("审核入库单")
    @ApiOperation("审核")
    @PostMapping("/check")
    @RequiresPermissions("cbs:storegoodsin:check")
    public R check(@RequestBody CbsStoreGoodsInStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            storeGoodsInService.check(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }
}
