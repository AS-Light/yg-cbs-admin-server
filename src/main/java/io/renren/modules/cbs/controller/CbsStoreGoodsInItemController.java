package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsInItemEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsInItemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 商品入库表，
继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，
原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@RestController
@RequestMapping("cbs/storegoodsinitem")
public class CbsStoreGoodsInItemController {

    private CbsStoreGoodsInItemService cbsStoreGoodsInItemService;

    @Autowired
    public void setCbsStoreGoodsInItemService(CbsStoreGoodsInItemService cbsStoreGoodsInItemService) {
        this.cbsStoreGoodsInItemService = cbsStoreGoodsInItemService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:storegoodsinitem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsStoreGoodsInItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoodsinitem:info")
    public R info(@PathVariable("id") Long id){
		CbsStoreGoodsInItemEntity cbsStoreGoodsInItem = cbsStoreGoodsInItemService.getById(id);

        return R.ok().put("cbsStoreGoodsInItem", cbsStoreGoodsInItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:storegoodsinitem:save")
    public R save(@RequestBody CbsStoreGoodsInItemEntity cbsStoreGoodsInItem){
		cbsStoreGoodsInItemService.save(cbsStoreGoodsInItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoodsinitem:update")
    public R update(@RequestBody CbsStoreGoodsInItemEntity cbsStoreGoodsInItem){
		cbsStoreGoodsInItemService.updateById(cbsStoreGoodsInItem);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoodsinitem:delete")
    public R delete(@RequestBody Long[] ids){
		cbsStoreGoodsInItemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
