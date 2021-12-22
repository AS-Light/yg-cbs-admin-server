package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsContractMemberEntity;
import io.renren.modules.cbs.service.CbsContractMemberService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 合同过程中对应角色（成员）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-25 11:14:59
 */
@RestController
@RequestMapping("cbs/contractmember")
public class CbsContractMemberController {

    private CbsContractMemberService cbsContractMemberService;

    @Autowired
    public void setCbsContractMemberService(CbsContractMemberService cbsContractMemberService) {
        this.cbsContractMemberService = cbsContractMemberService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:contractmember:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cbsContractMemberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:contractmember:info")
    public R info(@PathVariable("id") Long id){
		CbsContractMemberEntity cbsContractMember = cbsContractMemberService.detail(id);
        return R.ok().put("cbsContractMember", cbsContractMember);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:contractmember:save")
    public R save(@RequestBody CbsContractMemberEntity cbsContractMember){
		cbsContractMemberService.save(cbsContractMember);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:contractmember:update")
    public R update(@RequestBody CbsContractMemberEntity cbsContractMember){
		cbsContractMemberService.updateById(cbsContractMember);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:contractmember:delete")
    public R delete(@RequestBody Long[] ids){
		cbsContractMemberService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
