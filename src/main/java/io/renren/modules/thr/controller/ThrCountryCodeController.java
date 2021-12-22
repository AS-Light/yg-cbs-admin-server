package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrCountryCodeEntity;
import io.renren.modules.thr.service.ThrCountryCodeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-21 15:45:17
 */
@RestController
@RequestMapping("thr/countrycode")
public class ThrCountryCodeController {
    @Autowired
    private ThrCountryCodeService thrCountryCodeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:countrycode:list")
    public R list(@RequestBody ThrCountryCodeEntity entity){
        PageUtils page = thrCountryCodeService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:countrycode:info")
    public R info(@PathVariable("id") Long id){
		ThrCountryCodeEntity thrCountryCode = thrCountryCodeService.getById(id);

        return R.ok().put("thrCountryCode", thrCountryCode);
    }

    /**
     * 通过code获取，必须包含 code
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:countrycode:info")
    public R infoByCode(@RequestBody ThrCountryCodeEntity entity) {
        if (entity.getCode() != null) {
            return R.ok().put("entity", thrCountryCodeService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:countrycode:save")
    public R save(@RequestBody ThrCountryCodeEntity thrCountryCode){
		thrCountryCodeService.save(thrCountryCode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:countrycode:update")
    public R update(@RequestBody ThrCountryCodeEntity thrCountryCode){
		thrCountryCodeService.updateById(thrCountryCode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:countrycode:delete")
    public R delete(@RequestBody Long[] ids){
		thrCountryCodeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
