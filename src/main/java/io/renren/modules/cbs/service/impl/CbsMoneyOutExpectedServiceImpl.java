package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsMoneyOutExpectedDao;
import io.renren.modules.cbs.entity.CbsMoneyOutExpectedEntity;
import io.renren.modules.cbs.service.CbsMoneyOutExpectedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("cbsMoneyOutExpectedService")
public class CbsMoneyOutExpectedServiceImpl extends ServiceImpl<CbsMoneyOutExpectedDao, CbsMoneyOutExpectedEntity> implements CbsMoneyOutExpectedService {
    private CbsMoneyOutExpectedDao cbsMoneyOutExpectedDao;

    @Autowired
    public void setCbsMoneyOutExpectedDao(CbsMoneyOutExpectedDao cbsMoneyOutExpectedDao) {
        this.cbsMoneyOutExpectedDao = cbsMoneyOutExpectedDao;
    }

    @Override
    public PageUtils queryPage(CbsMoneyOutExpectedEntity entity) {
        IPage<CbsMoneyOutExpectedEntity> page = cbsMoneyOutExpectedDao.queryIndex(new QueryPage<CbsMoneyOutExpectedEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

}
