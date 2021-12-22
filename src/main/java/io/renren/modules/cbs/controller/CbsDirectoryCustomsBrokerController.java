package io.renren.modules.cbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.renren.modules.bind.service.BindCbsCustomsBrokerCtbCompanyService;
import io.renren.modules.bind.vo.CreateBindCompanyVo;
import io.renren.modules.cbs.entity.CbsDirectoryCustomsBrokerEntity;
import io.renren.modules.cbs.service.CbsDirectoryCustomsBrokerService;
import io.renren.modules.ctb.entity.CtbPriceListEntity;
import io.renren.modules.ctb.entity.CtbPriceListItemEntity;
import io.renren.modules.ctb.entity.CtbPriceListTempleteEntity;
import io.renren.modules.ctb.entity.CtbServiceContractEntity;
import io.renren.modules.ctb.service.CtbPriceListItemService;
import io.renren.modules.ctb.service.CtbPriceListService;
import io.renren.modules.ctb.service.CtbPriceListTempleteService;
import io.renren.modules.ctb.service.CtbServiceContractService;
import io.renren.modules.org_cbs.controller.AbstractController;
import io.renren.modules.org_cbs.entity.OrgCbsCompanyEntity;
import io.renren.modules.org_cbs.service.OrgCbsCompanyService;
import io.renren.modules.org_ctb.entity.OrgCtbCompanyEntity;
import io.renren.modules.org_ctb.service.OrgCtbCompanyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 报关行名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@RestController
@RequestMapping("cbs/directory/customsbroker")
public class CbsDirectoryCustomsBrokerController extends AbstractController {
    private BindCbsCustomsBrokerCtbCompanyService bindCbsCustomsBrokerCtbCompanyService;
    private OrgCbsCompanyService orgCbsCompanyService;
    private OrgCtbCompanyService orgCtbCompanyService;
    private CbsDirectoryCustomsBrokerService cbsDirectoryCustomsBrokerService;
    private CtbServiceContractService ctbServiceContractService;
    private CtbPriceListService ctbPriceListService;
    private CtbPriceListItemService ctbPriceListItemService;
    private CtbPriceListTempleteService ctbPriceListTempleteService;

    public CbsDirectoryCustomsBrokerController(
            BindCbsCustomsBrokerCtbCompanyService bindCbsCustomsBrokerCtbCompanyService,
            OrgCbsCompanyService orgCbsCompanyService,
            OrgCtbCompanyService orgCtbCompanyService,
            CbsDirectoryCustomsBrokerService cbsDirectoryCustomsBrokerService,
            CtbServiceContractService ctbServiceContractService,
            CtbPriceListService ctbPriceListService,
            CtbPriceListItemService ctbPriceListItemService,
            CtbPriceListTempleteService ctbPriceListTempleteService
    ) {
        this.bindCbsCustomsBrokerCtbCompanyService = bindCbsCustomsBrokerCtbCompanyService;
        this.orgCbsCompanyService = orgCbsCompanyService;
        this.orgCtbCompanyService = orgCtbCompanyService;
        this.cbsDirectoryCustomsBrokerService = cbsDirectoryCustomsBrokerService;
        this.ctbServiceContractService = ctbServiceContractService;
        this.ctbPriceListService = ctbPriceListService;
        this.ctbPriceListItemService = ctbPriceListItemService;
        this.ctbPriceListTempleteService = ctbPriceListTempleteService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:directory:customsbroker:list")
    public R list(@RequestBody CbsDirectoryCustomsBrokerEntity entity) {
        PageUtils page = cbsDirectoryCustomsBrokerService.queryPage(entity);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:directory:customsbroker:info")
    public R info(@PathVariable("id") Long id) {
        CbsDirectoryCustomsBrokerEntity cbsDirectoryCustomsBroker = cbsDirectoryCustomsBrokerService.getById(id);
        return R.ok().put("cbsDirectoryCustomsBroker", cbsDirectoryCustomsBroker);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:directory:customsbroker:save")
    public R save(@RequestBody CbsDirectoryCustomsBrokerEntity cbsDirectoryCustomsBroker) {
        cbsDirectoryCustomsBrokerService.save(cbsDirectoryCustomsBroker);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:directory:customsbroker:update")
    public R update(@RequestBody CbsDirectoryCustomsBrokerEntity cbsDirectoryCustomsBroker) {
        cbsDirectoryCustomsBrokerService.updateById(cbsDirectoryCustomsBroker);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:directory:customsbroker:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsDirectoryCustomsBrokerService.update(CbsDirectoryCustomsBrokerEntity.builder().available(0).build(), new QueryWrapper<CbsDirectoryCustomsBrokerEntity>().in("id", ids));
        return R.ok();
    }

    /**
     * 获取已绑定的报关行列表
     */
    @RequestMapping("/listBound")
    @RequiresPermissions("cbs:directory:customsbroker:list")
    public R listBound() {
        return R.ok().put("list", cbsDirectoryCustomsBrokerService.listBound());
    }

    /**
     * 获取CtbCompany列表以及已绑定关系
     */
    @PostMapping("/listCbsCompanyWithBind")
    @RequiresPermissions("cbs:directory:customsbroker:saveCustomize")
    public R listCbsCompanyWithBind(@RequestBody OrgCtbCompanyEntity entity) {
        return R.ok().put("list", orgCtbCompanyService.listWithCbsBind(getTenantId(), entity.getName()));
    }

    /**
     * 保存企业发起的报关行
     */
    @RequestMapping("/saveCustomize")
    @RequiresPermissions("cbs:directory:customsbroker:saveCustomize")
    @Transactional
    public R saveCustomize(@RequestBody CreateBindCompanyVo vo) {
        Long bindId = bindCbsCustomsBrokerCtbCompanyService.createBind(orgCbsCompanyService.getById(getTenantId()), vo.getCbsDirectoryId(), vo.getCtbCompanyId());
        // 创建服务合同实体
        BindCbsCustomsBrokerCtbCompanyEntity bindEntity = bindCbsCustomsBrokerCtbCompanyService.detail(bindId);
        OrgCbsCompanyEntity cbsCompanyEntity = bindEntity.getCbsCompanyEntity();
        OrgCtbCompanyEntity ctbCompanyEntity = bindEntity.getCtbCompanyEntity();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
        CtbServiceContractEntity ctbServiceContractEntity = CtbServiceContractEntity.builder().
                ctbTenantId(ctbCompanyEntity.getId()).
                code(ctbCompanyEntity.getHeaderCode() + sdf.format(new Date())).
                partA(cbsCompanyEntity.getName()).
                partAAddress(cbsCompanyEntity.getAddress()).
                partAManager(cbsCompanyEntity.getContactor()).
                partAPhone(cbsCompanyEntity.getPhone()).
                partB(ctbCompanyEntity.getName()).
                partBAddress(ctbCompanyEntity.getAddress()).
                partBManager(ctbCompanyEntity.getContactor()).
                partBPhone(ctbCompanyEntity.getCompanyPhone()).
                build();
        ctbServiceContractService.create(ctbServiceContractEntity);
        // 绑定服务合同实体
        bindEntity.setFkCtbServiceContractId(ctbServiceContractEntity.getId());
        bindCbsCustomsBrokerCtbCompanyService.updateById(bindEntity);
        // 创建报价单实体
        CtbPriceListEntity ctbPriceListEntity = CtbPriceListEntity.builder().
                fkContractId(ctbServiceContractEntity.getId()).
                address(ctbCompanyEntity.getAddress()).
                phone(ctbCompanyEntity.getCompanyPhone()).
                build();
        ctbPriceListService.save(ctbPriceListEntity);
        // 报价单列表初始化并更新
        CtbPriceListTempleteEntity ctbPriceListTempleteEntity = ctbPriceListTempleteService.getByTenantId(ctbCompanyEntity.getId());
        List<CtbPriceListItemEntity> priceListItemEntityList = new Gson().fromJson(ctbPriceListTempleteEntity.getTempleteJson(), new TypeToken<List<CtbPriceListItemEntity>>(){}.getType());
        for (CtbPriceListItemEntity priceListItemEntity : priceListItemEntityList) {
            priceListItemEntity.setFkPriceListId(ctbPriceListEntity.getId());
            ctbPriceListItemService.save(priceListItemEntity);
        }
        return R.ok();
    }
}
