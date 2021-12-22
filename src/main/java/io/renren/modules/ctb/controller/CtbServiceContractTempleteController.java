package io.renren.modules.ctb.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ctb.entity.CtbServiceContractTempleteEntity;
import io.renren.modules.ctb.service.CtbServiceContractTempleteService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 报关行货运代理合同模板，原则上报关行只有一个模板，有一个ctb_tenant_id=-1的模板作为公共模板
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/servicecontracttemplete")
public class CtbServiceContractTempleteController {
    @Autowired
    private CtbServiceContractTempleteService ctbServiceContractTempleteService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:servicecontracttemplete:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ctbServiceContractTempleteService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:servicecontracttemplete:info")
    public R info(@PathVariable("id") Long id){
		CtbServiceContractTempleteEntity ctbServiceContractTemplete = ctbServiceContractTempleteService.getById(id);

        return R.ok().put("ctbServiceContractTemplete", ctbServiceContractTemplete);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:servicecontracttemplete:save")
    public R save(@RequestBody CtbServiceContractTempleteEntity ctbServiceContractTemplete){
		ctbServiceContractTempleteService.save(ctbServiceContractTemplete);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:servicecontracttemplete:update")
    public R update(@RequestBody CtbServiceContractTempleteEntity ctbServiceContractTemplete){
		ctbServiceContractTempleteService.updateById(ctbServiceContractTemplete);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:servicecontracttemplete:delete")
    public R delete(@RequestBody Long[] ids){
		ctbServiceContractTempleteService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
