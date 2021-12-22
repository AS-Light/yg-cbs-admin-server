package io.renren.modules.cst.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cst.entity.CstNptsEmlConsumeEntity;
import io.renren.modules.cst.service.CstNptsEmlConsumeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 加工贸易手册单耗表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-14 10:42:32
 */
@RestController
@RequestMapping("cst/npt/semlconsume")
public class CstNptsEmlConsumeController {
    @Autowired
    private CstNptsEmlConsumeService cstNptsEmlConsumeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:npt:semlconsume:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstNptsEmlConsumeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:npt:semlconsume:info")
    public R info(@PathVariable("id") Long id){
		CstNptsEmlConsumeEntity cstNptsEmlConsume = cstNptsEmlConsumeService.getById(id);

        return R.ok().put("cstNptsEmlConsume", cstNptsEmlConsume);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:npt:semlconsume:save")
    public R save(@RequestBody CstNptsEmlConsumeEntity cstNptsEmlConsume){
		cstNptsEmlConsumeService.save(cstNptsEmlConsume);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:npt:semlconsume:update")
    public R update(@RequestBody CstNptsEmlConsumeEntity cstNptsEmlConsume){
		cstNptsEmlConsumeService.updateById(cstNptsEmlConsume);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:npt:semlconsume:delete")
    public R delete(@RequestBody Long[] ids){
		cstNptsEmlConsumeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
