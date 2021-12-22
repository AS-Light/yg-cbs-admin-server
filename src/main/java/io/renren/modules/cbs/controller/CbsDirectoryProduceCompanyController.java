package io.renren.modules.cbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsDirectoryProduceCompanyEntity;
import io.renren.modules.cbs.service.CbsDirectoryProduceCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 生产公司名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@RestController
@RequestMapping("cbs/directory/producecompany")
public class CbsDirectoryProduceCompanyController {

    private CbsDirectoryProduceCompanyService cbsDirectoryProduceCompanyService;

    @Autowired
    public void setCbsDirectoryProduceCompanyService(CbsDirectoryProduceCompanyService cbsDirectoryProduceCompanyService) {
        this.cbsDirectoryProduceCompanyService = cbsDirectoryProduceCompanyService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:directory:producecompany:list")
    public R list(@RequestBody CbsDirectoryProduceCompanyEntity entity) {
        PageUtils page = cbsDirectoryProduceCompanyService.queryPage(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:directory:producecompany:info")
    public R info(@PathVariable("id") Long id) {
        CbsDirectoryProduceCompanyEntity cbsDirectoryProduceCompany = cbsDirectoryProduceCompanyService.getById(id);
        return R.ok().put("cbsDirectoryProduceCompany", cbsDirectoryProduceCompany);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:directory:producecompany:save")
    public R save(@RequestBody CbsDirectoryProduceCompanyEntity cbsDirectoryProduceCompany) {
        cbsDirectoryProduceCompanyService.save(cbsDirectoryProduceCompany);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:directory:producecompany:update")
    public R update(@RequestBody CbsDirectoryProduceCompanyEntity cbsDirectoryProduceCompany) {
        cbsDirectoryProduceCompanyService.updateById(cbsDirectoryProduceCompany);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:directory:producecompany:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsDirectoryProduceCompanyService.update(CbsDirectoryProduceCompanyEntity.builder().available(0).build(), new QueryWrapper<CbsDirectoryProduceCompanyEntity>().in("id", ids));
        return R.ok();
    }

}
