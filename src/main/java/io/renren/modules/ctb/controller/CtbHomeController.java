package io.renren.modules.ctb.controller;

import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbHomeEntity;
import io.renren.modules.ctb.service.CtbHomeService;
import io.renren.modules.org_ctb.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("ctb/home")
public class CtbHomeController extends AbstractController {

    private CtbHomeService ctbHomeService;

    public CtbHomeController(CtbHomeService ctbHomeService) {
        this.ctbHomeService = ctbHomeService;
    }

    @ApiOperation("详情")
    @GetMapping("/total")
    @RequiresPermissions("ctb:home:total")
    public R total() {
        CtbHomeEntity entity = ctbHomeService.total(getCtbTenantId());
        return R.ok().put("entity", entity);
    }

    @ApiOperation("加贸报关统计")
    @PostMapping("/canadaTradeApplyToCustoms")
    @RequiresPermissions("ctb:home:canadaTradeApplyToCustoms")
    public R canadaTradeApplyToCustoms(@RequestBody String[] months) {
        CtbHomeEntity entity = ctbHomeService.canadaTradeApplyToCustoms(getCtbTenantId(), months);
        return R.ok().put("entity", entity);
    }

    @ApiOperation("实际收支")
    @PostMapping("/actual")
    @RequiresPermissions("ctb:home:actual")
    public R actual(@RequestBody String[] months) {
        CtbHomeEntity entity = ctbHomeService.actual(getCtbTenantId(), months);
        return R.ok().put("entity", entity);
    }


}
