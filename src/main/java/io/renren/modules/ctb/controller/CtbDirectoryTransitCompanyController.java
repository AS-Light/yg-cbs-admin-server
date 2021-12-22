package io.renren.modules.ctb.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbDirectoryTransitCompanyEntity;
import io.renren.modules.ctb.service.CtbDirectoryTransitCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 国内运输公司名录表（报关行副本）
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/directory/transitcompany")
public class CtbDirectoryTransitCompanyController {
    @Autowired
    private CtbDirectoryTransitCompanyService ctbDirectoryTransitCompanyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:directory:transitcompany:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ctbDirectoryTransitCompanyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:directory:transitcompany:info")
    public R info(@PathVariable("id") Long id){
		CtbDirectoryTransitCompanyEntity ctbDirectoryTransitCompany = ctbDirectoryTransitCompanyService.getById(id);

        return R.ok().put("ctbDirectoryTransitCompany", ctbDirectoryTransitCompany);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:directory:transitcompany:save")
    public R save(@RequestBody CtbDirectoryTransitCompanyEntity ctbDirectoryTransitCompany){
		ctbDirectoryTransitCompanyService.save(ctbDirectoryTransitCompany);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:directory:transitcompany:update")
    public R update(@RequestBody CtbDirectoryTransitCompanyEntity ctbDirectoryTransitCompany){
		ctbDirectoryTransitCompanyService.updateById(ctbDirectoryTransitCompany);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:directory:transitcompany:delete")
    public R delete(@RequestBody Long[] ids){
		ctbDirectoryTransitCompanyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
