package io.renren.modules.ctb.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbDocumentControlEntity;
import io.renren.modules.ctb.service.CtbDocumentControlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-16 15:09:03
 */
@RestController
@RequestMapping("ctb/documentcontrol")
public class CtbDocumentControlController {
    @Autowired
    private CtbDocumentControlService ctbDocumentControlService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:documentcontrol:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ctbDocumentControlService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:documentcontrol:info")
    public R info(@PathVariable("id") Long id){
		CtbDocumentControlEntity ctbDocumentControl = ctbDocumentControlService.getById(id);

        return R.ok().put("ctbDocumentControl", ctbDocumentControl);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:documentcontrol:save")
    public R save(@RequestBody CtbDocumentControlEntity ctbDocumentControl){
		ctbDocumentControlService.save(ctbDocumentControl);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:documentcontrol:update")
    public R update(@RequestBody CtbDocumentControlEntity ctbDocumentControl){
		ctbDocumentControlService.updateById(ctbDocumentControl);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:documentcontrol:delete")
    public R delete(@RequestBody Long[] ids){
		ctbDocumentControlService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
