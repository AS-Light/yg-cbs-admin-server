package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsPartnerEntity;

import java.util.List;
import java.util.Map;

/**
 * 交易对象（公司）名录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-25 11:46:38
 */
public interface CbsPartnerService extends IService<CbsPartnerEntity> {

    Long saveReturnId(CbsPartnerEntity entity);

    void update(CbsPartnerEntity entity);

    PageUtils queryPage(Map<String, Object> params);

    List<CbsPartnerEntity> listByTypes(List<Integer> type, String name);

    CbsPartnerEntity detail(Long id);

    CbsPartnerEntity detailByCode(String code);
}

