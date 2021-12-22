package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsMoneyTypeDao;
import io.renren.modules.cbs.entity.CbsMoneyTypeEntity;
import io.renren.modules.cbs.service.CbsMoneyTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("cbsMoneyTypeService")
public class CbsMoneyTypeServiceImpl extends ServiceImpl<CbsMoneyTypeDao, CbsMoneyTypeEntity> implements CbsMoneyTypeService {

    private CbsMoneyTypeDao cbsMoneyTypeDao;

    public CbsMoneyTypeServiceImpl(CbsMoneyTypeDao cbsMoneyTypeDao) {
        this.cbsMoneyTypeDao = cbsMoneyTypeDao;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsMoneyTypeEntity> page = this.page(
                new Query<CbsMoneyTypeEntity>().getPage(params),
                new QueryWrapper<CbsMoneyTypeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CbsMoneyTypeEntity> includeSelfBuilt() {
        return cbsMoneyTypeDao.includeSelfBuilt();
    }
}