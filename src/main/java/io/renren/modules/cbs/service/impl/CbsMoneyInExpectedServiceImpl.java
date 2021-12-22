package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsMoneyInExpectedDao;
import io.renren.modules.cbs.entity.CbsMoneyInExpectedEntity;
import io.renren.modules.cbs.service.CbsMoneyInExpectedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("cbsMoneyInExpectedService")
public class CbsMoneyInExpectedServiceImpl extends ServiceImpl<CbsMoneyInExpectedDao, CbsMoneyInExpectedEntity> implements CbsMoneyInExpectedService {
    private CbsMoneyInExpectedDao cbsMoneyInExpected;

    @Autowired
    public void setCbsMoneyInExpected(CbsMoneyInExpectedDao cbsMoneyInExpected) {
        this.cbsMoneyInExpected = cbsMoneyInExpected;
    }

    @Override
    public PageUtils queryPage(CbsMoneyInExpectedEntity entity) {
//        IPage<CbsMoneyInExpectedEntity> page = new QueryPage<CbsMoneyInExpectedEntity>().getPage(entity);
//        page.setRecords(cbsMoneyInExpected.queryIndex(page, entity));
        IPage<CbsMoneyInExpectedEntity> page = cbsMoneyInExpected.queryIndex(new QueryPage<CbsMoneyInExpectedEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

}
