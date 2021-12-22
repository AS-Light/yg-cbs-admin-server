package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsDirectoryCustomsBrokerEntity;

import java.util.List;

/**
 * 报关行名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
public interface CbsDirectoryCustomsBrokerService extends IService<CbsDirectoryCustomsBrokerEntity> {

    PageUtils queryPage(CbsDirectoryCustomsBrokerEntity entity);

    List<CbsDirectoryCustomsBrokerEntity> listBound();
}

