package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsContractProcessingRecordEntity;
import io.renren.modules.cbs.service.CbsContractProcessingRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * （合同）海关（手册）备案表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@RestController
@RequestMapping("cbs/contractprocessingrecord")
public class CbsContractProcessingRecordController {
    @Autowired
    private CbsContractProcessingRecordService cbsContractProcessingRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:contractprocessingrecord:list")
    public R list(@RequestBody CbsContractProcessingRecordEntity entity) {
        PageUtils page = cbsContractProcessingRecordService.queryPage(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:contractprocessingrecord:info")
    public R info(@PathVariable("id") Long id) {
        CbsContractProcessingRecordEntity cbsContractProcessingRecord = cbsContractProcessingRecordService.getById(id);
        return R.ok().put("cbsContractProcessingRecord", cbsContractProcessingRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:contractprocessingrecord:save")
    public R save(@RequestBody CbsContractProcessingRecordEntity cbsContractProcessingRecord) {
        cbsContractProcessingRecordService.save(cbsContractProcessingRecord);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:contractprocessingrecord:update")
    public R update(@RequestBody CbsContractProcessingRecordEntity cbsContractProcessingRecord) {
        cbsContractProcessingRecordService.updateById(cbsContractProcessingRecord);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:contractprocessingrecord:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsContractProcessingRecordService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
