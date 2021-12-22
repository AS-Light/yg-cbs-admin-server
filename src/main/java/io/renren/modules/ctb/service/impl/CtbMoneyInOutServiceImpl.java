package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.ctb.dao.CtbMoneyInOutDao;
import io.renren.modules.ctb.entity.CtbMoneyInOutEntity;
import io.renren.modules.ctb.service.CtbMoneyInOutService;
import org.springframework.stereotype.Service;


@Service("ctbMoneyInOutService")
public class CtbMoneyInOutServiceImpl extends ServiceImpl<CtbMoneyInOutDao, CtbMoneyInOutEntity> implements CtbMoneyInOutService {

}