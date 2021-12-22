package io.renren.modules.ctb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbImgServiceContractEntity;
import io.renren.modules.ctb.service.CtbImgServiceContractService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 报关行服务企业的合同附件表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-21 16:09:24
 */
@RestController
@RequestMapping("ctb/ctbimgservicecontract")
public class CtbImgServiceContractController {
    private CtbImgServiceContractService ctbImgServiceContractService;

    public CtbImgServiceContractController(CtbImgServiceContractService ctbImgServiceContractService) {
        this.ctbImgServiceContractService = ctbImgServiceContractService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:ctbimgservicecontract:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = ctbImgServiceContractService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:ctbimgservicecontract:info")
    public R info(@PathVariable("id") Long id) {
        CtbImgServiceContractEntity ctbImgServiceContract = ctbImgServiceContractService.getById(id);

        return R.ok().put("ctbImgServiceContract", ctbImgServiceContract);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:ctbimgservicecontract:save")
    public R save(@RequestBody CtbImgServiceContractEntity ctbImgServiceContract) {
        ctbImgServiceContractService.save(ctbImgServiceContract);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:ctbimgservicecontract:update")
    public R update(@RequestBody CtbImgServiceContractEntity ctbImgServiceContract) {
        ctbImgServiceContractService.updateById(ctbImgServiceContract);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:ctbimgservicecontract:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbImgServiceContractService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 保存合同附件
     */
    @RequestMapping("/saveList")
    @RequiresPermissions("ctb:ctbimgservicecontract:saveList")
    public R saveList(@RequestBody CtbImgServiceContractEntity entity) {
        ctbImgServiceContractService.remove(new QueryWrapper<CtbImgServiceContractEntity>().eq("fk_service_contract_id", entity.getFkServiceContractId()));
        for (CtbImgServiceContractEntity ctbImgServiceContractEntity : entity.getImgList()) {
            ctbImgServiceContractService.save(ctbImgServiceContractEntity);
        }
        return R.ok();
    }

    /**
     * 返回合同附件list
     */
    @RequestMapping("/contractAnnexInfo/{id}")
    @RequiresPermissions("ctb:ctbimgservicecontract:contractAnnexInfo")
    public R contractAnnexInfo(@PathVariable("id") Long id) {
        List<CtbImgServiceContractEntity> contractAnnex = ctbImgServiceContractService.contractAnnexInfo(id);
        return R.ok().put("contractAnnex", contractAnnex);
    }

}
