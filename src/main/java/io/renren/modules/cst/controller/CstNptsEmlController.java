package io.renren.modules.cst.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.common.utils.R;
import io.renren.modules.cst.entity.CstNptsEmlConsumeEntity;
import io.renren.modules.cst.entity.CstNptsEmlEntity;
import io.renren.modules.cst.entity.CstNptsEmlImgExgEntity;
import io.renren.modules.cst.service.CstNptsEmlConsumeService;
import io.renren.modules.cst.service.CstNptsEmlImgExgService;
import io.renren.modules.cst.service.CstNptsEmlService;
import io.renren.modules.org_cbs.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("cst/nptseml")
public class CstNptsEmlController extends AbstractController {

    private CstNptsEmlService nptsEmlService;

    private CstNptsEmlImgExgService nptsEmlImgExgService;

    private CstNptsEmlConsumeService nptsEmlConsumeService;

    @Autowired
    public void setNptsEmlService(CstNptsEmlService nptsEmlService) {
        this.nptsEmlService = nptsEmlService;
    }

    @Autowired
    public void setNptsEmlImgExgService(CstNptsEmlImgExgService nptsEmlImgExgService) {
        this.nptsEmlImgExgService = nptsEmlImgExgService;
    }

    @Autowired
    public void setNptsEmlConsumeService(CstNptsEmlConsumeService nptsEmlConsumeService) {
        this.nptsEmlConsumeService = nptsEmlConsumeService;
    }

    /**
     * 获取手册详情
     */
    @RequestMapping("/detail/{id}")
    @RequiresPermissions("cst:nptseml:detail")
    public R detail(@PathVariable("id") Long id) {
        CstNptsEmlEntity entity = nptsEmlService.detail(id);
        return R.ok().put("entity", entity);
    }

    /**
     * 通过contractId获取手册详情
     */
    @RequestMapping("/detail")
    @RequiresPermissions("cst:nptseml:detail")
    public R detail(@RequestBody CstNptsEmlEntity entity) {
        return R.ok().put("entity", nptsEmlService.detailByContractId(entity.getFkContractId()));
    }

    /**
     * 通过料号获取手册料件或成品
     */
    @RequestMapping("/goods/selectByPartNo/{partNo}")
    @RequiresPermissions("cst:nptseml:detail")
    public R selectGoodsByPartNo(@PathVariable("partNo") Long partNo) {
        return R.ok().put("goods", nptsEmlService.selectGoodsByPartNo(partNo));
    }

    /**
     * 保存或更新手册
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:nptseml:save")
    public R save(@RequestBody CstNptsEmlEntity entity) {
        try {
            return R.ok().put("id", nptsEmlService.save(entity));
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 保存和更改手册商品（料件/成品）
     */
    @RequestMapping("/saveGoods")
    @RequiresPermissions("cst:nptseml:save")
    public R save(@RequestBody CstNptsEmlImgExgEntity entity) {
        UpdateWrapper<CstNptsEmlImgExgEntity> uw = new UpdateWrapper<>();
        uw.eq("gds_mtno", entity.getGdsMtno());
        nptsEmlImgExgService.saveOrUpdate(entity, uw);
        return R.ok().put("id", entity.getId());
    }

    /**
     * 保存和更改手册商品（料件/成品）
     */
    @RequestMapping("/saveConsume")
    @RequiresPermissions("cst:nptseml:save")
    public R save(@RequestBody CstNptsEmlConsumeEntity entity) {
        UpdateWrapper<CstNptsEmlConsumeEntity> uw = new UpdateWrapper<>();
        uw.eq("endprd_gds_mtno", entity.getEndprdGdsMtno());
        uw.eq("mtpck_gds_mtno", entity.getMtpckGdsMtno());
        nptsEmlConsumeService.saveOrUpdate(entity, uw);
        return R.ok().put("id", entity.getId());
    }

    /**
     * 通过contractId生成手册XML
     */
    @RequestMapping("/generateNptsEmlXml")
    @RequiresPermissions("cst:nptseml:generateNptsEmlXml")
    public R generateNptsEmlXml(HttpServletResponse response, @RequestBody CstNptsEmlEntity entity) {
        entity.setId(getUserId());
        String url = nptsEmlService.generateNptsEmlXml(response, entity);
        return R.ok().put("url", url);
    }
}
