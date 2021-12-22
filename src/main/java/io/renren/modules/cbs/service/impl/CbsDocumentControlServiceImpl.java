package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsDocumentControlDao;
import io.renren.modules.cbs.entity.CbsContractEntity;
import io.renren.modules.cbs.entity.CbsDocumentControlEntity;
import io.renren.modules.cbs.service.CbsDocumentControlService;
import org.springframework.stereotype.Service;


@Service("cbsDocumentControlService")
public class CbsDocumentControlServiceImpl extends ServiceImpl<CbsDocumentControlDao, CbsDocumentControlEntity> implements CbsDocumentControlService {

    private CbsDocumentControlDao cbsDocumentControlDao;

    public CbsDocumentControlServiceImpl(CbsDocumentControlDao cbsDocumentControlDao) {
        this.cbsDocumentControlDao = cbsDocumentControlDao;
    }

    @Override
    public PageUtils queryPage(CbsDocumentControlEntity entity) {
        IPage<CbsDocumentControlEntity> page = cbsDocumentControlDao.queryIndex(new QueryPage<CbsDocumentControlEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

}