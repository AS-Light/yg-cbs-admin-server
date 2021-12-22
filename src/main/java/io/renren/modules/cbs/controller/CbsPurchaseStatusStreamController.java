package io.renren.modules.cbs.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.cbs.entity.CbsPurchaseStatusStreamEntity;
import io.renren.modules.cbs.service.CbsPurchaseStatusStreamService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@RestController
@RequestMapping("cbs/purchasestatusstream")
public class CbsPurchaseStatusStreamController {
    @Autowired
    private CbsPurchaseStatusStreamService cbsPurchaseStatusStreamService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:purchasestatusstream:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsPurchaseStatusStreamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:purchasestatusstream:info")
    public R info(@PathVariable("id") Long id){
		CbsPurchaseStatusStreamEntity cbsPurchaseStatusStream = cbsPurchaseStatusStreamService.getById(id);

        return R.ok().put("cbsPurchaseStatusStream", cbsPurchaseStatusStream);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:purchasestatusstream:save")
    public R save(@RequestBody CbsPurchaseStatusStreamEntity cbsPurchaseStatusStream){
		cbsPurchaseStatusStreamService.save(cbsPurchaseStatusStream);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:purchasestatusstream:update")
    public R update(@RequestBody CbsPurchaseStatusStreamEntity cbsPurchaseStatusStream){
		cbsPurchaseStatusStreamService.updateById(cbsPurchaseStatusStream);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:purchasestatusstream:delete")
    public R delete(@RequestBody Long[] ids){
		cbsPurchaseStatusStreamService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
