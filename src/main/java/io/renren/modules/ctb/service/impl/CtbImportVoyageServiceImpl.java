package io.renren.modules.ctb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ctb.dao.CtbImportVoyageDao;
import io.renren.modules.ctb.entity.CtbImportVoyageEntity;
import io.renren.modules.ctb.service.CtbImportVoyageService;


@Service("ctbImportVoyageService")
public class CtbImportVoyageServiceImpl extends ServiceImpl<CtbImportVoyageDao, CtbImportVoyageEntity> implements CtbImportVoyageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbImportVoyageEntity> page = this.page(
                new Query<CtbImportVoyageEntity>().getPage(params),
                new QueryWrapper<CtbImportVoyageEntity>()
        );

        return new PageUtils(page);
    }

}