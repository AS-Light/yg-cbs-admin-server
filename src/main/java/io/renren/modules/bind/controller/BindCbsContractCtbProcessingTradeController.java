package io.renren.modules.bind.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.bind.entity.BindCbsContractCtbProcessingTradeEntity;
import io.renren.modules.bind.service.BindCbsContractCtbProcessingTradeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;



/**
 * 关联表：cbs_contract | ctb_order_prodcessing_trade
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:34:21
 */
@RestController
@RequestMapping("bind/cbsContractCtbProcessingTrade")
public class BindCbsContractCtbProcessingTradeController {
    @Autowired
    private BindCbsContractCtbProcessingTradeService bindCbsContractCtbProcessingTradeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("bind:cbscontractctbprocessingtrade:list")
    public R list(@RequestBody BindCbsContractCtbProcessingTradeEntity entity){
        PageUtils page = bindCbsContractCtbProcessingTradeService.queryIndex(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bind:cbscontractctbprocessingtrade:info")
    public R info(@PathVariable("id") Long id){
		BindCbsContractCtbProcessingTradeEntity bindCbsContractCtbProcessingTrade = bindCbsContractCtbProcessingTradeService.getById(id);

        return R.ok().put("bindCbsContractCtbProcessingTrade", bindCbsContractCtbProcessingTrade);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("bind:cbscontractctbprocessingtrade:save")
    public R save(@RequestBody BindCbsContractCtbProcessingTradeEntity bindCbsContractCtbProcessingTrade){
		bindCbsContractCtbProcessingTradeService.save(bindCbsContractCtbProcessingTrade);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("bind:cbscontractctbprocessingtrade:update")
    public R update(@RequestBody BindCbsContractCtbProcessingTradeEntity bindCbsContractCtbProcessingTrade){
		bindCbsContractCtbProcessingTradeService.updateById(bindCbsContractCtbProcessingTrade);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("bind:cbscontractctbprocessingtrade:delete")
    public R delete(@RequestBody Long[] ids){
		bindCbsContractCtbProcessingTradeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
