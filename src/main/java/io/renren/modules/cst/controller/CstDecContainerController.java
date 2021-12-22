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

import io.renren.modules.cst.entity.CstDecContainerEntity;
import io.renren.modules.cst.service.CstDecContainerService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 报关单集装箱
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@RestController
@RequestMapping("cst/dec/container")
public class CstDecContainerController {
    @Autowired
    private CstDecContainerService cstDecContainerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:deccontainer:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstDecContainerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:deccontainer:info")
    public R info(@PathVariable("id") Long id){
		CstDecContainerEntity cstDecContainer = cstDecContainerService.getById(id);

        return R.ok().put("cstDecContainer", cstDecContainer);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:deccontainer:save")
    public R save(@RequestBody CstDecContainerEntity cstDecContainer){
		cstDecContainerService.save(cstDecContainer);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:deccontainer:update")
    public R update(@RequestBody CstDecContainerEntity cstDecContainer){
		cstDecContainerService.updateById(cstDecContainer);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:deccontainer:delete")
    public R delete(@RequestBody Long[] ids){
		cstDecContainerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
