package io.renren.modules.ctb.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbDirectoryShipCompanyEntity;
import io.renren.modules.ctb.service.CtbDirectoryShipCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 船务公司名录表（报关行副本）
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/directory/shipcompany")
public class CtbDirectoryShipCompanyController {
    @Autowired
    private CtbDirectoryShipCompanyService ctbDirectoryShipCompanyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:directory:shipcompany:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ctbDirectoryShipCompanyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:directory:shipcompany:info")
    public R info(@PathVariable("id") Long id){
		CtbDirectoryShipCompanyEntity ctbDirectoryShipCompany = ctbDirectoryShipCompanyService.getById(id);

        return R.ok().put("ctbDirectoryShipCompany", ctbDirectoryShipCompany);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:directory:shipcompany:save")
    public R save(@RequestBody CtbDirectoryShipCompanyEntity ctbDirectoryShipCompany){
		ctbDirectoryShipCompanyService.save(ctbDirectoryShipCompany);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:directory:shipcompany:update")
    public R update(@RequestBody CtbDirectoryShipCompanyEntity ctbDirectoryShipCompany){
		ctbDirectoryShipCompanyService.updateById(ctbDirectoryShipCompany);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:directory:shipcompany:delete")
    public R delete(@RequestBody Long[] ids){
		ctbDirectoryShipCompanyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
