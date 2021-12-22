package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstDecRequestCertDao;
import io.renren.modules.cst.entity.CstDecRequestCertEntity;
import io.renren.modules.cst.service.CstDecRequestCertService;


@Service("cstDecRequestCertService")
public class CstDecRequestCertServiceImpl extends ServiceImpl<CstDecRequestCertDao, CstDecRequestCertEntity> implements CstDecRequestCertService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstDecRequestCertEntity> page = this.page(
                new Query<CstDecRequestCertEntity>().getPage(params),
                new QueryWrapper<CstDecRequestCertEntity>()
        );

        return new PageUtils(page);
    }

}