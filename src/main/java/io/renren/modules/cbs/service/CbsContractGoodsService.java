package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsContractGoodsEntity;

import java.util.Map;

/**
 * 原材料名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 10:19:45
 */
public interface CbsContractGoodsService extends IService<CbsContractGoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    CbsContractGoodsEntity detail(Long id);
}

