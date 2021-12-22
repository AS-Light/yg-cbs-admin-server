package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsPartnerTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * cbs_partner的type表 ，同一个partner可以有多种类型
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-25 11:46:38
 */
@Mapper
@Repository
public interface CbsPartnerTypeDao extends BaseMapper<CbsPartnerTypeEntity> {
	List<CbsPartnerTypeEntity> listByPartnerId(Long partnerId);
}
