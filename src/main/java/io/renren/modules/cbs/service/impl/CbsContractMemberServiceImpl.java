package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cbs.dao.CbsContractMemberDao;
import io.renren.modules.cbs.entity.CbsContractMemberEntity;
import io.renren.modules.cbs.service.CbsContractMemberService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cbsContractMemberService")
public class CbsContractMemberServiceImpl extends ServiceImpl<CbsContractMemberDao, CbsContractMemberEntity> implements CbsContractMemberService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CbsContractMemberEntity> page = this.page(
                new Query<CbsContractMemberEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public CbsContractMemberEntity detail(Long id) {
        return getBaseMapper().detail(id);
    }

}