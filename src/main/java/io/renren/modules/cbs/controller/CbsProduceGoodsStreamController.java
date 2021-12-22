package io.renren.modules.cbs.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsProduceGoodsStreamEntity;
import io.renren.modules.cbs.service.CbsProduceGoodsStreamService;
import io.renren.modules.org_cbs.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 生产流水
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:15:37
 */
@RestController
@RequestMapping("cbs/producegoodsstream")
public class CbsProduceGoodsStreamController extends AbstractController {

    private CbsProduceGoodsStreamService cbsProduceGoodsStreamService;

    @Autowired
    public void setCbsProduceGoodsStreamService(CbsProduceGoodsStreamService cbsProduceGoodsStreamService) {
        this.cbsProduceGoodsStreamService = cbsProduceGoodsStreamService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:produce:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cbsProduceGoodsStreamService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:produce:info")
    public R info(@PathVariable("id") Long id) {
        return R.ok().put("cbsProduceGoodsStream", cbsProduceGoodsStreamService.selectById(id));
    }

    /**
     * 创建前检查是否有未确核生产计划
     */
    @RequestMapping("/preCreate")
    @RequiresPermissions("cbs:produce:save")
    public R preCreate(@RequestBody CbsProduceGoodsStreamEntity cbsProduceGoodsStream) {
        Long id = cbsProduceGoodsStreamService.preCreate(cbsProduceGoodsStream);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 200);
        resultMap.put("id", id);
        return R.ok(resultMap);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:produce:save")
    public R save(@RequestBody CbsProduceGoodsStreamEntity cbsProduceGoodsStream) {
        cbsProduceGoodsStream.setOperator(getUserId());
        cbsProduceGoodsStream.setStatus(1);
        return R.ok(cbsProduceGoodsStreamService.saveOrUpdateWithItems(cbsProduceGoodsStream));
    }

    /**
     * 修改
     */
    @RequestMapping("/check")
    @RequiresPermissions("cbs:produce:check")
    public R check(@RequestBody CbsProduceGoodsStreamEntity cbsProduceGoodsStream) {
        return R.ok(cbsProduceGoodsStreamService.check(cbsProduceGoodsStream.getId()));
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:produce:delete")
    public R delete(@RequestBody Long[] ids) {
        cbsProduceGoodsStreamService.deleteByIds(ids);
        return R.ok();
    }

}
