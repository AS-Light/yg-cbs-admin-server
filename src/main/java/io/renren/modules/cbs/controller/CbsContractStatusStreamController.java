package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsContractStatusStreamEntity;
import io.renren.modules.cbs.service.CbsContractStatusStreamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-16 16:45:20
 */
@RestController
@RequestMapping("cbs/contractstatusstream")
public class CbsContractStatusStreamController {
    @Autowired
    private CbsContractStatusStreamService cbsContractStatusStreamService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:contractstatusstream:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsContractStatusStreamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:contractstatusstream:info")
    public R info(@PathVariable("id") Long id){
		CbsContractStatusStreamEntity cbsContractStatusStream = cbsContractStatusStreamService.getById(id);

        return R.ok().put("cbsContractStatusStream", cbsContractStatusStream);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:contractstatusstream:save")
    public R save(@RequestBody CbsContractStatusStreamEntity cbsContractStatusStream){
		cbsContractStatusStreamService.save(cbsContractStatusStream);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:contractstatusstream:update")
    public R update(@RequestBody CbsContractStatusStreamEntity cbsContractStatusStream){
		cbsContractStatusStreamService.updateById(cbsContractStatusStream);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:contractstatusstream:delete")
    public R delete(@RequestBody Long[] ids){
		cbsContractStatusStreamService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
