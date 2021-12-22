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

import io.renren.modules.ctb.entity.CtbPartnerTypeEntity;
import io.renren.modules.ctb.service.CtbPartnerTypeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * ctb_partner的type表 ，同一个partner可以有多种类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/partnertype")
public class CtbPartnerTypeController {
    @Autowired
    private CtbPartnerTypeService ctbPartnerTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:partnertype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ctbPartnerTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:partnertype:info")
    public R info(@PathVariable("id") Long id){
		CtbPartnerTypeEntity ctbPartnerType = ctbPartnerTypeService.getById(id);

        return R.ok().put("ctbPartnerType", ctbPartnerType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:partnertype:save")
    public R save(@RequestBody CtbPartnerTypeEntity ctbPartnerType){
		ctbPartnerTypeService.save(ctbPartnerType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:partnertype:update")
    public R update(@RequestBody CtbPartnerTypeEntity ctbPartnerType){
		ctbPartnerTypeService.updateById(ctbPartnerType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:partnertype:delete")
    public R delete(@RequestBody Long[] ids){
		ctbPartnerTypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
