package io.renren.modules.ctb.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbStoreGoodsEntity;
import io.renren.modules.ctb.service.CtbStoreGoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 产品库存表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@RestController
@RequestMapping("ctb/storegoods")
public class CtbStoreGoodsController {

    private CtbStoreGoodsService ctbStoreGoodsService;

    public CtbStoreGoodsController(
            CtbStoreGoodsService ctbStoreGoodsService
    ) {
        this.ctbStoreGoodsService = ctbStoreGoodsService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:storegoods:list")
    public R list(@RequestBody CtbStoreGoodsEntity entity) {
        PageUtils page = ctbStoreGoodsService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:storegoods:info")
    public R info(@PathVariable("id") Long id) {
        CtbStoreGoodsEntity ctbStoreGoods = ctbStoreGoodsService.detail(id);
        return R.ok().put("ctbStoreGoods", ctbStoreGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:storegoods:save")
    public R save(@RequestBody CtbStoreGoodsEntity ctbStoreGoods) {
        ctbStoreGoodsService.save(ctbStoreGoods);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:storegoods:update")
    public R update(@RequestBody CtbStoreGoodsEntity ctbStoreGoods) {
        ctbStoreGoodsService.updateById(ctbStoreGoods);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:storegoods:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbStoreGoodsService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
