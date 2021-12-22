package io.renren.modules.cst.dao;

import io.renren.modules.cst.entity.CstInvtHeaderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 保税核注清单表头
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@Mapper
@Repository
public interface CstInvtHeaderDao extends BaseMapper<CstInvtHeaderEntity> {
	
}
