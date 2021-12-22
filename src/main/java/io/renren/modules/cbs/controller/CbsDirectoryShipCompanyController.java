package io.renren.modules.cbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsDirectoryShipCompanyEntity;
import io.renren.modules.cbs.service.CbsDirectoryShipCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 船务公司名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@RestController
@RequestMapping("cbs/directory/shipcompany")
public class CbsDirectoryShipCompanyController {

    private CbsDirectoryShipCompanyService cbsDirectoryShipCompanyService;

    @Autowired
    public void setCbsDirectoryShipCompanyService(CbsDirectoryShipCompanyService cbsDirectoryShipCompanyService) {
        this.cbsDirectoryShipCompanyService = cbsDirectoryShipCompanyService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:directory:shipcompany:list")
    public R list(@RequestBody CbsDirectoryShipCompanyEntity entity) {
        PageUtils page = cbsDirectoryShipCompanyService.queryPage(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:directory:shipcompany:info")
    public R info(@PathVariable("id") Long id) {
        CbsDirectoryShipCompanyEntity cbsDirectoryShipCompany = cbsDirectoryShipCompanyService.getById(id);
        return R.ok().put("cbsDirectoryShipCompany", cbsDirectoryShipCompany);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:directory:shipcompany:save")
    public R save(@RequestBody CbsDirectoryShipCompanyEntity cbsDirectoryShipCompany) {
        cbsDirectoryShipCompanyService.save(cbsDirectoryShipCompany);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:directory:shipcompany:update")
    public R update(@RequestBody CbsDirectoryShipCompanyEntity cbsDirectoryShipCompany) {
        cbsDirectoryShipCompanyService.updateById(cbsDirectoryShipCompany);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:directory:shipcompany:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsDirectoryShipCompanyService.update(CbsDirectoryShipCompanyEntity.builder().available(0).build(), new QueryWrapper<CbsDirectoryShipCompanyEntity>().in("id", ids));
        return R.ok();
    }

}
