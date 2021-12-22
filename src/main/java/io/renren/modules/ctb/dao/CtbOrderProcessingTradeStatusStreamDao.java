package io.renren.modules.ctb.dao;

import io.renren.modules.ctb.entity.CtbOrderProcessingTradeStatusStreamEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 加贸状态变更表（操作跟踪）
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbOrderProcessingTradeStatusStreamDao extends BaseMapper<CtbOrderProcessingTradeStatusStreamEntity> {
	
}
