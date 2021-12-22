package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsProduceGoodsEntity;
import io.renren.modules.cbs.service.CbsProduceGoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 生产的商品缓存
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:47:33
 */
@RestController
@RequestMapping("cbs/producegoods")
public class CbsProduceGoodsController {
    @Autowired
    private CbsProduceGoodsService cbsProduceGoodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:producegoods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsProduceGoodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:producegoods:info")
    public R info(@PathVariable("id") Long id){
		CbsProduceGoodsEntity cbsProduceGoods = cbsProduceGoodsService.getById(id);

        return R.ok().put("cbsProduceGoods", cbsProduceGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:producegoods:save")
    public R save(@RequestBody CbsProduceGoodsEntity cbsProduceGoods){
		cbsProduceGoodsService.save(cbsProduceGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:producegoods:update")
    public R update(@RequestBody CbsProduceGoodsEntity cbsProduceGoods){
		cbsProduceGoodsService.updateById(cbsProduceGoods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:producegoods:delete")
    public R delete(@RequestBody Long[] ids){
		cbsProduceGoodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
