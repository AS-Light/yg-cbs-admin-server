package io.renren.modules.thr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.thr.entity.ThrHsCodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 14:42:21
 */
@Mapper
public interface ThrHsCodeDao extends BaseMapper<ThrHsCodeEntity> {
    IPage<ThrHsCodeEntity> pageByCodeOrName(IPage<ThrHsCodeEntity> page, @Param("params") ThrHsCodeEntity params);

    ThrHsCodeEntity selectByHsCode(String hsCode);
}
