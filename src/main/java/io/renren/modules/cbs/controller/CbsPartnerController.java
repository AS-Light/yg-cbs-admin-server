package io.renren.modules.cbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsPartnerEntity;
import io.renren.modules.cbs.service.CbsPartnerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 交易对象（公司）名录表
 */
@RestController
@RequestMapping("cbs/partner")
public class CbsPartnerController {

    private CbsPartnerService cbsPartnerService;

    @Autowired
    public void setCbsPartnerService(CbsPartnerService cbsPartnerService) {
        this.cbsPartnerService = cbsPartnerService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:partner:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsPartnerService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 通过type获取列表
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/listByTypes")
    @RequiresPermissions("cbs:partner:list")
    public R list(@RequestBody HashMap<String, Object> params) {
        List<Integer> types = (ArrayList<Integer>) params.get("types");
        String name = (String) params.get("name");
        return R.ok().put("list", cbsPartnerService.listByTypes(types, name));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:partner:info")
    public R info(@PathVariable("id") Long id) {
        CbsPartnerEntity cbsPartner = cbsPartnerService.detail(id);
        return R.ok().put("cbsPartner", cbsPartner);
    }

    /**
     * 信息
     */
    @RequestMapping("/infoByCode/{code}")
    @RequiresPermissions("cbs:partner:info")
    public R infoByCode(@PathVariable("code") String code) {
        CbsPartnerEntity cbsPartner = cbsPartnerService.detailByCode(code);
        return R.ok().put("cbsPartner", cbsPartner);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:partner:save")
    public R save(@RequestBody CbsPartnerEntity cbsPartner) {
        return R.ok(cbsPartnerService.saveReturnId(cbsPartner));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:partner:update")
    public R update(@RequestBody CbsPartnerEntity cbsPartner) {
        cbsPartnerService.update(cbsPartner);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:partner:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsPartnerService.update(CbsPartnerEntity.builder().available(0).build(), new QueryWrapper<CbsPartnerEntity>().in("id", ids));
        return R.ok();
    }

}
