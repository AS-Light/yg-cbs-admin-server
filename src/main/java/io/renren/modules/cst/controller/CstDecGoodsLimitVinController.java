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

import io.renren.modules.cst.entity.CstDecGoodsLimitVinEntity;
import io.renren.modules.cst.service.CstDecGoodsLimitVinService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 进出口报关单许可证VIN信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@RestController
@RequestMapping("cst/dec/goodslimitvin")
public class CstDecGoodsLimitVinController {
    @Autowired
    private CstDecGoodsLimitVinService cstDecGoodsLimitVinService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:decgoodslimitvin:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstDecGoodsLimitVinService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:decgoodslimitvin:info")
    public R info(@PathVariable("id") Long id){
		CstDecGoodsLimitVinEntity cstDecGoodsLimitVin = cstDecGoodsLimitVinService.getById(id);

        return R.ok().put("cstDecGoodsLimitVin", cstDecGoodsLimitVin);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:decgoodslimitvin:save")
    public R save(@RequestBody CstDecGoodsLimitVinEntity cstDecGoodsLimitVin){
		cstDecGoodsLimitVinService.save(cstDecGoodsLimitVin);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:decgoodslimitvin:update")
    public R update(@RequestBody CstDecGoodsLimitVinEntity cstDecGoodsLimitVin){
		cstDecGoodsLimitVinService.updateById(cstDecGoodsLimitVin);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:decgoodslimitvin:delete")
    public R delete(@RequestBody Long[] ids){
		cstDecGoodsLimitVinService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
