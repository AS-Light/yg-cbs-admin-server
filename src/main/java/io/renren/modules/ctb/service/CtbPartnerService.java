package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbPartnerEntity;

import java.util.List;
import java.util.Map;

/**
 * 交易对象（公司）名录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
public interface CtbPartnerService extends IService<CtbPartnerEntity> {

    Long saveReturnId(CtbPartnerEntity entity);

    void update(CtbPartnerEntity entity);

    PageUtils queryPage(Map<String, Object> params);

    List<CtbPartnerEntity> listByTypes(List<Integer> type, String name);

    CtbPartnerEntity detail(Long id);

    CtbPartnerEntity detailByCode(String code);
}

