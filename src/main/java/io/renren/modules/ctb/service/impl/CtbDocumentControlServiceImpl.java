package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.ctb.dao.CtbDocumentControlDao;
import io.renren.modules.ctb.entity.CtbDocumentControlEntity;
import io.renren.modules.ctb.service.CtbDocumentControlService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("ctbDocumentControlService")
public class CtbDocumentControlServiceImpl extends ServiceImpl<CtbDocumentControlDao, CtbDocumentControlEntity> implements CtbDocumentControlService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbDocumentControlEntity> page = this.page(
                new Query<CtbDocumentControlEntity>().getPage(params),
                new QueryWrapper<CtbDocumentControlEntity>()
        );

        return new PageUtils(page);
    }

}