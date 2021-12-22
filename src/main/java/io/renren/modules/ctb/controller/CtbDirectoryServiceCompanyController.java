package io.renren.modules.ctb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.renren.modules.bind.service.BindCbsCustomsBrokerCtbCompanyService;
import io.renren.modules.ctb.entity.CtbDirectoryServiceCompanyEntity;
import io.renren.modules.ctb.entity.CtbMoneyTypeEntity;
import io.renren.modules.ctb.entity.CtbPriceListItemEntity;
import io.renren.modules.ctb.entity.CtbServiceContractEntity;
import io.renren.modules.ctb.service.CtbDirectoryServiceCompanyService;
import io.renren.modules.ctb.service.CtbMoneyTypeService;
import io.renren.modules.org_ctb.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * 报关行服务公司名录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@RestController
@RequestMapping("ctb/directory/servicecompany")
public class CtbDirectoryServiceCompanyController extends AbstractController {
    private CtbDirectoryServiceCompanyService ctbDirectoryServiceCompanyService;
    private CtbMoneyTypeService ctbMoneyTypeService;
    private BindCbsCustomsBrokerCtbCompanyService bindCbsCustomsBrokerCtbCompanyService;

    public CtbDirectoryServiceCompanyController(
            CtbDirectoryServiceCompanyService ctbDirectoryServiceCompanyService,
            BindCbsCustomsBrokerCtbCompanyService bindCbsCustomsBrokerCtbCompanyService,
            CtbMoneyTypeService ctbMoneyTypeService
    ) {
        this.ctbDirectoryServiceCompanyService = ctbDirectoryServiceCompanyService;
        this.bindCbsCustomsBrokerCtbCompanyService = bindCbsCustomsBrokerCtbCompanyService;
        this.ctbMoneyTypeService = ctbMoneyTypeService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:directory:servicecompany:list")
    public R list(@RequestBody CtbDirectoryServiceCompanyEntity entity) {
        entity.setCtbTenantId(getCtbTenantId());
        PageUtils page = ctbDirectoryServiceCompanyService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/listForChoice")
    @RequiresPermissions("ctb:directory:servicecompany:list")
    public R listForChoice(@RequestBody CtbDirectoryServiceCompanyEntity entity) {
        entity.setCtbTenantId(getCtbTenantId());
        return R.ok().put("list", ctbDirectoryServiceCompanyService.listForChoice(entity));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:directory:servicecompany:info")
    public R info(@PathVariable("id") Long id) {
        CtbDirectoryServiceCompanyEntity ctbDirectoryServiceCompany = ctbDirectoryServiceCompanyService.getById(id);

        return R.ok().put("ctbDirectoryServiceCompany", ctbDirectoryServiceCompany);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:directory:servicecompany:save")
    public R save(@RequestBody CtbDirectoryServiceCompanyEntity ctbDirectoryServiceCompany) {
        ctbDirectoryServiceCompanyService.save(ctbDirectoryServiceCompany);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:directory:servicecompany:update")
    public R update(@RequestBody CtbDirectoryServiceCompanyEntity ctbDirectoryServiceCompany) {
        ctbDirectoryServiceCompanyService.updateById(ctbDirectoryServiceCompany);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:directory:servicecompany:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbDirectoryServiceCompanyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 无需合并
     */
    @RequestMapping("/notMerge")
    @RequiresPermissions("ctb:directory:servicecompany:notMerge")
    public R notMerge(@RequestBody Long[] ids) {
        QueryWrapper<CtbDirectoryServiceCompanyEntity> qw = new QueryWrapper<>();
        qw.in("id", Arrays.asList(ids));
        ctbDirectoryServiceCompanyService.update(CtbDirectoryServiceCompanyEntity.builder().needSyn(false).build(), qw);
        return R.ok();
    }


    /**
     * 自编辑的企业信息
     */
    @RequestMapping("/selfEditingList")
    @RequiresPermissions("ctb:directory:servicecompany:selfEditingList")
    public R selfEditingList(@RequestBody CtbDirectoryServiceCompanyEntity entity) {
        return R.ok(ctbDirectoryServiceCompanyService.selfEditingList(entity));
    }

    /**
     * 合并
     */
    @RequestMapping("/merge")
    @RequiresPermissions("ctb:directory:servicecompany:merge")
    @Transactional
    public R merge(@RequestBody CtbDirectoryServiceCompanyEntity entity) {
        // 更新
        QueryWrapper<BindCbsCustomsBrokerCtbCompanyEntity> qw = new QueryWrapper<>();
        qw.eq("fk_ctb_service_company_id", entity.getPreDeleteId());
        qw.eq("fk_ctb_company_id", getCtbTenantId());
        bindCbsCustomsBrokerCtbCompanyService.update(BindCbsCustomsBrokerCtbCompanyEntity.builder().fkCtbServiceCompanyId(entity.getId()).build(), qw);
        // 删除
        ctbDirectoryServiceCompanyService.removeById(entity.getPreDeleteId());
        return R.ok();
    }


    /**
     * 返回合同信息
     */
    @RequestMapping("/contractInfo/{id}")
    @RequiresPermissions("ctb:directory:servicecompany:contractInfo")
    public R contractInfo(@PathVariable("id") Long id) {
        CtbServiceContractEntity contract = bindCbsCustomsBrokerCtbCompanyService.selectServiceContract(BindCbsCustomsBrokerCtbCompanyEntity.builder()
                .fkCtbServiceCompanyId(id)
                .fkCtbCompanyId(getCtbTenantId())
                .build());
        return R.ok().put("contract", contract);
    }


    /**
     * 乙方发起确认合同信息
     */
    @RequestMapping("/initiateConfirmation/{id}")
    @RequiresPermissions("ctb:directory:servicecompany:initiateConfirmation")
    public R initiateConfirmation(@PathVariable("id") Long id) {
        bindCbsCustomsBrokerCtbCompanyService.initiateConfirmation(BindCbsCustomsBrokerCtbCompanyEntity.builder()
                .fkCtbServiceCompanyId(id)
                .fkCtbCompanyId(getCtbTenantId())
                .build());
        return R.ok();
    }

    /**
     * 确核合同
     */
    @RequestMapping("/confirmContract/{id}")
    @RequiresPermissions("ctb:directory:servicecompany:confirmContract")
    @Transactional
    public R confirmContract(@PathVariable("id") Long id) {
        try {
            bindCbsCustomsBrokerCtbCompanyService.confirmContract(BindCbsCustomsBrokerCtbCompanyEntity.builder()
                    .fkCtbServiceCompanyId(id)
                    .fkCtbCompanyId(getCtbTenantId())
                    .build());
            // 转化priceList
            QueryWrapper<BindCbsCustomsBrokerCtbCompanyEntity> qw = new QueryWrapper<>();
            qw.eq("fk_ctb_service_company_id", id);
            qw.eq("fk_ctb_company_id", getCtbTenantId());
            BindCbsCustomsBrokerCtbCompanyEntity bindEntity = bindCbsCustomsBrokerCtbCompanyService.getOne(qw);
            bindEntity = bindCbsCustomsBrokerCtbCompanyService.detail(bindEntity.getId());
            List<CtbPriceListItemEntity> ctbPriceListItemEntities = bindEntity.getCtbServiceContractEntity().getPriceListEntity().getItemEntityList();
            for (CtbPriceListItemEntity ctbPriceListItemEntity : ctbPriceListItemEntities) {
                CtbMoneyTypeEntity ctbMoneyTypeEntity = CtbMoneyTypeEntity.builder().
                        name(ctbPriceListItemEntity.getName()).
                        isReimburse(ctbPriceListItemEntity.getIsReimburse()).
                        defTaxRate(ctbPriceListItemEntity.getTaxRate()).
                        defUnit(ctbPriceListItemEntity.getUnit()).
                        defUnitPrice(ctbPriceListItemEntity.getPrice()).
                        fkServiceCompanyId(bindEntity.getFkCtbServiceCompanyId()).
                        available(true).
                        io(ctbPriceListItemEntity.getIsReimburse() ? "O" : "I").
                        build();
                ctbMoneyTypeService.save(ctbMoneyTypeEntity);
            }
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }

    }

}
