package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.thr.entity.ThrCurrencyEntity;

import java.util.List;

/**
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 16:55:11
 */
public interface ThrCurrencyService extends IService<ThrCurrencyEntity> {

    List<ThrCurrencyEntity> queryIndex(ThrCurrencyEntity params);

    void updateRateWithList(List<ThrCurrencyEntity> list);

    ThrCurrencyEntity getOneByCode(ThrCurrencyEntity entity);
}

