package io.renren.modules.ctb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbPriceListEntity;
import io.renren.modules.ctb.entity.CtbPriceListItemEntity;
import io.renren.modules.ctb.service.CtbPriceListItemService;
import io.renren.modules.ctb.service.CtbPriceListService;
import io.renren.modules.ctb.service.CtbPriceListTempleteService;
import io.renren.modules.org_ctb.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 报关行报价单表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/pricelistitem")
public class CtbPriceListItemController extends AbstractController {
    private CtbPriceListService ctbPriceListService;
    private CtbPriceListItemService ctbPriceListItemService;
    private CtbPriceListTempleteService ctbPriceListTempleteService;

    public CtbPriceListItemController(CtbPriceListService ctbPriceListService, CtbPriceListItemService ctbPriceListItemService, CtbPriceListTempleteService ctbPriceListTempleteService) {
        this.ctbPriceListService = ctbPriceListService;
        this.ctbPriceListItemService = ctbPriceListItemService;
        this.ctbPriceListTempleteService = ctbPriceListTempleteService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:pricelistitem:list")
    public R list(@RequestBody CtbPriceListItemEntity entity) {
        PageUtils page = ctbPriceListItemService.queryIndex(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:pricelistitem:info")
    public R info(@PathVariable("id") Long id) {
        CtbPriceListItemEntity ctbPriceListItem = ctbPriceListItemService.getById(id);

        return R.ok().put("ctbPriceListItem", ctbPriceListItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:pricelistitem:save")
    @Transactional
    public R save(@RequestBody CtbPriceListItemEntity ctbPriceListItem) {
        if (ctbPriceListItem.getContractId() == null) {
            throw new RuntimeException("没找到相关合同");
        }
        CtbPriceListEntity ctbPriceListEntity = ctbPriceListService.getOne(new QueryWrapper<CtbPriceListEntity>().eq("fk_contract_id", ctbPriceListItem.getContractId()));
        if (ctbPriceListEntity == null) {
            ctbPriceListEntity = CtbPriceListEntity.builder().fkContractId(ctbPriceListItem.getContractId()).build();
            ctbPriceListService.save(ctbPriceListEntity);
        }
        ctbPriceListItem.setFkPriceListId(ctbPriceListEntity.getId());
        ctbPriceListItemService.save(ctbPriceListItem);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:pricelistitem:update")
    public R update(@RequestBody CtbPriceListItemEntity ctbPriceListItem) {
        ctbPriceListItemService.updateById(ctbPriceListItem);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:pricelistitem:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbPriceListItemService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 预览
     */
    @RequestMapping("/preview/{id}")
    @RequiresPermissions("ctb:pricelistitem:preview")
    public R preview(@PathVariable("id") Long id) {
        String pdf = ctbPriceListItemService.generatePDF(id);
        return R.ok(pdf);
    }

    /**
     * 预览保存
     */
    @RequestMapping("/previewSave/{id}")
    @RequiresPermissions("ctb:pricelistitem:previewSave")
    public R previewSave(@PathVariable("id") Long id) {
        String pdf = ctbPriceListItemService.generatePDF(id);
        CtbPriceListEntity entity = ctbPriceListService.getOne(new QueryWrapper<CtbPriceListEntity>().eq("fk_contract_id", id));
        entity.setPdf(pdf);
        ctbPriceListService.saveOrUpdate(entity);
        return R.ok();
    }


}
