package io.renren.modules.cst.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.cst.entity.CstDecLicenseDocusEntity;
import io.renren.modules.cst.service.CstDecLicenseDocusService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 进出口报关单随附单证
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@RestController
@RequestMapping("cst/dec/licensedocus")
public class CstDecLicenseDocusController {
    @Autowired
    private CstDecLicenseDocusService cstDecLicenseDocusService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:declicensedocus:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstDecLicenseDocusService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:declicensedocus:info")
    public R info(@PathVariable("id") Long id){
		CstDecLicenseDocusEntity cstDecLicenseDocus = cstDecLicenseDocusService.getById(id);

        return R.ok().put("cstDecLicenseDocus", cstDecLicenseDocus);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:declicensedocus:save")
    public R save(@RequestBody CstDecLicenseDocusEntity cstDecLicenseDocus){
		cstDecLicenseDocusService.save(cstDecLicenseDocus);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:declicensedocus:update")
    public R update(@RequestBody CstDecLicenseDocusEntity cstDecLicenseDocus){
		cstDecLicenseDocusService.updateById(cstDecLicenseDocus);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:declicensedocus:delete")
    public R delete(@RequestBody Long[] ids){
		cstDecLicenseDocusService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
