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

import io.renren.modules.ctb.entity.CtbGoodsPartNoEntity;
import io.renren.modules.ctb.service.CtbGoodsPartNoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
@RestController
@RequestMapping("ctb/goodspartno")
public class CtbGoodsPartNoController {
    @Autowired
    private CtbGoodsPartNoService ctbGoodsPartNoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:goodspartno:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ctbGoodsPartNoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:goodspartno:info")
    public R info(@PathVariable("id") Long id){
		CtbGoodsPartNoEntity ctbGoodsPartNo = ctbGoodsPartNoService.getById(id);

        return R.ok().put("ctbGoodsPartNo", ctbGoodsPartNo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:goodspartno:save")
    public R save(@RequestBody CtbGoodsPartNoEntity ctbGoodsPartNo){
		ctbGoodsPartNoService.save(ctbGoodsPartNo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:goodspartno:update")
    public R update(@RequestBody CtbGoodsPartNoEntity ctbGoodsPartNo){
		ctbGoodsPartNoService.updateById(ctbGoodsPartNo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:goodspartno:delete")
    public R delete(@RequestBody Long[] ids){
		ctbGoodsPartNoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
