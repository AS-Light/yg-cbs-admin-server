package io.renren.modules.cbs.controller;

import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutSellEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsOutSellService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;



/**
 * 国内采购入库表，与cbs_store_goods_in表为1对1继承关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@RestController
@RequestMapping("cbs/storegoodsoutsell")
public class CbsStoreGoodsOutSellController {
    @Autowired
    private CbsStoreGoodsOutSellService cbsStoreGoodsOutSellService;

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoodsoutsell:info")
    public R info(@PathVariable("id") Long id){
		CbsStoreGoodsOutSellEntity cbsStoreGoodsOutSell = cbsStoreGoodsOutSellService.getById(id);

        return R.ok().put("cbsStoreGoodsOutSell", cbsStoreGoodsOutSell);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:storegoodsoutsell:save")
    public R save(@RequestBody CbsStoreGoodsOutSellEntity cbsStoreGoodsOutSell){
		cbsStoreGoodsOutSellService.save(cbsStoreGoodsOutSell);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoodsoutsell:update")
    public R update(@RequestBody CbsStoreGoodsOutSellEntity cbsStoreGoodsOutSell){
		cbsStoreGoodsOutSellService.updateById(cbsStoreGoodsOutSell);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoodsoutsell:delete")
    public R delete(@RequestBody Long[] ids){
		cbsStoreGoodsOutSellService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
