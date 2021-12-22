package io.renren.modules.thr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.thr.dao.ThrHsCodeDao;
import io.renren.modules.thr.entity.ThrHsCodeEntity;
import io.renren.modules.thr.service.ThrHsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("thrHsCodeService")
public class ThrHsCodeServiceImpl extends ServiceImpl<ThrHsCodeDao, ThrHsCodeEntity> implements ThrHsCodeService {

    ThrHsCodeDao hsCodeDao;

    @Autowired
    public void setHsCodeDao(ThrHsCodeDao hsCodeDao) {
        this.hsCodeDao = hsCodeDao;
    }

    @Override
    public PageUtils queryIndex(ThrHsCodeEntity entity) {
        QueryWrapper<ThrHsCodeEntity> qw = new QueryWrapper<>();
        qw.like(entity.getHsCode() != null, "hs_code", entity.getHsCode());
        qw.or().like(entity.getName() != null, "name", entity.getName());
        IPage<ThrHsCodeEntity> page = this.page(new QueryPage<ThrHsCodeEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    public ThrHsCodeEntity getOneByCode(ThrHsCodeEntity entity) {
        return hsCodeDao.selectByHsCode(entity.getHsCode());
    }
}