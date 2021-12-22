package io.renren.modules.ctb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbDirectoryGoodsEntity;
import io.renren.modules.ctb.service.CtbDirectoryGoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 原材料名录表（报关行副本）
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/directory/goods")
public class CtbDirectoryGoodsController {
    private CtbDirectoryGoodsService ctbDirectoryGoodsService;

    public CtbDirectoryGoodsController(CtbDirectoryGoodsService ctbDirectoryGoodsService) {
        this.ctbDirectoryGoodsService = ctbDirectoryGoodsService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:directory:goods:list")
    public R list(@RequestBody CtbDirectoryGoodsEntity entity) {
        PageUtils page = ctbDirectoryGoodsService.queryPage(entity);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:directory:goods:info")
    public R info(@PathVariable("id") Long id) {
        CtbDirectoryGoodsEntity ctbDirectoryGoods = ctbDirectoryGoodsService.detail(id);
        return R.ok().put("detail", ctbDirectoryGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:directory:goods:save")
    public R save(@RequestBody CtbDirectoryGoodsEntity ctbDirectoryGoods) {
        ctbDirectoryGoodsService.save(ctbDirectoryGoods);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:directory:goods:update")
    public R update(@RequestBody CtbDirectoryGoodsEntity ctbDirectoryGoods) {
        ctbDirectoryGoodsService.updateById(ctbDirectoryGoods);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:directory:goods:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbDirectoryGoodsService.update(CtbDirectoryGoodsEntity.builder().available(0).build(), new QueryWrapper<CtbDirectoryGoodsEntity>().in("id", ids));
        return R.ok();
    }

}
