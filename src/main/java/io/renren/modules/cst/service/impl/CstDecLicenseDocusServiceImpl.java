package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstDecLicenseDocusDao;
import io.renren.modules.cst.entity.CstDecLicenseDocusEntity;
import io.renren.modules.cst.service.CstDecLicenseDocusService;


@Service("cstDecLicenseDocusService")
public class CstDecLicenseDocusServiceImpl extends ServiceImpl<CstDecLicenseDocusDao, CstDecLicenseDocusEntity> implements CstDecLicenseDocusService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstDecLicenseDocusEntity> page = this.page(
                new Query<CstDecLicenseDocusEntity>().getPage(params),
                new QueryWrapper<CstDecLicenseDocusEntity>()
        );

        return new PageUtils(page);
    }

}