package io.renren.modules.org_ctb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.org_ctb.entity.OrgCtbCompanyEntity;
import io.renren.modules.org_ctb.entity.OrgCtbMenuEntity;
import io.renren.modules.org_ctb.service.OrgCtbCompanyService;
import io.renren.modules.org_ctb.service.OrgCtbMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("org/ctb/company")
public class OrgCtbCompanyController extends AbstractController {
    private OrgCtbCompanyService orgCtbCompanyService;
    private OrgCtbMenuService orgCtbMenuService;

    public OrgCtbCompanyController(
            OrgCtbCompanyService orgCtbCompanyService,
            OrgCtbMenuService orgCtbMenuService
    ) {
        this.orgCtbCompanyService = orgCtbCompanyService;
        this.orgCtbMenuService = orgCtbMenuService;
    }


    /**
     * 列表
     */
    @PostMapping("/list")
    @RequiresPermissions("org:ctb:company:list")
    public R list(@RequestBody OrgCtbCompanyEntity entity) {
        entity.setId(getCtbTenantId());
        PageUtils page = orgCtbCompanyService.queryIndex(entity);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("org:ctb:company:info")
    public R info(@PathVariable("id") Long id) {
        OrgCtbCompanyEntity orgCtbCompany = orgCtbCompanyService.getById(id);
        List<OrgCtbMenuEntity> list = orgCtbMenuService.list(new QueryWrapper<OrgCtbMenuEntity>().eq("ctb_tenant_id", id));
        List<Long> longList = list.stream().map(item -> {
            return item.getFkSysCtbMenuId();
        }).collect(Collectors.toList());
        orgCtbCompany.setMenuIdList(longList);
        return R.ok().put("company", orgCtbCompany);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("org:ctb:company:save")
    public R save(@RequestBody OrgCtbCompanyEntity orgCtbCompany) {
        ValidatorUtils.validateEntity(orgCtbCompany);
        orgCtbCompany.setCreateUserId(getUserId());
        orgCtbCompanyService.saveCompany(orgCtbCompany);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("org:ctb:company:update")
    public R update(@RequestBody OrgCtbCompanyEntity orgCtbCompany) {
        ValidatorUtils.validateEntity(orgCtbCompany);
        orgCtbCompany.setCreateUserId(getUserId());
        orgCtbCompanyService.updateCompanyById(orgCtbCompany);
        return R.ok();
    }

    /**
     * 只修改公司信息，给与公司修改信息权限
     */
    @RequestMapping("/updateInfoOnly")
    @RequiresPermissions("org:ctb:company:update")
    public R updateInfoOnly(@RequestBody OrgCtbCompanyEntity orgCtbCompany) {
        ValidatorUtils.validateEntity(orgCtbCompany);
        orgCtbCompany.setCreateUserId(getUserId());
        orgCtbCompanyService.updateCompanyInfoOnlyById(orgCtbCompany);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("org:ctb:company:delete")
    public R delete(@RequestBody Long[] ids) {
        orgCtbCompanyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
