package io.renren.modules.cbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsDirectoryTransitCompanyEntity;
import io.renren.modules.cbs.service.CbsDirectoryTransitCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 国内运输公司名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@RestController
@RequestMapping("cbs/directory/transitcompany")
public class CbsDirectoryTransitCompanyController {

    private CbsDirectoryTransitCompanyService cbsDirectoryTransitCompanyService;

    @Autowired
    public void setCbsDirectoryTransitCompanyService(CbsDirectoryTransitCompanyService cbsDirectoryTransitCompanyService) {
        this.cbsDirectoryTransitCompanyService = cbsDirectoryTransitCompanyService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:directory:transitcompany:list")
    public R list(@RequestBody CbsDirectoryTransitCompanyEntity entity) {
        PageUtils page = cbsDirectoryTransitCompanyService.queryPage(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:directory:transitcompany:info")
    public R info(@PathVariable("id") Long id) {
        CbsDirectoryTransitCompanyEntity cbsDirectoryTransitCompany = cbsDirectoryTransitCompanyService.getById(id);
        return R.ok().put("cbsDirectoryTransitCompany", cbsDirectoryTransitCompany);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:directory:transitcompany:save")
    public R save(@RequestBody CbsDirectoryTransitCompanyEntity cbsDirectoryTransitCompany) {
        cbsDirectoryTransitCompanyService.save(cbsDirectoryTransitCompany);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:directory:transitcompany:update")
    public R update(@RequestBody CbsDirectoryTransitCompanyEntity cbsDirectoryTransitCompany) {
        cbsDirectoryTransitCompanyService.updateById(cbsDirectoryTransitCompany);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:directory:transitcompany:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsDirectoryTransitCompanyService.update(CbsDirectoryTransitCompanyEntity.builder().available(0).build(), new QueryWrapper<CbsDirectoryTransitCompanyEntity>().in("id", ids));
        return R.ok();
    }

}
