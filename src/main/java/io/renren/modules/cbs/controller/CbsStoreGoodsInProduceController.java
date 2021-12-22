package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsInProduceEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsInProduceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 生产入库表，与cbs_store_goods_in表为1对1继承关系
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@RestController
@RequestMapping("cbs/storegoodsinproduce")
public class CbsStoreGoodsInProduceController {

    private CbsStoreGoodsInProduceService cbsStoreGoodsInProduceService;

    @Autowired
    public void setCbsStoreGoodsInProduceService(CbsStoreGoodsInProduceService cbsStoreGoodsInProduceService) {
        this.cbsStoreGoodsInProduceService = cbsStoreGoodsInProduceService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:storegoodsinproduce:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsStoreGoodsInProduceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoodsinproduce:info")
    public R info(@PathVariable("id") Long id){
		CbsStoreGoodsInProduceEntity cbsStoreGoodsInProduce = cbsStoreGoodsInProduceService.getById(id);

        return R.ok().put("cbsStoreGoodsInProduce", cbsStoreGoodsInProduce);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:storegoodsinproduce:save")
    public R save(@RequestBody CbsStoreGoodsInProduceEntity cbsStoreGoodsInProduce){
		cbsStoreGoodsInProduceService.save(cbsStoreGoodsInProduce);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoodsinproduce:update")
    public R update(@RequestBody CbsStoreGoodsInProduceEntity cbsStoreGoodsInProduce){
		cbsStoreGoodsInProduceService.updateById(cbsStoreGoodsInProduce);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoodsinproduce:delete")
    public R delete(@RequestBody Long[] ids){
		cbsStoreGoodsInProduceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
