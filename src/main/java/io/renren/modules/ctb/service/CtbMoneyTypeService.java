package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.ctb.entity.CtbMoneyTypeEntity;

import java.util.List;

/**
 * 收入/支出类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbMoneyTypeService extends IService<CtbMoneyTypeEntity> {

    List<CtbMoneyTypeEntity> queryIndex(CtbMoneyTypeEntity entity);

    List<CtbMoneyTypeEntity> listByServiceCompanyId(Long serviceCompanyId);

    List<CtbMoneyTypeEntity> listDef();
}

