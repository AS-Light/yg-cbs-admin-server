package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutProduceEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsOutProduceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 生产出库表，与cbs_store_goods_out表为1对1继承关系
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@RestController
@RequestMapping("cbs/storegoodsoutproduce")
public class CbsStoreGoodsOutProduceController {

    private CbsStoreGoodsOutProduceService cbsStoreGoodsOutProduceService;

    @Autowired
    public void setCbsStoreGoodsOutProduceService(CbsStoreGoodsOutProduceService cbsStoreGoodsOutProduceService) {
        this.cbsStoreGoodsOutProduceService = cbsStoreGoodsOutProduceService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:storegoodsoutproduce:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsStoreGoodsOutProduceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoodsoutproduce:info")
    public R info(@PathVariable("id") Long id) {
        CbsStoreGoodsOutProduceEntity cbsStoreGoodsOutProduce = cbsStoreGoodsOutProduceService.getById(id);

        return R.ok().put("cbsStoreGoodsOutProduce", cbsStoreGoodsOutProduce);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:storegoodsoutproduce:save")
    public R save(@RequestBody CbsStoreGoodsOutProduceEntity cbsStoreGoodsOutProduce) {
        cbsStoreGoodsOutProduceService.save(cbsStoreGoodsOutProduce);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoodsoutproduce:update")
    public R update(@RequestBody CbsStoreGoodsOutProduceEntity cbsStoreGoodsOutProduce) {
        cbsStoreGoodsOutProduceService.updateById(cbsStoreGoodsOutProduce);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoodsoutproduce:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsStoreGoodsOutProduceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
