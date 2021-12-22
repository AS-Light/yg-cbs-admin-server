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

import io.renren.modules.cst.entity.CstNptsDcrImgEntity;
import io.renren.modules.cst.service.CstNptsDcrImgService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 手册报核料件表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@RestController
@RequestMapping("cst/nptsdcrimg")
public class CstNptsDcrImgController {
    @Autowired
    private CstNptsDcrImgService cstNptsDcrImgService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:nptsdcrimg:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstNptsDcrImgService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:nptsdcrimg:info")
    public R info(@PathVariable("id") Long id){
		CstNptsDcrImgEntity cstNptsDcrImg = cstNptsDcrImgService.getById(id);

        return R.ok().put("cstNptsDcrImg", cstNptsDcrImg);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:nptsdcrimg:save")
    public R save(@RequestBody CstNptsDcrImgEntity cstNptsDcrImg){
		cstNptsDcrImgService.save(cstNptsDcrImg);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:nptsdcrimg:update")
    public R update(@RequestBody CstNptsDcrImgEntity cstNptsDcrImg){
		cstNptsDcrImgService.updateById(cstNptsDcrImg);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:nptsdcrimg:delete")
    public R delete(@RequestBody Long[] ids){
		cstNptsDcrImgService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
