package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsDirectoryProduceCompanyEntity;

/**
 * 生产公司名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
public interface CbsDirectoryProduceCompanyService extends IService<CbsDirectoryProduceCompanyEntity> {
    PageUtils queryPage(CbsDirectoryProduceCompanyEntity entity);
}

