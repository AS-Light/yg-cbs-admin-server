package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrCorrelationReasonEntity;
import io.renren.modules.thr.service.ThrCorrelationReasonService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 关联理由
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/correlationreason")
public class ThrCorrelationReasonController {
    @Autowired
    private ThrCorrelationReasonService thrCorrelationReasonService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:correlationreason:list")
    public R list(@RequestBody ThrCorrelationReasonEntity entity){
        PageUtils page = thrCorrelationReasonService.queryIndex(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:correlationreason:info")
    public R info(@PathVariable("id") Integer id){
		ThrCorrelationReasonEntity thrCorrelationReason = thrCorrelationReasonService.getById(id);

        return R.ok().put("thrCorrelationReason", thrCorrelationReason);
    }

    /**
     * 通过code获取，必须包含 correlationReasonFlag
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:correlationreason:info")
    public R infoByCode(@RequestBody ThrCorrelationReasonEntity entity) {
        if (entity.getCorrelationReasonFlag() != null) {
            return R.ok().put("entity", thrCorrelationReasonService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:correlationreason:save")
    public R save(@RequestBody ThrCorrelationReasonEntity thrCorrelationReason){
		thrCorrelationReasonService.save(thrCorrelationReason);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:correlationreason:update")
    public R update(@RequestBody ThrCorrelationReasonEntity thrCorrelationReason){
		thrCorrelationReasonService.updateById(thrCorrelationReason);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:correlationreason:delete")
    public R delete(@RequestBody Integer[] ids){
		thrCorrelationReasonService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
