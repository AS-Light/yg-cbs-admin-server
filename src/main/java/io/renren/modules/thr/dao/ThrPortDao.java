package io.renren.modules.thr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.thr.entity.ThrPortEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 国际口岸（亦表示：港口、启运口岸、经停口岸、国际抵达口岸等）
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@Mapper
public interface ThrPortDao extends BaseMapper<ThrPortEntity> {
    ThrPortEntity selectByCode(String code);
}
