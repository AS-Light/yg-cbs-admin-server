package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsMoneyInEntity;

/**
 * 出口单产生的收入流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
public interface CbsMoneyInService extends IService<CbsMoneyInEntity> {

    PageUtils queryIndex(CbsMoneyInEntity entity);

    CbsMoneyInEntity getMapById(Long id);

    CbsMoneyInEntity simpleDetailById(Long id);

    void updateMoneyImg(CbsMoneyInEntity entity);

    void confirmAnnex(CbsMoneyInEntity entity);

    PageUtils financeList(CbsMoneyInEntity entity);
}

