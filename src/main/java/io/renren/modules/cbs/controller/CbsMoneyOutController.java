package io.renren.modules.cbs.controller;

import io.renren.common.enumeration.MoneyEnum;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.cbs.entity.CbsMoneyOutEntity;
import io.renren.modules.cbs.entity.CbsMoneyTypeOneselfEntity;
import io.renren.modules.cbs.service.CbsMoneyOutService;
import io.renren.modules.cbs.service.CbsMoneyTypeOneselfService;
import io.renren.modules.org_cbs.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 进口单产生的支出流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
@RestController
@RequestMapping("cbs/moneyout")
public class CbsMoneyOutController extends AbstractController {
    private final CbsMoneyOutService cbsMoneyOutService;
    private final CbsMoneyTypeOneselfService cbsMoneyTypeOneselfService;

    public CbsMoneyOutController(CbsMoneyOutService cbsMoneyOutService, CbsMoneyTypeOneselfService cbsMoneyTypeOneselfService) {
        this.cbsMoneyOutService = cbsMoneyOutService;
        this.cbsMoneyTypeOneselfService = cbsMoneyTypeOneselfService;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cbs:moneyout:list")
    public R list(@RequestBody CbsMoneyOutEntity entity) {
        PageUtils page = cbsMoneyOutService.queryIndex(entity);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cbs:moneyout:info")
    public R info(@PathVariable("id") Long id) {
        CbsMoneyOutEntity cbsMoneyOut = cbsMoneyOutService.getMapById(id);

        return R.ok().put("cbsMoneyOut", cbsMoneyOut);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cbs:moneyout:save")
    public R save(@RequestBody CbsMoneyOutEntity entity) {
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
        cbsMoneyOutService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cbs:moneyout:update")
    public R update(@RequestBody CbsMoneyOutEntity entity) {
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
        cbsMoneyOutService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cbs:moneyout:delete")
    public R delete(@RequestBody Long[] ids) {
        for (Long id : ids) {
            if (id != MoneyEnum.CONFIRM_STATUS_1.getCode()) {
                throw new RRException("状态不符合要求");
            }
        }
        cbsMoneyOutService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 确核
     */
    @RequestMapping("/confirm")
    @RequiresPermissions("cbs:moneyout:confirm")
    public R confirm(@RequestBody Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        List<CbsMoneyOutEntity> list = new ArrayList<>();
        idList.forEach(id -> {
            CbsMoneyOutEntity entity = cbsMoneyOutService.simpleDetailById(id);
            BigDecimal cny = entity.getCurrencyEntity().getExchangeRate().multiply(entity.getMoney()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            list.add(CbsMoneyOutEntity.builder().id(id).status(MoneyEnum.CONFIRM_STATUS_2.getCode()).cny(cny).build());
        });
        cbsMoneyOutService.updateBatchById(list);
        return R.ok();
    }

    /**
     * 获取自编辑类型
     */
    @RequestMapping("/selfEditingType")
    @RequiresPermissions("cbs:moneyout:selfEditingType")
    public R selfEditingType(@RequestBody CbsMoneyTypeOneselfEntity entity) {
        PageUtils page = cbsMoneyTypeOneselfService.queryPage(entity);
        return R.ok().put("page", page);
    }

    /**
     * 补充附件
     */
    @PostMapping("/updateMoneyImg")
    @RequiresPermissions("cbs:moneyout:updateMoneyImg")
    public R updateMoneyImg(@RequestBody CbsMoneyOutEntity entity) {
        cbsMoneyOutService.updateMoneyImg(entity);
        return R.ok();
    }

    /**
     * 确核附件
     */
    @PostMapping("/confirmAnnex")
    @RequiresPermissions("cbs:moneyout:confirmAnnex")
    public R confirmAnnex(@RequestBody CbsMoneyOutEntity entity) {
        cbsMoneyOutService.confirmAnnex(entity);
        return R.ok();
    }
}
