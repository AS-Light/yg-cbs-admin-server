package io.renren.modules.cst.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.cst.entity.CstNptsDcrBillEntity;
import io.renren.modules.cst.service.CstNptsDcrBillService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 手册报核清单表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@RestController
@RequestMapping("cst/nptsdcrbill")
public class CstNptsDcrBillController {
    @Autowired
    private CstNptsDcrBillService cstNptsDcrBillService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cst:nptsdcrbill:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cstNptsDcrBillService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cst:nptsdcrbill:info")
    public R info(@PathVariable("id") Long id){
		CstNptsDcrBillEntity cstNptsDcrBill = cstNptsDcrBillService.getById(id);

        return R.ok().put("cstNptsDcrBill", cstNptsDcrBill);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cst:nptsdcrbill:save")
    public R save(@RequestBody CstNptsDcrBillEntity cstNptsDcrBill){
		cstNptsDcrBillService.save(cstNptsDcrBill);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cst:nptsdcrbill:update")
    public R update(@RequestBody CstNptsDcrBillEntity cstNptsDcrBill){
		cstNptsDcrBillService.updateById(cstNptsDcrBill);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cst:nptsdcrbill:delete")
    public R delete(@RequestBody Long[] ids){
		cstNptsDcrBillService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
