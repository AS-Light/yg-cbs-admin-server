package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsMoneyOutExpectedEntity;

/**
 * 合同审核通过后的预计支出流水
 *
 * @author ChenNing
 * @email record_7@126.com
 * @date 2020-02-18 11:16:49
 */
public interface CbsMoneyOutExpectedService extends IService<CbsMoneyOutExpectedEntity> {

    PageUtils queryPage(CbsMoneyOutExpectedEntity entity);
}

