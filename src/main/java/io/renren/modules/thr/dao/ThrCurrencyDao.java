package io.renren.modules.thr.dao;

import io.renren.modules.thr.entity.ThrCurrencyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 16:55:11
 */
@Mapper
@Repository
public interface ThrCurrencyDao extends BaseMapper<ThrCurrencyEntity> {
    void updateRateWithList(List<ThrCurrencyEntity> list);

    ThrCurrencyEntity selectByCode(String code);
}
