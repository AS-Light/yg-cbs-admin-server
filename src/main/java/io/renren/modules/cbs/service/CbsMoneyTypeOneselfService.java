package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsMoneyTypeOneselfEntity;

import java.util.Map;

/**
 * 收入/支出类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-09 10:52:57
 */
public interface CbsMoneyTypeOneselfService extends IService<CbsMoneyTypeOneselfEntity> {

    PageUtils queryPage(CbsMoneyTypeOneselfEntity params);
}

