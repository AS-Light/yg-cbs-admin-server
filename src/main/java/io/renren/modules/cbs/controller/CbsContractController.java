package io.renren.modules.cbs.controller;

import io.renren.common.annotation.OrgCbsLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.bind.service.BindCbsContractCtbProcessingTradeService;
import io.renren.modules.cbs.entity.CbsContractEntity;
import io.renren.modules.cbs.entity.CbsContractStatusStreamEntity;
import io.renren.modules.cbs.service.CbsContractService;
import io.renren.modules.cbs.vo.SynContractToCtbVo;
import io.renren.modules.org_cbs.controller.AbstractController;
import io.renren.modules.sys.entity.MessageEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;


/**
 * 合同表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-09 11:16:37
 */
@Api(tags = "合同")
@RestController
@RequestMapping("cbs/contract")
public class CbsContractController extends AbstractController {

    private CbsContractService cbsContractService;
    private BindCbsContractCtbProcessingTradeService bindCbsContractCtbProcessingTradeService;

    public CbsContractController(
            CbsContractService cbsContractService,
            BindCbsContractCtbProcessingTradeService bindCbsContractCtbProcessingTradeService) {
        this.cbsContractService = cbsContractService;
        this.bindCbsContractCtbProcessingTradeService = bindCbsContractCtbProcessingTradeService;
    }

    @OrgCbsLog("创建合同")
    @ApiOperation("创建并返回主键")
    @PostMapping("/saveReturnId")
    @RequiresPermissions("cbs:contract:saveReturnId")
    public R saveReturnId(@RequestBody CbsContractEntity entity) {
        entity.setOperator(getUserId());
        Long id = cbsContractService.saveReturnId(entity);
        return R.ok(id);
    }

    @ApiOperation("合同列表")
    @PostMapping("/list")
    @RequiresPermissions("cbs:contract:list")
    public R list(@RequestBody CbsContractEntity entity) {
        PageUtils page = cbsContractService.queryIndex(entity);
        return R.ok().put("page", page);
    }

    @OrgCbsLog("查看合同详情")
    @ApiOperation("详情")
    @GetMapping("/info/{id}")
    @RequiresPermissions("cbs:contract:info")
    public R info(@PathVariable("id") Long id) {
        CbsContractEntity entity = cbsContractService.detail(id);
        return R.ok().put("entity", entity);
    }

    @OrgCbsLog("编辑合同")
    @ApiOperation("修改")
    @PostMapping("/update")
    @RequiresPermissions("cbs:contract:update")
    public R update(@RequestBody CbsContractEntity entity) {
        entity.setOperator(getUserId());
        return R.ok(cbsContractService.updateAllById(entity));
    }

    @OrgCbsLog("反填手册编号")
    @ApiOperation("修改")
    @PostMapping("/updateEmlCode")
    @RequiresPermissions("cbs:contract:update")
    public R updateEmlCode(@RequestBody CbsContractEntity entity) {
        entity.setOperator(getUserId());
        cbsContractService.updateEmlCode(entity);
        return R.ok();
    }

    @OrgCbsLog("合同提审")
    @ApiOperation("提审")
    @PostMapping("/submit")
    @RequiresPermissions("cbs:contract:submit")
    public R submit(@RequestBody CbsContractStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsContractService.submit(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("撤销合同提审")
    @ApiOperation("撤销提审")
    @PostMapping("/submitBack")
    @RequiresPermissions("cbs:contract:submitBack")
    public R submitBack(@RequestBody CbsContractStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = cbsContractService.submitBack(statusStreamEntity);
        switch (result) {
            case -1:
                return R.error("合同状态不合法，待审核状态合同才能撤销提审");
            case -2:
                return R.error("未知异常，请联系管理员");
            case -3:
                return R.error("没有找到这个合同,请联系管理员");
            default:
                return R.ok(result);
        }
    }

    @OrgCbsLog("审核合同")
    @ApiOperation("审核")
    @PostMapping("/check")
    @RequiresPermissions("cbs:contract:check")
    public R check(@RequestBody CbsContractStatusStreamEntity statusStreamEntity) {
        try {
            statusStreamEntity.setOperator(getUserId());
            cbsContractService.check(statusStreamEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error(e.getMessage() == null ? "未知错误" : e.getMessage());
        }
    }

    @OrgCbsLog("合同结案提审")
    @ApiOperation("结案提审")
    @PostMapping("/submitComplete")
    @RequiresPermissions("cbs:contract:submit")
    public R submitComplete(@RequestBody CbsContractStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        return R.ok(cbsContractService.submitComplete(statusStreamEntity));
    }

    @OrgCbsLog("撤销合同结案提审")
    @ApiOperation("结案撤审")
    @PostMapping("/submitCompleteBack")
    @RequiresPermissions("cbs:contract:submitBack")
    public R submitCompleteBack(@RequestBody CbsContractStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        Integer result = cbsContractService.submitCompleteBack(statusStreamEntity);
        switch (result) {
            case -1:
                return R.error("合同状态不合法，待审核状态合同才能撤销提审");
            case -2:
                return R.error("未知异常，请联系管理员");
            case -3:
                return R.error("没有找到这个合同,请联系管理员");
            default:
                return R.ok(result);
        }
    }

    @OrgCbsLog("审核合同结案")
    @ApiOperation("结案审核")
    @PostMapping("/checkComplete")
    @RequiresPermissions("cbs:contract:check")
    public R checkComplete(@RequestBody CbsContractStatusStreamEntity statusStreamEntity) {
        statusStreamEntity.setOperator(getUserId());
        return R.ok(cbsContractService.checkComplete(statusStreamEntity));
    }

    @OrgCbsLog("删除合同")
    @ApiOperation("删除")
    @PostMapping("/delete")
    @RequiresPermissions("cbs:contract:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsContractService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @ApiOperation("状态变更")
    @PostMapping("/status")
    @RequiresPermissions("cbs:contract:status")
    public R status(@RequestBody CbsContractEntity entity) {
        CbsContractEntity e = new CbsContractEntity();
        e.setId(entity.getId());
        e.setStatus(entity.getStatus());
        e.setOperator(getUserId());
        MessageEntity message = new MessageEntity();
        message.setContractEntity(e);
//        simpleProducer.send("contract", "contract", message);
        cbsContractService.updateAllById(e);
        return R.ok();
    }

    @ApiOperation("删除")
    @PostMapping("/deleteOne")
    @RequiresPermissions("cbs:contract:deleteOne")
    public R deleteOne(@RequestBody CbsContractEntity entity) {
        return R.ok(cbsContractService.deleteOne(entity));
    }

    @ApiOperation("多type合同列表")
    @PostMapping("/listForType")
    @RequiresPermissions("cbs:contract:list")
    public R listForType(@RequestBody CbsContractEntity entity) {
        PageUtils page = cbsContractService.listForType(entity);
        return R.ok().put("page", page);
    }

    @ApiOperation("创建进口单用合同列表")
    @PostMapping("/listForCreateImport")
    @RequiresPermissions("cbs:contract:list")
    public R listForCreateImport() {
        List<CbsContractEntity> list = cbsContractService.listForCreateImport();
        return R.ok().put("list", list);
    }

    @ApiOperation("创建出口单用合同列表")
    @PostMapping("/listForCreateExport")
    @RequiresPermissions("cbs:contract:list")
    public R listForCreateExport() {
        List<CbsContractEntity> list = cbsContractService.listForCreateExport();
        return R.ok().put("list", list);
    }

    @ApiOperation("创建国内收货单用合同列表")
    @PostMapping("/listForCreatePurchase")
    @RequiresPermissions("cbs:contract:list")
    public R listForCreatePurchase() {
        List<CbsContractEntity> list = cbsContractService.listForCreatePurchase();
        return R.ok().put("list", list);
    }

    @ApiOperation("创建国内发货单用合同列表")
    @PostMapping("/listForCreateSell")
    @RequiresPermissions("cbs:contract:list")
    public R listForCreateSell() {
        List<CbsContractEntity> list = cbsContractService.listForCreateSell();
        return R.ok().put("list", list);
    }

    @ApiOperation("创建试产用合同列表")
    @PostMapping("/listForCreateProduce")
    @RequiresPermissions("cbs:contract:list")
    public R listForCreateProduce() {
        List<CbsContractEntity> list = cbsContractService.listForCreateProduce();
        return R.ok().put("list", list);
    }

    @ApiOperation("进口报关单号查询合同")
    @PostMapping("/selectContractByImportBillCode")
    @RequiresPermissions("cbs:contract:selectContractByImportBillCode")
    public R selectContractByImportBillCode(@RequestBody CbsContractEntity entity) {
        PageUtils page = cbsContractService.selectContractByImportBillCode(entity);
        return R.ok().put("page", page);
    }

    @ApiOperation("出口报关单号查询合同")
    @PostMapping("/selectContractByExportBillCode")
    @RequiresPermissions("cbs:contract:selectContractByExportBillCode")
    public R selectContractByExportBillCode(@RequestBody CbsContractEntity entity) {
        PageUtils page = cbsContractService.selectContractByExportBillCode(entity);
        return R.ok().put("page", page);
    }

    @ApiOperation("商品码查询合同")
    @PostMapping("/selectContractByGoodsCode")
    @RequiresPermissions("cbs:contract:selectContractByGoodsCode")
    public R selectContractByGoodsCode(@RequestBody CbsContractEntity entity) {
        PageUtils page = cbsContractService.selectContractByGoodsCode(entity);
        return R.ok().put("page", page);
    }

    @ApiOperation("HS码查询合同")
    @PostMapping("/selectContractByHsCode")
    @RequiresPermissions("cbs:contract:selectContractByHsCode")
    public R selectContractByHsCode(@RequestBody CbsContractEntity entity) {
        PageUtils page = cbsContractService.selectContractByHsCode(entity);
        return R.ok().put("page", page);
    }

    @ApiOperation("子合同列表")
    @PostMapping("/subcontractList")
    @RequiresPermissions("cbs:contract:subcontractList")
    public R subcontractList(@RequestBody CbsContractEntity entity) {
        PageUtils page = cbsContractService.subcontractList(entity);
        return R.ok().put("page", page);
    }

    @ApiOperation("AEO-导出Excel")
    @PostMapping("/AEOExportExcel")
    @RequiresPermissions("cbs:contract:AEOExportExcel")
    public R AEOExportExcel(HttpServletResponse response, @RequestBody CbsContractEntity entity) {
        String url = cbsContractService.AEOExportExcel(response, entity);
        return R.ok().put("url", url);
    }

    @ApiOperation("同步给报关行")
    @PostMapping("/synToCtb")
    @RequiresPermissions("cbs:contract:syn")
    public R synToCtb(@RequestBody SynContractToCtbVo vo) {
        return R.ok(bindCbsContractCtbProcessingTradeService.cbsSynToCtb(vo.getContractId(), getTenantId(), vo.getCtbCompanyId(), getUserId()));
    }
}
