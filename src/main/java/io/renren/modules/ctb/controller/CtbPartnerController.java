package io.renren.modules.ctb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ctb.entity.CtbPartnerEntity;
import io.renren.modules.ctb.service.CtbPartnerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 交易对象（公司）名录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
@RestController
@RequestMapping("ctb/partner")
public class CtbPartnerController {
    private CtbPartnerService ctbPartnerService;

    @Autowired
    public void setCtbPartnerService(CtbPartnerService ctbPartnerService) {
        this.ctbPartnerService = ctbPartnerService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ctb:partner:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = ctbPartnerService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 通过type获取列表
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/listByTypes")
    @RequiresPermissions("ctb:partner:list")
    public R list(@RequestBody HashMap<String, Object> params) {
        List<Integer> types = (ArrayList<Integer>) params.get("types");
        String name = (String) params.get("name");
        return R.ok().put("list", ctbPartnerService.listByTypes(types, name));
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ctb:partner:info")
    public R info(@PathVariable("id") Long id) {
        CtbPartnerEntity ctbPartner = ctbPartnerService.detail(id);
        return R.ok().put("ctbPartner", ctbPartner);
    }

    /**
     * 信息
     */
    @RequestMapping("/infoByCode/{code}")
    @RequiresPermissions("ctb:partner:info")
    public R infoByCode(@PathVariable("code") String code) {
        CtbPartnerEntity ctbPartner = ctbPartnerService.detailByCode(code);
        return R.ok().put("ctbPartner", ctbPartner);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ctb:partner:save")
    public R save(@RequestBody CtbPartnerEntity ctbPartner) {
        return R.ok(ctbPartnerService.saveReturnId(ctbPartner));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ctb:partner:update")
    public R update(@RequestBody CtbPartnerEntity ctbPartner) {
        ctbPartnerService.update(ctbPartner);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ctb:partner:delete")
    public R delete(@RequestBody Long[] ids) {
        ctbPartnerService.update(CtbPartnerEntity.builder().available(0).build(), new QueryWrapper<CtbPartnerEntity>().in("id", ids));
        return R.ok();
    }

}
