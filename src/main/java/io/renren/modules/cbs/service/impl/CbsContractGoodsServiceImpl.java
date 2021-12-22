package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsContractGoodsDao;
import io.renren.modules.cbs.entity.CbsContractGoodsEntity;
import io.renren.modules.cbs.service.CbsContractGoodsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsContractGoodsService")
public class CbsContractGoodsServiceImpl extends ServiceImpl<CbsContractGoodsDao, CbsContractGoodsEntity> implements CbsContractGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsContractGoodsEntity> page = this.page(
                new Query<CbsContractGoodsEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public CbsContractGoodsEntity detail(Long id) {
        return baseMapper.detail(id);
    }

}