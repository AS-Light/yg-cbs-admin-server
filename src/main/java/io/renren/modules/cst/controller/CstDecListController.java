package io.renren.modules.cst.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cst.entity.CstDecListEntity;
import io.renren.modules.cst.service.CstDecListService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 进出口报关单表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@RestController
@RequestMapping("cst/dec/list")
public class CstDecListController {
    @Autowired
    private CstDecListService cstDecListService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:declist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstDecListService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:declist:info")
    public R info(@PathVariable("id") Long id){
		CstDecListEntity cstDecList = cstDecListService.getById(id);

        return R.ok().put("cstDecList", cstDecList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:declist:save")
    public R save(@RequestBody CstDecListEntity cstDecList){
		cstDecListService.save(cstDecList);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:declist:update")
    public R update(@RequestBody CstDecListEntity cstDecList){
		cstDecListService.updateById(cstDecList);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:declist:delete")
    public R delete(@RequestBody Long[] ids){
		cstDecListService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
