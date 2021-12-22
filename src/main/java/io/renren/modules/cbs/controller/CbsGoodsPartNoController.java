package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsGoodsPartNoEntity;
import io.renren.modules.cbs.service.CbsGoodsPartNoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-07 18:33:29
 */
@RestController
@RequestMapping("cbs/goodspartno")
public class CbsGoodsPartNoController {

    private CbsGoodsPartNoService cbsGoodsPartNoService;

    @Autowired
    public void setCbsGoodsPartNoService(CbsGoodsPartNoService cbsGoodsPartNoService) {
        this.cbsGoodsPartNoService = cbsGoodsPartNoService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:goodspartno:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsGoodsPartNoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{goodsPartNo}")
    @RequiresPermissions("cbs:goodspartno:info")
    public R info(@PathVariable("goodsPartNo") Long goodsPartNo) {
        CbsGoodsPartNoEntity cbsGoodsPartNo = cbsGoodsPartNoService.getById(goodsPartNo);

        return R.ok().put("cbsGoodsPartNo", cbsGoodsPartNo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:goodspartno:save")
    public R save(@RequestBody CbsGoodsPartNoEntity cbsGoodsPartNo) {
        cbsGoodsPartNoService.save(cbsGoodsPartNo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:goodspartno:update")
    public R update(@RequestBody CbsGoodsPartNoEntity cbsGoodsPartNo) {
        cbsGoodsPartNoService.updateById(cbsGoodsPartNo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:goodspartno:delete")
    public R delete(@RequestBody Long[] goodsPartNos) {
        cbsGoodsPartNoService.removeByIds(Arrays.asList(goodsPartNos));

        return R.ok();
    }

}
