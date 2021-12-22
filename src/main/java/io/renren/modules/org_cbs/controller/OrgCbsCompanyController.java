package io.renren.modules.org_cbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.org_cbs.entity.OrgCbsCompanyEntity;
import io.renren.modules.org_cbs.entity.OrgCbsMenuEntity;
import io.renren.modules.org_cbs.service.OrgCbsCompanyService;
import io.renren.modules.org_cbs.service.OrgCbsMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户公司表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-17 16:41:40
 */
@RestController
@RequestMapping("org/cbs/company")
public class OrgCbsCompanyController extends AbstractController {
    @Autowired
    private OrgCbsCompanyService orgCbsCompanyService;
    @Autowired
    private OrgCbsMenuService orgCbsMenuService;


    /**
     * 列表
     */
    @PostMapping("/list")
    @RequiresPermissions("org:cbs:company:list")
    public R list(@RequestBody OrgCbsCompanyEntity entity) {
        entity.setId(getTenantId());
        PageUtils page = orgCbsCompanyService.queryIndex(entity);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("org:cbs:company:info")
    public R info(@PathVariable("id") Long id) {
        OrgCbsCompanyEntity orgCbsCompany = orgCbsCompanyService.getById(id);
        List<OrgCbsMenuEntity> list = orgCbsMenuService.list(new QueryWrapper<OrgCbsMenuEntity>().eq("tenant_id", id));
        List<Long> longList = list.stream().map(item -> {
            return item.getFkSysCbsMenuId();
        }).collect(Collectors.toList());
        orgCbsCompany.setMenuIdList(longList);
        return R.ok().put("company", orgCbsCompany);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("org:cbs:company:save")
    public R save(@RequestBody OrgCbsCompanyEntity orgCbsCompany) {
        ValidatorUtils.validateEntity(orgCbsCompany);
        orgCbsCompany.setCreateUserId(getUserId());
        orgCbsCompanyService.saveCompany(orgCbsCompany);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("org:cbs:company:update")
    public R update(@RequestBody OrgCbsCompanyEntity orgCbsCompany) {
        ValidatorUtils.validateEntity(orgCbsCompany);
        orgCbsCompany.setCreateUserId(getUserId());
        orgCbsCompanyService.updateCompanyById(orgCbsCompany);
        return R.ok();
    }

    /**
     * 只修改公司信息，给与公司修改信息权限
     */
    @RequestMapping("/updateInfoOnly")
    @RequiresPermissions("org:cbs:company:update")
    public R updateInfoOnly(@RequestBody OrgCbsCompanyEntity orgCbsCompany) {
        ValidatorUtils.validateEntity(orgCbsCompany);
        orgCbsCompany.setCreateUserId(getUserId());
        orgCbsCompanyService.updateCompanyInfoOnlyById(orgCbsCompany);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("org:cbs:company:delete")
    public R delete(@RequestBody Long[] ids) {
        orgCbsCompanyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
