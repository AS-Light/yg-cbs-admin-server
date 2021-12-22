package io.renren.modules.cst.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.cst.entity.CstInvtHeaderEntity;
import io.renren.modules.cst.service.CstInvtHeaderService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 保税核注清单表头
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@RestController
@RequestMapping("cst/invtheader")
public class CstInvtHeaderController {
    @Autowired
    private CstInvtHeaderService cstInvtHeaderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:invtheader:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstInvtHeaderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:invtheader:info")
    public R info(@PathVariable("id") Long id){
		CstInvtHeaderEntity cstInvtHeader = cstInvtHeaderService.getById(id);

        return R.ok().put("cstInvtHeader", cstInvtHeader);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:invtheader:save")
    public R save(@RequestBody CstInvtHeaderEntity cstInvtHeader){
		cstInvtHeaderService.save(cstInvtHeader);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:invtheader:update")
    public R update(@RequestBody CstInvtHeaderEntity cstInvtHeader){
		cstInvtHeaderService.updateById(cstInvtHeader);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:invtheader:delete")
    public R delete(@RequestBody Long[] ids){
		cstInvtHeaderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
