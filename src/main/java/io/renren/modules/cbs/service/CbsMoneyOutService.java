package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsMoneyOutEntity;

/**
 * 进口单产生的支出流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
public interface CbsMoneyOutService extends IService<CbsMoneyOutEntity> {

    PageUtils queryIndex(CbsMoneyOutEntity entity);

    CbsMoneyOutEntity getMapById(Long id);

    CbsMoneyOutEntity simpleDetailById(Long id);

    void updateMoneyImg(CbsMoneyOutEntity entity);

    void confirmAnnex(CbsMoneyOutEntity entity);
}

