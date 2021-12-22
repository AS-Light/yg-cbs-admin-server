package io.renren.modules.ctb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.DateTimeUtils;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.renren.modules.bind.service.BindCbsCustomsBrokerCtbCompanyService;
import io.renren.modules.ctb.entity.CtbServiceContractEntity;
import io.renren.modules.ctb.service.CtbServiceContractService;
import io.renren.modules.org_ctb.controller.AbstractController;
import io.renren.modules.org_ctb.entity.OrgCtbCompanyEntity;
import io.renren.modules.org_ctb.service.OrgCtbCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 报关行货运代理合同
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/servicecontract")
public class CtbServiceContractController extends AbstractController {

    private CtbServiceContractService ctbServiceContractService;
    private BindCbsCustomsBrokerCtbCompanyService bindCbsCustomsBrokerCtbCompanyService;
    private OrgCtbCompanyService orgCtbCompanyService;

    public CtbServiceContractController(
            CtbServiceContractService ctbServiceContractService,
            BindCbsCustomsBrokerCtbCompanyService bindCbsCustomsBrokerCtbCompanyService,
            OrgCtbCompanyService orgCtbCompanyService
    ) {
        this.ctbServiceContractService = ctbServiceContractService;
        this.bindCbsCustomsBrokerCtbCompanyService = bindCbsCustomsBrokerCtbCompanyService;
        this.orgCtbCompanyService = orgCtbCompanyService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:servicecontract:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = ctbServiceContractService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:servicecontract:info")
    public R info(@PathVariable("id") Long id) {
        CtbServiceContractEntity ctbServiceContract = ctbServiceContractService.getById(id);
        return R.ok().put("ctbServiceContract", ctbServiceContract);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:servicecontract:save")
    @Transactional
    public R save(@RequestBody CtbServiceContractEntity ctbServiceContract) {
        Long ctbTenantId = getCtbTenantId();
        OrgCtbCompanyEntity orgCtbCompanyEntity = orgCtbCompanyService.getById(ctbTenantId);
        ctbServiceContract.setCode(orgCtbCompanyEntity.getHeaderCode() + DateTimeUtils.getCurrentShortDateTimeStr());
        ctbServiceContract.setCtbTenantId(ctbTenantId);
        ctbServiceContractService.save(ctbServiceContract);
        // 报价单
        QueryWrapper<BindCbsCustomsBrokerCtbCompanyEntity> qw = new QueryWrapper<>();
        qw.eq("fk_ctb_service_company_id", ctbServiceContract.getServiceCompanyId());
        qw.eq("fk_ctb_company_id", ctbServiceContract.getCtbTenantId());
        bindCbsCustomsBrokerCtbCompanyService.update(BindCbsCustomsBrokerCtbCompanyEntity.builder().fkCtbServiceContractId(ctbServiceContract.getId()).build(), qw);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:servicecontract:update")
    public R update(@RequestBody CtbServiceContractEntity ctbServiceContract) {
        ctbServiceContractService.updateById(ctbServiceContract);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:servicecontract:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbServiceContractService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 预览
     */
    @RequestMapping("/preview/{id}")
    @RequiresPermissions("ctb:servicecontract:preview")
    public R preview(@PathVariable("id") Long id) {
        String pdf = ctbServiceContractService.generatePDF(id);
        return R.ok(pdf);
    }

    /**
     * 预览保存
     */
    @RequestMapping("/previewSave/{id}")
    @RequiresPermissions("ctb:servicecontract:previewSave")
    @Transactional
    public R previewSave(@PathVariable("id") Long id) {
        String pdf = ctbServiceContractService.generatePDF(id);
        ctbServiceContractService.saveOrUpdate(CtbServiceContractEntity.builder().pdf(pdf).ctbTenantId(getCtbTenantId()).build(),
                new QueryWrapper<CtbServiceContractEntity>().eq("ctb_tenant_id", getCtbTenantId()));
        return R.ok();
    }
}
