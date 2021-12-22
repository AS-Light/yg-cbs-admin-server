package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsContractGoodsEntity;
import io.renren.modules.cbs.service.CbsContractGoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 原材料名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 10:19:45
 */
@RestController
@RequestMapping("cbs/contractgoods")
public class CbsContractGoodsController {

    private CbsContractGoodsService cbsContractGoodsService;

    @Autowired
    public void setCbsContractGoodsService(CbsContractGoodsService cbsContractGoodsService) {
        this.cbsContractGoodsService = cbsContractGoodsService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:contractgoods:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsContractGoodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:contractgoods:info")
    public R info(@PathVariable("id") Long id) {
        CbsContractGoodsEntity cbsContractGoods = cbsContractGoodsService.getById(id);

        return R.ok().put("cbsContractGoods", cbsContractGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:contractgoods:save")
    public R save(@RequestBody CbsContractGoodsEntity cbsContractGoods) {
        cbsContractGoodsService.save(cbsContractGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:contractgoods:update")
    public R update(@RequestBody CbsContractGoodsEntity cbsContractGoods) {
        cbsContractGoodsService.updateById(cbsContractGoods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:contractgoods:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsContractGoodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
