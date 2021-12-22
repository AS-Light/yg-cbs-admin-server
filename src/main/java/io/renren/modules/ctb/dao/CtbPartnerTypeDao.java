package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbPartnerTypeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ctb_partner的type表 ，同一个partner可以有多种类型
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbPartnerTypeDao extends BaseMapper<CtbPartnerTypeEntity> {
    List<CtbPartnerTypeEntity> listByPartnerId(Long partnerId);
}
