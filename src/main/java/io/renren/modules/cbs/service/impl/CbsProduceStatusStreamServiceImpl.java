package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.cbs.dao.CbsProduceStatusStreamDao;
import io.renren.modules.cbs.entity.CbsProduceStatusStreamEntity;
import io.renren.modules.cbs.service.CbsProduceStatusStreamService;
import org.springframework.stereotype.Service;


@Service("cbsProduceStatusStreamService")
public class CbsProduceStatusStreamServiceImpl extends ServiceImpl<CbsProduceStatusStreamDao, CbsProduceStatusStreamEntity> implements CbsProduceStatusStreamService {

}