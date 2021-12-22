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

import io.renren.modules.cst.entity.CstNptsDcrExgEntity;
import io.renren.modules.cst.service.CstNptsDcrExgService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 手册报核成品表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@RestController
@RequestMapping("cst/nptsdcrexg")
public class CstNptsDcrExgController {
    @Autowired
    private CstNptsDcrExgService cstNptsDcrExgService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:nptsdcrexg:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstNptsDcrExgService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:nptsdcrexg:info")
    public R info(@PathVariable("id") Long id){
		CstNptsDcrExgEntity cstNptsDcrExg = cstNptsDcrExgService.getById(id);

        return R.ok().put("cstNptsDcrExg", cstNptsDcrExg);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:nptsdcrexg:save")
    public R save(@RequestBody CstNptsDcrExgEntity cstNptsDcrExg){
		cstNptsDcrExgService.save(cstNptsDcrExg);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:nptsdcrexg:update")
    public R update(@RequestBody CstNptsDcrExgEntity cstNptsDcrExg){
		cstNptsDcrExgService.updateById(cstNptsDcrExg);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:nptsdcrexg:delete")
    public R delete(@RequestBody Long[] ids){
		cstNptsDcrExgService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
