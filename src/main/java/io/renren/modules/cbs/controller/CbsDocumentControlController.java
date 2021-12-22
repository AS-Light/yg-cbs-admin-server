package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsDocumentControlEntity;
import io.renren.modules.cbs.service.CbsDocumentControlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-27 10:02:18
 */
@RestController
@RequestMapping("cbs/documentcontrol")
public class CbsDocumentControlController {
    @Autowired
    private CbsDocumentControlService cbsDocumentControlService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:documentcontrol:list")
    public R list(@RequestBody CbsDocumentControlEntity entity) {
        PageUtils page = cbsDocumentControlService.queryPage(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:documentcontrol:info")
    public R info(@PathVariable("id") Long id) {
        CbsDocumentControlEntity cbsDocumentControl = cbsDocumentControlService.getById(id);

        return R.ok().put("cbsDocumentControl", cbsDocumentControl);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:documentcontrol:save")
    public R save(@RequestBody CbsDocumentControlEntity cbsDocumentControl) {
        cbsDocumentControlService.save(cbsDocumentControl);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:documentcontrol:update")
    public R update(@RequestBody CbsDocumentControlEntity cbsDocumentControl) {
        cbsDocumentControlService.updateById(cbsDocumentControl);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:documentcontrol:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsDocumentControlService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
