package io.renren.modules.thr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.thr.entity.ThrDeclPortEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 国内关区（关别）代码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@Mapper
public interface ThrDeclPortDao extends BaseMapper<ThrDeclPortEntity> {
    ThrDeclPortEntity selectByCode(String code);
}
