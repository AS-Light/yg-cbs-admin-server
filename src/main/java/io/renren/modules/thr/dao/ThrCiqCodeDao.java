package io.renren.modules.thr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.thr.entity.ThrCiqCodeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 14:42:22
 */
@Mapper
public interface ThrCiqCodeDao extends BaseMapper<ThrCiqCodeEntity> {
    List<ThrCiqCodeEntity> listByHsCode(String hsCode);
}
