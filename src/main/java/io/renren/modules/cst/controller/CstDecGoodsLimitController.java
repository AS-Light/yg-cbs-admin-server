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

import io.renren.modules.cst.entity.CstDecGoodsLimitEntity;
import io.renren.modules.cst.service.CstDecGoodsLimitService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 进出口报关单许可证信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@RestController
@RequestMapping("cst/dec/goodslimit")
public class CstDecGoodsLimitController {
    @Autowired
    private CstDecGoodsLimitService cstDecGoodsLimitService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:decgoodslimit:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstDecGoodsLimitService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:decgoodslimit:info")
    public R info(@PathVariable("id") Long id){
		CstDecGoodsLimitEntity cstDecGoodsLimit = cstDecGoodsLimitService.getById(id);

        return R.ok().put("cstDecGoodsLimit", cstDecGoodsLimit);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:decgoodslimit:save")
    public R save(@RequestBody CstDecGoodsLimitEntity cstDecGoodsLimit){
		cstDecGoodsLimitService.save(cstDecGoodsLimit);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:decgoodslimit:update")
    public R update(@RequestBody CstDecGoodsLimitEntity cstDecGoodsLimit){
		cstDecGoodsLimitService.updateById(cstDecGoodsLimit);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:decgoodslimit:delete")
    public R delete(@RequestBody Long[] ids){
		cstDecGoodsLimitService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
