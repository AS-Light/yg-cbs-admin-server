package io.renren.modules.cst.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.common.utils.R;
import io.renren.modules.cst.entity.CstInvtEntity;
import io.renren.modules.cst.entity.CstInvtHeaderEntity;
import io.renren.modules.cst.entity.CstInvtListEntity;
import io.renren.modules.cst.entity.CstNptsEmlEntity;
import io.renren.modules.cst.service.CstInvtHeaderService;
import io.renren.modules.cst.service.CstInvtListService;
import io.renren.modules.cst.service.CstInvtService;
import io.renren.modules.org_cbs.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("cst/invt")
public class CstInvtController extends AbstractController {

    private CstInvtService invtService;

    private CstInvtHeaderService invtHeaderService;

    private CstInvtListService invtGoodsService;

    @Autowired
    public void setInvtGoodsService(CstInvtListService invtGoodsService) {
        this.invtGoodsService = invtGoodsService;
    }

    @Autowired
    public void setInvtHeaderService(CstInvtHeaderService invtHeaderService) {
        this.invtHeaderService = invtHeaderService;
    }

    @Autowired
    public void setInvtService(CstInvtService invtService) {
        this.invtService = invtService;
    }

    /**
     * 获取手册保税核注清单详情
     */
    @RequestMapping("/detail/{id}")
    @RequiresPermissions("cst:invt:detail")
    public R detail(@PathVariable("id") Long id) {
        CstInvtEntity entity = invtService.detail(id);
        return R.ok().put("entity", entity);
    }

    /**
     * 通过进口单ID获取手册保税核注清单详情
     */
    @RequestMapping("/detailByImportId/{importId}")
    @RequiresPermissions("cst:invt:detail")
    public R detailByImportId(@PathVariable("importId") Long importId) {
        CstInvtEntity entity = invtService.detailByImportId(importId);
        return entity == null ? R.error() : R.ok().put("entity", entity);
    }

    /**
     * 通过出口单ID获取手册保税核注清单详情
     */
    @RequestMapping("/detailByExportId/{exportId}")
    @RequiresPermissions("cst:invt:detail")
    public R detailByExportId(@PathVariable("exportId") Long exportId) {
        CstInvtEntity entity = invtService.detailByExportId(exportId);
        return entity == null ? R.error() : R.ok().put("entity", entity);
    }

    /**
     * 通过contractId获取手册保税核注清单详情
     */
    @RequestMapping("/listByContractId")
    @RequiresPermissions("cst:invt:list")
    public R detail(@RequestBody CstInvtEntity entity) {
        return R.ok().put("entity", invtService.listByContractId(entity.getFkContractId()));
    }

    /**
     * 保存或更新手册保税核注清单
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:invt:save")
    public R save(@RequestBody CstInvtHeaderEntity headerEntity) {
        try {
            invtHeaderService.save(headerEntity);
            return R.ok().put("id", headerEntity.getId());
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 保存或更新手册保税核注清单
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:invt:save")
    public R update(@RequestBody CstInvtHeaderEntity headerEntity) {
        try {
            invtHeaderService.updateById(headerEntity);
            return R.ok().put("id", headerEntity.getId());
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 保存和更改手册保税核注清单商品（料件/成品）
     */
    @RequestMapping("/saveGoods")
    @RequiresPermissions("cst:invt:save")
    public R save(@RequestBody CstInvtListEntity entity) {
        UpdateWrapper<CstInvtListEntity> uw = new UpdateWrapper<>();
        uw.eq("id", entity.getId());
        invtGoodsService.saveOrUpdate(entity, uw);
        return R.ok().put("id", entity.getId());
    }

    /**
     * 通过id生成手册XML
     */
    @RequestMapping("/generateInvtXml")
    @RequiresPermissions("cst:invt:generateInvtXml")
    public R generateInvtXml(HttpServletResponse response, @RequestBody CstInvtEntity entity) {
        entity.setId(getUserId());
        String url = invtService.generateInvtXml(response, entity);
        return R.ok().put("url", url);
    }
}
