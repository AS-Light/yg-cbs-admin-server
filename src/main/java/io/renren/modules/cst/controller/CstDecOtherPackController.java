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

import io.renren.modules.cst.entity.CstDecOtherPackEntity;
import io.renren.modules.cst.service.CstDecOtherPackService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 进出口报关单 其他包装信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@RestController
@RequestMapping("cst/dec/otherpack")
public class CstDecOtherPackController {
    @Autowired
    private CstDecOtherPackService cstDecOtherPackService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:decotherpack:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstDecOtherPackService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:decotherpack:info")
    public R info(@PathVariable("id") Long id){
		CstDecOtherPackEntity cstDecOtherPack = cstDecOtherPackService.getById(id);

        return R.ok().put("cstDecOtherPack", cstDecOtherPack);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:decotherpack:save")
    public R save(@RequestBody CstDecOtherPackEntity cstDecOtherPack){
		cstDecOtherPackService.save(cstDecOtherPack);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:decotherpack:update")
    public R update(@RequestBody CstDecOtherPackEntity cstDecOtherPack){
		cstDecOtherPackService.updateById(cstDecOtherPack);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:decotherpack:delete")
    public R delete(@RequestBody Long[] ids){
		cstDecOtherPackService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
