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

import io.renren.modules.cst.entity.CstDecRequestCertEntity;
import io.renren.modules.cst.service.CstDecRequestCertService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 进出口报关单 申请单证信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@RestController
@RequestMapping("cst/dec/requestcert")
public class CstDecRequestCertController {
    @Autowired
    private CstDecRequestCertService cstDecRequestCertService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:decrequestcert:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstDecRequestCertService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:decrequestcert:info")
    public R info(@PathVariable("id") Long id){
		CstDecRequestCertEntity cstDecRequestCert = cstDecRequestCertService.getById(id);

        return R.ok().put("cstDecRequestCert", cstDecRequestCert);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:decrequestcert:save")
    public R save(@RequestBody CstDecRequestCertEntity cstDecRequestCert){
		cstDecRequestCertService.save(cstDecRequestCert);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:decrequestcert:update")
    public R update(@RequestBody CstDecRequestCertEntity cstDecRequestCert){
		cstDecRequestCertService.updateById(cstDecRequestCert);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:decrequestcert:delete")
    public R delete(@RequestBody Long[] ids){
		cstDecRequestCertService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
