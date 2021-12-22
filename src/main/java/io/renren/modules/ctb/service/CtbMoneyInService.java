package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbMoneyInEntity;

/**
 * 出口单产生的收入流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
public interface CtbMoneyInService extends IService<CtbMoneyInEntity> {

    PageUtils queryPage(CtbMoneyInEntity entity);

    void updateMoneyImg(CtbMoneyInEntity entity);

    void confirmAnnex(CtbMoneyInEntity entity);
}

