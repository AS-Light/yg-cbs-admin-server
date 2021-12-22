package io.renren.modules.cbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsDirectoryGoodsEntity;
import io.renren.modules.cbs.service.CbsDirectoryGoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 原材料名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 10:29:32
 */
@RestController
@RequestMapping("cbs/directory/goods")
public class CbsDirectoryGoodsController {

    private CbsDirectoryGoodsService cbsDirectoryGoodsService;

    @Autowired
    public void setCbsDirectoryGoodsService(CbsDirectoryGoodsService cbsDirectoryGoodsService) {
        this.cbsDirectoryGoodsService = cbsDirectoryGoodsService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:directory:goods:list")
    public R list(@RequestBody CbsDirectoryGoodsEntity entity) {
        PageUtils page = cbsDirectoryGoodsService.queryPage(entity);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:directory:goods:info")
    public R info(@PathVariable("id") Long id) {
        CbsDirectoryGoodsEntity cbsDirectoryGoods = cbsDirectoryGoodsService.detail(id);
        return R.ok().put("detail", cbsDirectoryGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:directory:goods:save")
    public R save(@RequestBody CbsDirectoryGoodsEntity cbsDirectoryGoods) {
        cbsDirectoryGoodsService.save(cbsDirectoryGoods);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:directory:goods:update")
    public R update(@RequestBody CbsDirectoryGoodsEntity cbsDirectoryGoods) {
        cbsDirectoryGoodsService.updateById(cbsDirectoryGoods);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:directory:goods:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsDirectoryGoodsService.update(CbsDirectoryGoodsEntity.builder().available(0).build(), new QueryWrapper<CbsDirectoryGoodsEntity>().in("id", ids));
        return R.ok();
    }

}
