package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsProduceGoodsStreamItemEntity;
import io.renren.modules.cbs.service.CbsProduceGoodsStreamItemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * cbs_produce_goods_stream 的子表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:15:37
 */
@RestController
@RequestMapping("cbs/producegoodsstreamitem")
public class CbsProduceGoodsStreamItemController {
    @Autowired
    private CbsProduceGoodsStreamItemService cbsProduceGoodsStreamItemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:producegoodsstreamitem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsProduceGoodsStreamItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:producegoodsstreamitem:info")
    public R info(@PathVariable("id") Long id){
		CbsProduceGoodsStreamItemEntity cbsProduceGoodsStreamItem = cbsProduceGoodsStreamItemService.getById(id);

        return R.ok().put("cbsProduceGoodsStreamItem", cbsProduceGoodsStreamItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:producegoodsstreamitem:save")
    public R save(@RequestBody CbsProduceGoodsStreamItemEntity cbsProduceGoodsStreamItem){
		cbsProduceGoodsStreamItemService.save(cbsProduceGoodsStreamItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:producegoodsstreamitem:update")
    public R update(@RequestBody CbsProduceGoodsStreamItemEntity cbsProduceGoodsStreamItem){
		cbsProduceGoodsStreamItemService.updateById(cbsProduceGoodsStreamItem);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:producegoodsstreamitem:delete")
    public R delete(@RequestBody Long[] ids){
		cbsProduceGoodsStreamItemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
