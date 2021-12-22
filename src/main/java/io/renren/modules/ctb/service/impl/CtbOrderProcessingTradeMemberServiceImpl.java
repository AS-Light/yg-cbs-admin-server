package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.ctb.dao.CtbOrderProcessingTradeMemberDao;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeMemberEntity;
import io.renren.modules.ctb.service.CtbOrderProcessingTradeMemberService;
import org.springframework.stereotype.Service;


@Service("ctbOrderProcessingTradeMemberService")
public class CtbOrderProcessingTradeMemberServiceImpl extends ServiceImpl<CtbOrderProcessingTradeMemberDao, CtbOrderProcessingTradeMemberEntity> implements CtbOrderProcessingTradeMemberService {

    @Override
    public CtbOrderProcessingTradeMemberEntity detail(Long id) {
        return getBaseMapper().detail(id);
    }

}