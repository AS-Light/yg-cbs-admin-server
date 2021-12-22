package io.renren.modules.cst.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.cst.dao.CstNptsDcrBillDao;
import io.renren.modules.cst.entity.CstNptsDcrBillEntity;
import io.renren.modules.cst.service.CstNptsDcrBillService;


@Service("cstNptsDcrBillService")
public class CstNptsDcrBillServiceImpl extends ServiceImpl<CstNptsDcrBillDao, CstNptsDcrBillEntity> implements CstNptsDcrBillService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstNptsDcrBillEntity> page = this.page(
                new Query<CstNptsDcrBillEntity>().getPage(params),
                new QueryWrapper<CstNptsDcrBillEntity>()
        );

        return new PageUtils(page);
    }

}