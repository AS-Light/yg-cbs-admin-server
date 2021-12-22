package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsMoneyTypeOneselfDao;
import io.renren.modules.cbs.entity.CbsMoneyTypeOneselfEntity;
import io.renren.modules.cbs.service.CbsMoneyTypeOneselfService;
import org.springframework.stereotype.Service;


@Service("cbsMoneyTypeOneselfService")
public class CbsMoneyTypeOneselfServiceImpl extends ServiceImpl<CbsMoneyTypeOneselfDao, CbsMoneyTypeOneselfEntity> implements CbsMoneyTypeOneselfService {

    private final CbsMoneyTypeOneselfDao cbsMoneyTypeOneselfDao;

    public CbsMoneyTypeOneselfServiceImpl(CbsMoneyTypeOneselfDao cbsMoneyTypeOneselfDao) {
        this.cbsMoneyTypeOneselfDao = cbsMoneyTypeOneselfDao;
    }

    @Override
    public PageUtils queryPage(CbsMoneyTypeOneselfEntity entity) {
        QueryWrapper<CbsMoneyTypeOneselfEntity> qw = new QueryWrapper<>();
        qw.eq(entity.getIo() != null, "io", entity.getIo());
        IPage<CbsMoneyTypeOneselfEntity> page = cbsMoneyTypeOneselfDao.selectPage(new QueryPage<CbsMoneyTypeOneselfEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

}