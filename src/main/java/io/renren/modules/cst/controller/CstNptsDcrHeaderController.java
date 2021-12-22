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

import io.renren.modules.cst.entity.CstNptsDcrHeaderEntity;
import io.renren.modules.cst.service.CstNptsDcrHeaderService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 手册报核表头
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@RestController
@RequestMapping("cst/nptsdcrheader")
public class CstNptsDcrHeaderController {
    @Autowired
    private CstNptsDcrHeaderService cstNptsDcrHeaderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:nptsdcrheader:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstNptsDcrHeaderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:nptsdcrheader:info")
    public R info(@PathVariable("id") Long id){
		CstNptsDcrHeaderEntity cstNptsDcrHeader = cstNptsDcrHeaderService.getById(id);

        return R.ok().put("cstNptsDcrHeader", cstNptsDcrHeader);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:nptsdcrheader:save")
    public R save(@RequestBody CstNptsDcrHeaderEntity cstNptsDcrHeader){
		cstNptsDcrHeaderService.save(cstNptsDcrHeader);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:nptsdcrheader:update")
    public R update(@RequestBody CstNptsDcrHeaderEntity cstNptsDcrHeader){
		cstNptsDcrHeaderService.updateById(cstNptsDcrHeader);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:nptsdcrheader:delete")
    public R delete(@RequestBody Long[] ids){
		cstNptsDcrHeaderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
