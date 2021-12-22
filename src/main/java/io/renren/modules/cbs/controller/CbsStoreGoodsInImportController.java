package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsStoreGoodsInImportEntity;
import io.renren.modules.cbs.service.CbsStoreGoodsInImportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 进口入库表，与cbs_store_goods_in表为1对1继承关系
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@RestController
@RequestMapping("cbs/storegoodsinimport")
public class CbsStoreGoodsInImportController {

    private CbsStoreGoodsInImportService cbsStoreGoodsInImportService;

    @Autowired
    public void setCbsStoreGoodsInImportService(CbsStoreGoodsInImportService cbsStoreGoodsInImportService) {
        this.cbsStoreGoodsInImportService = cbsStoreGoodsInImportService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:storegoodsinimport:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsStoreGoodsInImportService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:storegoodsinimport:info")
    public R info(@PathVariable("id") Long id) {
        CbsStoreGoodsInImportEntity cbsStoreGoodsInImport = cbsStoreGoodsInImportService.getById(id);

        return R.ok().put("cbsStoreGoodsInImport", cbsStoreGoodsInImport);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:storegoodsinimport:save")
    public R save(@RequestBody CbsStoreGoodsInImportEntity cbsStoreGoodsInImport) {
        cbsStoreGoodsInImportService.save(cbsStoreGoodsInImport);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:storegoodsinimport:update")
    public R update(@RequestBody CbsStoreGoodsInImportEntity cbsStoreGoodsInImport) {
        cbsStoreGoodsInImportService.updateById(cbsStoreGoodsInImport);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:storegoodsinimport:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsStoreGoodsInImportService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
