package io.renren.modules.cbs.controller;

import io.renren.common.enumeration.MoneyEnum;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsMoneyInEntity;
import io.renren.modules.cbs.entity.CbsMoneyTypeOneselfEntity;
import io.renren.modules.cbs.service.CbsMoneyInService;
import io.renren.modules.cbs.service.CbsMoneyTypeOneselfService;
import io.renren.modules.org_cbs.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 出口单产生的收入流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
@RestController
@RequestMapping("cbs/moneyin")
public class CbsMoneyInController extends AbstractController {

    private final CbsMoneyInService cbsMoneyInService;
    private final CbsMoneyTypeOneselfService cbsMoneyTypeOneselfService;

    public CbsMoneyInController(CbsMoneyInService cbsMoneyInService, CbsMoneyTypeOneselfService cbsMoneyTypeOneselfService) {
        this.cbsMoneyInService = cbsMoneyInService;
        this.cbsMoneyTypeOneselfService = cbsMoneyTypeOneselfService;
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:moneyin:list")
    public R list(@RequestBody CbsMoneyInEntity entity) {
        PageUtils page = cbsMoneyInService.queryIndex(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:moneyin:info")
    public R info(@PathVariable("id") Long id) {
        CbsMoneyInEntity cbsMoneyIn = cbsMoneyInService.getMapById(id);
        return R.ok().put("cbsMoneyIn", cbsMoneyIn);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:moneyin:save")
    public R save(@RequestBody CbsMoneyInEntity entity) {
        entity.setIsBuildOneself(true);
        entity.setOperator(getUserId());
        CbsMoneyTypeOneselfEntity item = entity.getSelfEditingType();
        if (item != null) {
            if (item.getId() == null) {
                // 创建自编辑类型返回ID
                cbsMoneyTypeOneselfService.save(item);
            }
            entity.setFkTypeOneselfId(item.getId());
        }
        cbsMoneyInService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:moneyin:update")
    public R update(@RequestBody CbsMoneyInEntity entity) {
        entity.setIsBuildOneself(true);
        entity.setOperator(getUserId());
        CbsMoneyTypeOneselfEntity item = entity.getSelfEditingType();
        if (item != null) {
            if (item.getId() == null) {
                // 创建自编辑类型返回ID
                cbsMoneyTypeOneselfService.save(item);
            }
            entity.setFkTypeOneselfId(item.getId());
        }
        cbsMoneyInService.updateById(entity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:moneyin:delete")
    public R delete(@RequestBody Long[] ids) {
        for (Long id : ids) {
            if (id != MoneyEnum.CONFIRM_STATUS_1.getCode()) {
                throw new RRException("状态不符合要求");
            }
        }
        cbsMoneyInService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 确核
     */
    @RequestMapping("/confirm")
    @RequiresPermissions("cbs:moneyin:confirm")
    public R confirm(@RequestBody Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        List<CbsMoneyInEntity> list = new ArrayList<>();
        idList.forEach(id -> {
            CbsMoneyInEntity entity = cbsMoneyInService.simpleDetailById(id);
            BigDecimal cny = entity.getCurrencyEntity().getExchangeRate().multiply(entity.getMoney()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            list.add(CbsMoneyInEntity.builder().id(id).status(MoneyEnum.CONFIRM_STATUS_2.getCode()).cny(cny).build());
        });
        cbsMoneyInService.updateBatchById(list);
        return R.ok();
    }

    /**
     * 获取自编辑类型
     */
    @RequestMapping("/selfEditingType")
    @RequiresPermissions("cbs:moneyin:selfEditingType")
    public R selfEditingType(@RequestBody CbsMoneyTypeOneselfEntity entity) {
        PageUtils page = cbsMoneyTypeOneselfService.queryPage(entity);
        return R.ok().put("page", page);
    }

    /**
     * 补充附件
     */
    @PostMapping("/updateMoneyImg")
    @RequiresPermissions("cbs:moneyin:updateMoneyImg")
    public R updateMoneyImg(@RequestBody CbsMoneyInEntity entity) {
        cbsMoneyInService.updateMoneyImg(entity);
        return R.ok();
    }

    /**
     * 确核附件
     */
    @PostMapping("/confirmAnnex")
    @RequiresPermissions("cbs:moneyin:confirmAnnex")
    public R confirmAnnex(@RequestBody CbsMoneyInEntity entity) {
        cbsMoneyInService.confirmAnnex(entity);
        return R.ok();
    }


    /**
     * 财务收入支出总列表
     */
    @RequestMapping("/financeList")
    @RequiresPermissions("cbs:moneyin:financeList")
    public R financeList(@RequestBody CbsMoneyInEntity entity) {
        PageUtils page = cbsMoneyInService.financeList(entity);
        return R.ok().put("page", page);
    }
}
