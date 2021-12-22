package io.renren.modules.cst.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.common.utils.R;
import io.renren.modules.cst.entity.*;
import io.renren.modules.cst.service.CstDecContainerService;
import io.renren.modules.cst.service.CstDecHeaderService;
import io.renren.modules.cst.service.CstDecListService;
import io.renren.modules.cst.service.CstDecService;
import io.renren.modules.org_cbs.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("cst/dec")
public class CstDecController extends AbstractController {
    private CstDecService decService;
    private CstDecHeaderService headerService;
    private CstDecListService goodsService;
    private CstDecContainerService containerService;

    @Autowired
    public void setDecService(CstDecService decService) {
        this.decService = decService;
    }

    @Autowired
    public void setContainerService(CstDecContainerService containerService) {
        this.containerService = containerService;
    }

    @Autowired
    public void setGoodsService(CstDecListService goodsService) {
        this.goodsService = goodsService;
    }

    @Autowired
    public void setHeaderService(CstDecHeaderService headerService) {
        this.headerService = headerService;
    }

    /**
     * 获取报关单详情
     */
    @RequestMapping("/detail/{id}")
    @RequiresPermissions("cst:dec:detail")
    public R detail(@PathVariable("id") Long id) {
        CstDecEntity entity = decService.detail(id);
        return R.ok().put("entity", entity);
    }

    /**
     * 通过进口单ID获取报关单详情
     */
    @RequestMapping("/detailByImportId/{importId}")
    @RequiresPermissions("cst:dec:detail")
    public R detailByImportId(@PathVariable("importId") Long importId) {
        CstDecEntity entity = decService.detailByImportId(importId);
        return entity == null ? R.error() : R.ok().put("entity", entity);
    }

    /**
     * 通过出口单ID获取报关单详情
     */
    @RequestMapping("/detailByExportId/{exportId}")
    @RequiresPermissions("cst:invt:detail")
    public R detailByExportId(@PathVariable("exportId") Long exportId) {
        CstDecEntity entity = decService.detailByExportId(exportId);
        return entity == null ? R.error() : R.ok().put("entity", entity);
    }

    /**
     * 通过contractId获取报关单列表
     */
    @RequestMapping("/listByContractId")
    @RequiresPermissions("cst:dec:list")
    public R detail(@RequestBody CstInvtEntity entity) {
        return R.ok().put("entity", decService.listByContractId(entity.getFkContractId()));
    }

    /**
     * 保存或更新报关单
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:dec:save")
    public R save(@RequestBody CstDecHeaderEntity headerEntity) {
        try {
            headerService.save(headerEntity);
            return R.ok().put("id", headerEntity.getId());
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 保存或更新报关单
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:dec:save")
    public R update(@RequestBody CstDecHeaderEntity headerEntity) {
        try {
            headerService.updateById(headerEntity);
            return R.ok().put("id", headerEntity.getId());
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 保存和更改报关单物料表体
     */
    @RequestMapping("/saveGoods")
    @RequiresPermissions("cst:dec:save")
    public R saveGoods(@RequestBody CstDecListEntity entity) {
        UpdateWrapper<CstDecListEntity> uw = new UpdateWrapper<>();
        uw.eq("id", entity.getId());
        goodsService.saveOrUpdate(entity, uw);
        return R.ok().put("id", entity.getId());
    }

    /**
     * 保存和更改报关单集装箱表体
     */
    @RequestMapping("/saveContainer")
    @RequiresPermissions("cst:dec:save")
    public R saveContainer(@RequestBody CstDecContainerEntity entity) {
        UpdateWrapper<CstDecContainerEntity> uw = new UpdateWrapper<>();
        uw.eq("id", entity.getId());
        containerService.saveOrUpdate(entity, uw);
        return R.ok().put("id", entity.getId());
    }

    /**
     * 通过contractId生成手册XML
     */
    @RequestMapping("/generateDecXml")
    @RequiresPermissions("cst:dec:generateDecXml")
    public R generateDecXml(HttpServletResponse response, @RequestBody CstDecEntity entity) {
        entity.setId(getUserId());
        String url = decService.generateDecXml(response, entity);
        return R.ok().put("url", url);
    }
}
