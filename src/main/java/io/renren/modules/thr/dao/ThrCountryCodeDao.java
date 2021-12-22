package io.renren.modules.thr.dao;

import io.renren.modules.thr.entity.ThrCountryCodeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-21 15:45:17
 */
@Mapper
public interface ThrCountryCodeDao extends BaseMapper<ThrCountryCodeEntity> {
    ThrCountryCodeEntity selectByCode(String code);
}
