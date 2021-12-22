package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutStatusStreamEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsOutStatusStreamService;
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
@RequestMapping("cbs/storegoodsoutstatusstream")
public class CbsStoreGoodsOutStatusStreamController {

    private CbsStoreGoodsOutStatusStreamService cbsStoreGoodsOutStatusStreamService;

    @Autowired
    public void setCbsStoreGoodsOutStatusStreamService(CbsStoreGoodsOutStatusStreamService cbsStoreGoodsOutStatusStreamService) {
        this.cbsStoreGoodsOutStatusStreamService = cbsStoreGoodsOutStatusStreamService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:storegoodsoutstatusstream:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsStoreGoodsOutStatusStreamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoodsoutstatusstream:info")
    public R info(@PathVariable("id") Long id){
		CbsStoreGoodsOutStatusStreamEntity cbsStoreGoodsOutStatusStream = cbsStoreGoodsOutStatusStreamService.getById(id);

        return R.ok().put("cbsStoreGoodsOutStatusStream", cbsStoreGoodsOutStatusStream);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:storegoodsoutstatusstream:save")
    public R save(@RequestBody CbsStoreGoodsOutStatusStreamEntity cbsStoreGoodsOutStatusStream){
		cbsStoreGoodsOutStatusStreamService.save(cbsStoreGoodsOutStatusStream);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoodsoutstatusstream:update")
    public R update(@RequestBody CbsStoreGoodsOutStatusStreamEntity cbsStoreGoodsOutStatusStream){
		cbsStoreGoodsOutStatusStreamService.updateById(cbsStoreGoodsOutStatusStream);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoodsoutstatusstream:delete")
    public R delete(@RequestBody Long[] ids){
		cbsStoreGoodsOutStatusStreamService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
