package io.renren.modules.org_ctb.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.org_ctb.controller.AbstractController;
import io.renren.modules.org_ctb.entity.OrgCtbAccountEntity;
import io.renren.modules.org_ctb.service.OrgCtbAccountService;
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
@RequestMapping("org/ctb/ctbaccount")
public class OrgCtbAccountController extends AbstractController {

    private OrgCtbAccountService orgCtbAccountService;

    @Autowired
    public void setOrgCtbAccountService(OrgCtbAccountService orgCtbAccountService) {
        this.orgCtbAccountService = orgCtbAccountService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("org:ctb:account:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orgCtbAccountService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("org:ctb:account:info")
    public R info(@PathVariable("id") Long id){
		OrgCtbAccountEntity orgCtbAccount = orgCtbAccountService.getById(id);

        return R.ok().put("orgCtbAccount", orgCtbAccount);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("org:ctb:account:save")
    public R save(@RequestBody OrgCtbAccountEntity orgCtbAccount){
		orgCtbAccountService.save(orgCtbAccount);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("org:ctb:account:update")
    public R update(@RequestBody OrgCtbAccountEntity orgCtbAccount){
		orgCtbAccountService.updateById(orgCtbAccount);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("org:ctb:account:delete")
    public R delete(@RequestBody Long[] ids){
		orgCtbAccountService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
