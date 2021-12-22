package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsDirectoryCustomsBrokerDao;
import io.renren.modules.cbs.entity.CbsDirectoryCustomsBrokerEntity;
import io.renren.modules.cbs.service.CbsDirectoryCustomsBrokerService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("cbsDirectoryCustomsBrokerService")
public class CbsDirectoryCustomsBrokerServiceImpl extends ServiceImpl<CbsDirectoryCustomsBrokerDao, CbsDirectoryCustomsBrokerEntity> implements CbsDirectoryCustomsBrokerService {

    private CbsDirectoryCustomsBrokerDao cbsDirectoryCustomsBrokerDao;

    public CbsDirectoryCustomsBrokerServiceImpl(
            CbsDirectoryCustomsBrokerDao cbsDirectoryCustomsBrokerDao
    ) {
        this.cbsDirectoryCustomsBrokerDao = cbsDirectoryCustomsBrokerDao;
    }

    public PageUtils queryPage(CbsDirectoryCustomsBrokerEntity entity) {
        IPage<CbsDirectoryCustomsBrokerEntity> page = cbsDirectoryCustomsBrokerDao.queryIndex(new QueryPage<CbsDirectoryCustomsBrokerEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public List<CbsDirectoryCustomsBrokerEntity> listBound() {
        return cbsDirectoryCustomsBrokerDao.listBound();
    }

}