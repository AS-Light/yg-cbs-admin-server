package io.renren.modules.thr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.thr.entity.ThrGoodsAttrEntity;
import io.renren.modules.thr.service.ThrGoodsAttrService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 海关货物属性编码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@RestController
@RequestMapping("thr/goodsattr")
public class ThrGoodsAttrController {
    private ThrGoodsAttrService thrGoodsAttrService;

    @Autowired
    public void setThrGoodsAttrService(ThrGoodsAttrService thrGoodsAttrService) {
        this.thrGoodsAttrService = thrGoodsAttrService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("thr:goodsattr:list")
    public R list(@RequestBody ThrGoodsAttrEntity entity) {
        PageUtils page = thrGoodsAttrService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thr:goodsattr:info")
    public R info(@PathVariable("id") Integer id) {
        ThrGoodsAttrEntity thrGoodsAttr = thrGoodsAttrService.getById(id);
        return R.ok().put("entity", thrGoodsAttr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("thr:goodsattr:save")
    public R save(@RequestBody ThrGoodsAttrEntity thrGoodsAttr) {
        thrGoodsAttrService.save(thrGoodsAttr);
        return R.ok();
    }

    /**
     * 通过code获取，必须包含 goodsAttrCode
     */
    @RequestMapping("/infoByCode")
    @RequiresPermissions("thr:goodsattr:info")
    public R infoByCode(@RequestBody ThrGoodsAttrEntity entity) {
        if (entity.getGoodsAttrCode() != null) {
            return R.ok().put("entity", thrGoodsAttrService.getOneByCode(entity));
        } else {
            return R.error("参数不全");
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("thr:goodsattr:update")
    public R update(@RequestBody ThrGoodsAttrEntity thrGoodsAttr) {
        thrGoodsAttrService.updateById(thrGoodsAttr);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("thr:goodsattr:delete")
    public R delete(@RequestBody Integer[] ids) {
        thrGoodsAttrService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
