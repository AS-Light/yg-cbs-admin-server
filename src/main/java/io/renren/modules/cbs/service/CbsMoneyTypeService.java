package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsMoneyTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 收入/支出类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
public interface CbsMoneyTypeService extends IService<CbsMoneyTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CbsMoneyTypeEntity> includeSelfBuilt();
}

