package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.QueryPage;
import io.renren.modules.bind.dao.BindCbsCustomsBrokerCtbCompanyDao;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.renren.modules.ctb.dao.CtbDirectoryServiceCompanyDao;
import io.renren.modules.ctb.entity.CtbDirectoryServiceCompanyEntity;
import io.renren.modules.ctb.service.CtbDirectoryServiceCompanyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("ctbDirectoryServiceCompanyService")
public class CtbDirectoryServiceCompanyServiceImpl extends ServiceImpl<CtbDirectoryServiceCompanyDao, CtbDirectoryServiceCompanyEntity> implements CtbDirectoryServiceCompanyService {

    private CtbDirectoryServiceCompanyDao ctbDirectoryServiceCompanyDao;
    private BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao;

    public CtbDirectoryServiceCompanyServiceImpl(
            CtbDirectoryServiceCompanyDao ctbDirectoryServiceCompanyDao,
            BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao
    ) {
        this.ctbDirectoryServiceCompanyDao = ctbDirectoryServiceCompanyDao;
        this.bindCbsCustomsBrokerCtbCompanyDao = bindCbsCustomsBrokerCtbCompanyDao;
    }

    @Override
    public PageUtils queryIndex(CtbDirectoryServiceCompanyEntity entity) {
        QueryWrapper<CtbDirectoryServiceCompanyEntity> qw = new QueryWrapper<>();
        qw.like(entity.getName() != null, "name", entity.getName());
        IPage page = this.page(new QueryPage<CtbDirectoryServiceCompanyEntity>().getPage(entity), qw);
        List<CtbDirectoryServiceCompanyEntity> list = page.getRecords();
        for (CtbDirectoryServiceCompanyEntity c : list) {
            QueryWrapper<BindCbsCustomsBrokerCtbCompanyEntity> bindQw = new QueryWrapper<>();
            bindQw.eq("fk_ctb_service_company_id", c.getId());
            bindQw.eq("fk_ctb_company_id", entity.getCtbTenantId());
            BindCbsCustomsBrokerCtbCompanyEntity bind = bindCbsCustomsBrokerCtbCompanyDao.selectOne(bindQw);
            c.setBind(bind);
        }
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public List<CtbDirectoryServiceCompanyEntity> listForChoice(CtbDirectoryServiceCompanyEntity entity) {
        QueryWrapper<CtbDirectoryServiceCompanyEntity> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(entity.getName()), "name", entity.getName());
        List<CtbDirectoryServiceCompanyEntity> list = baseMapper.selectList(qw);
        for (CtbDirectoryServiceCompanyEntity serviceCompanyEntity : list) {
            QueryWrapper<BindCbsCustomsBrokerCtbCompanyEntity> bindQw = new QueryWrapper<>();
            bindQw.eq("fk_ctb_service_company_id", serviceCompanyEntity.getId());
            bindQw.eq("fk_ctb_company_id", entity.getCtbTenantId());
            BindCbsCustomsBrokerCtbCompanyEntity bind = bindCbsCustomsBrokerCtbCompanyDao.selectOne(bindQw);
            serviceCompanyEntity.setBind(bind);
        }
        return list;
    }

    @Override
    public List<CtbDirectoryServiceCompanyEntity> selfEditingList(CtbDirectoryServiceCompanyEntity entity) {
        return ctbDirectoryServiceCompanyDao.selfEditingList(entity);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbDirectoryServiceCompanyEntity> page = this.page(
                new Query<CtbDirectoryServiceCompanyEntity>().getPage(params),
                new QueryWrapper<CtbDirectoryServiceCompanyEntity>()
        );

        return new PageUtils(page);
    }

}