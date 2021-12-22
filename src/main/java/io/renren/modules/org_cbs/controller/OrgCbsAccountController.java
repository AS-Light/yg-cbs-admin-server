package io.renren.modules.org_cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.org_cbs.entity.OrgCbsAccountEntity;
import io.renren.modules.org_cbs.service.OrgCbsAccountService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 结算账户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
@RestController
@RequestMapping("org/cbs/cbsaccount")
public class OrgCbsAccountController extends AbstractController {

    private OrgCbsAccountService orgCbsAccountService;

    @Autowired
    public void setOrgCbsAccountService(OrgCbsAccountService orgCbsAccountService) {
        this.orgCbsAccountService = orgCbsAccountService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("org:cbs:account:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orgCbsAccountService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("org:cbs:account:info")
    public R info(@PathVariable("id") Long id){
		OrgCbsAccountEntity orgCbsAccount = orgCbsAccountService.getById(id);

        return R.ok().put("orgCbsAccount", orgCbsAccount);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("org:cbs:account:save")
    public R save(@RequestBody OrgCbsAccountEntity orgCbsAccount){
		orgCbsAccountService.save(orgCbsAccount);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("org:cbs:account:update")
    public R update(@RequestBody OrgCbsAccountEntity orgCbsAccount){
		orgCbsAccountService.updateById(orgCbsAccount);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("org:cbs:account:delete")
    public R delete(@RequestBody Long[] ids){
		orgCbsAccountService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
