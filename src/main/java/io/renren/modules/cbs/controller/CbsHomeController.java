package io.renren.modules.cbs.controller;

import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsHomeEntity;
import io.renren.modules.cbs.service.CbsHomeService;
import io.renren.modules.org_cbs.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 首页
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-09 11:16:37
 */
@Api(tags = "首页")
@RestController
@RequestMapping("cbs/home")
public class CbsHomeController extends AbstractController {

    @Autowired
    private CbsHomeService cbsHomeService;

    @ApiOperation("详情")
    @GetMapping("/total")
    @RequiresPermissions("cbs:home:total")
    public R total() {
        CbsHomeEntity entity = cbsHomeService.total(getTenantId());
        return R.ok().put("entity", entity);
    }

    @ApiOperation("预计收支")
    @PostMapping("/expected")
    @RequiresPermissions("cbs:home:expected")
    public R expected(@RequestBody String[] months) {
        CbsHomeEntity entity = cbsHomeService.expected(getTenantId(), months);
        return R.ok().put("entity", entity);
    }

    @ApiOperation("实际收支")
    @PostMapping("/actual")
    @RequiresPermissions("cbs:home:actual")
    public R actual(@RequestBody String[] months) {
        CbsHomeEntity entity = cbsHomeService.actual(getTenantId(), months);
        return R.ok().put("entity", entity);
    }

    @ApiOperation("待办事项")
    @GetMapping("/upcoming")
    @RequiresPermissions("cbs:home:upcoming")
    public R upcoming() {
        CbsHomeEntity entity = cbsHomeService.upcoming(getTenantId());
        return R.ok().put("entity", entity);
    }

}
