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

import io.renren.modules.cst.entity.CstInvtListEntity;
import io.renren.modules.cst.service.CstInvtListService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 保税核注清单表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@RestController
@RequestMapping("cst/invtlist")
public class CstInvtListController {
    @Autowired
    private CstInvtListService cstInvtListService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:invtlist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstInvtListService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:invtlist:info")
    public R info(@PathVariable("id") Long id){
		CstInvtListEntity cstInvtList = cstInvtListService.getById(id);

        return R.ok().put("cstInvtList", cstInvtList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:invtlist:save")
    public R save(@RequestBody CstInvtListEntity cstInvtList){
		cstInvtListService.save(cstInvtList);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:invtlist:update")
    public R update(@RequestBody CstInvtListEntity cstInvtList){
		cstInvtListService.updateById(cstInvtList);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:invtlist:delete")
    public R delete(@RequestBody Long[] ids){
		cstInvtListService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
