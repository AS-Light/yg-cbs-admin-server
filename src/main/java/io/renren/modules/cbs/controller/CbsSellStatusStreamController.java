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

import io.renren.modules.cbs.entity.CbsSellStatusStreamEntity;
import io.renren.modules.cbs.service.CbsSellStatusStreamService;
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
@RequestMapping("cbs/sellstatusstream")
public class CbsSellStatusStreamController {
    @Autowired
    private CbsSellStatusStreamService cbsSellStatusStreamService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:sellstatusstream:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsSellStatusStreamService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:sellstatusstream:info")
    public R info(@PathVariable("id") Long id){
		CbsSellStatusStreamEntity cbsSellStatusStream = cbsSellStatusStreamService.getById(id);

        return R.ok().put("cbsSellStatusStream", cbsSellStatusStream);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:sellstatusstream:save")
    public R save(@RequestBody CbsSellStatusStreamEntity cbsSellStatusStream){
		cbsSellStatusStreamService.save(cbsSellStatusStream);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:sellstatusstream:update")
    public R update(@RequestBody CbsSellStatusStreamEntity cbsSellStatusStream){
		cbsSellStatusStreamService.updateById(cbsSellStatusStream);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:sellstatusstream:delete")
    public R delete(@RequestBody Long[] ids){
		cbsSellStatusStreamService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
