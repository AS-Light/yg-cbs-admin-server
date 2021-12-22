package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsContractProcessingRecordEntity;

/**
 * （合同）海关（手册）备案表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
public interface CbsContractProcessingRecordService extends IService<CbsContractProcessingRecordEntity> {

    PageUtils queryPage(CbsContractProcessingRecordEntity entity);

    CbsContractProcessingRecordEntity selectById(Long id);

    CbsContractProcessingRecordEntity selectByContractId(Long id);
}

