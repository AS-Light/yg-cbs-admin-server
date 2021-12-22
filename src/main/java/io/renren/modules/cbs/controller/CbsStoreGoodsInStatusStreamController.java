package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsInStatusStreamEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsInStatusStreamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-17 12:51:23
 */
@RestController
@RequestMapping("cbs/storegoodsinstatusstream")
public class CbsStoreGoodsInStatusStreamController {

    private CbsStoreGoodsInStatusStreamService cbsStoreGoodsInStatusStreamService;

    @Autowired
    public void setCbsStoreGoodsInStatusStreamService(CbsStoreGoodsInStatusStreamService cbsStoreGoodsInStatusStreamService) {
        this.cbsStoreGoodsInStatusStreamService = cbsStoreGoodsInStatusStreamService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:storegoodsinstatusstream:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsStoreGoodsInStatusStreamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoodsinstatusstream:info")
    public R info(@PathVariable("id") Long id){
		CbsStoreGoodsInStatusStreamEntity cbsStoreGoodsInStatusStream = cbsStoreGoodsInStatusStreamService.getById(id);

        return R.ok().put("cbsStoreGoodsInStatusStream", cbsStoreGoodsInStatusStream);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:storegoodsinstatusstream:save")
    public R save(@RequestBody CbsStoreGoodsInStatusStreamEntity cbsStoreGoodsInStatusStream){
		cbsStoreGoodsInStatusStreamService.save(cbsStoreGoodsInStatusStream);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoodsinstatusstream:update")
    public R update(@RequestBody CbsStoreGoodsInStatusStreamEntity cbsStoreGoodsInStatusStream){
		cbsStoreGoodsInStatusStreamService.updateById(cbsStoreGoodsInStatusStream);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoodsinstatusstream:delete")
    public R delete(@RequestBody Long[] ids){
		cbsStoreGoodsInStatusStreamService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
