package io.renren.modules.cbs.controller;

import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsByNoEntity;
import io.renren.modules.cbs.entity.CbsStoreGoodsEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 产品库存表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@RestController
@RequestMapping("cbs/storegoods")
public class CbsStoreGoodsController {

    private CbsStoreGoodsService cbsStoreGoodsService;

    @Autowired
    public void setCbsStoreGoodsService(CbsStoreGoodsService cbsStoreGoodsService) {
        this.cbsStoreGoodsService = cbsStoreGoodsService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:storegoods:list")
    public R list(@RequestBody CbsStoreGoodsByNoEntity entity) {
        return R.ok().put("page", cbsStoreGoodsService.queryIndex(entity));
    }

    /**
     * 通过produceId获取对应加工合同料号原料库存
     * 刨除库存0的情况
     */
    @RequestMapping("/listForOutMaterialToProduce")
    @RequiresPermissions("cbs:storegoods:list")
    public R listForOutMaterialToProduce(@RequestBody Map<String, String> params) {
        Long produceId = Long.valueOf(params.get("produceId"));
        return R.ok().put("list", cbsStoreGoodsService.listForOutMaterialToProduce(produceId));
    }

    /**
     * 通过exportId获取对应加工合同料号产品库存
     * 刨除库存0的情况
     */
    @RequestMapping("/listForOutProductToExport")
    @RequiresPermissions("cbs:storegoods:list")
    public R listForOutProductToExport(@RequestBody Map<String, String> params) {
        Long exportId = Long.valueOf(params.get("exportId"));
        return R.ok().put("list", cbsStoreGoodsService.listForOutProductToExport(exportId));
    }

    /**
     * 通过exportId获取获取对应普通出口合同商品
     * 排除掉加工合同中的所有对应料号库存，这些库存不允许使用在普通出口
     * 刨除库存0的情况
     */
    @RequestMapping("/listForOutExport")
    @RequiresPermissions("cbs:storegoods:list")
    public R listForOutExport(@RequestBody Map<String, String> params) {
        Long exportId = Long.valueOf(params.get("exportId"));
        return R.ok().put("list", cbsStoreGoodsService.listForOutExport(exportId));
    }

    /**
     * 通过sellId获取获取对应国内销售合同商品
     * 排除掉加工合同中的所有对应料号库存，这些库存不允许使用在国内销售
     * 刨除库存0的情况
     */
    @RequestMapping("/listForOutSell")
    @RequiresPermissions("cbs:storegoods:list")
    public R listForOutSell(@RequestBody Map<String, String> params) {
        Long sellId = Long.valueOf(params.get("sellId"));
        return R.ok().put("list", cbsStoreGoodsService.listForOutSell(sellId));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoods:info")
    public R info(@PathVariable("id") Long id) {
        CbsStoreGoodsEntity cbsStoreGoods = cbsStoreGoodsService.getById(id);

        return R.ok().put("cbsStoreGoods", cbsStoreGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:storegoods:save")
    public R save(@RequestBody CbsStoreGoodsEntity cbsStoreGoods) {
        cbsStoreGoodsService.save(cbsStoreGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoods:update")
    public R update(@RequestBody CbsStoreGoodsEntity cbsStoreGoods) {
        cbsStoreGoodsService.updateById(cbsStoreGoods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoods:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsStoreGoodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
