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

import io.renren.modules.cst.entity.CstDecHeaderEntity;
import io.renren.modules.cst.service.CstDecHeaderService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 报关单表头
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@RestController
@RequestMapping("cst/dec/header")
public class CstDecHeaderController {
    @Autowired
    private CstDecHeaderService cstDecHeaderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:decheader:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstDecHeaderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:decheader:info")
    public R info(@PathVariable("id") Long id){
		CstDecHeaderEntity cstDecHeader = cstDecHeaderService.getById(id);

        return R.ok().put("cstDecHeader", cstDecHeader);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:decheader:save")
    public R save(@RequestBody CstDecHeaderEntity cstDecHeader){
		cstDecHeaderService.save(cstDecHeader);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:decheader:update")
    public R update(@RequestBody CstDecHeaderEntity cstDecHeader){
		cstDecHeaderService.updateById(cstDecHeader);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:decheader:delete")
    public R delete(@RequestBody Long[] ids){
		cstDecHeaderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
