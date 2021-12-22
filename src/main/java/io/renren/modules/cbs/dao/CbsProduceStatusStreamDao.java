package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsProduceStatusStreamEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 生产计划状态变更表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-06 11:40:38
 */
@Mapper
@Repository
public interface CbsProduceStatusStreamDao extends BaseMapper<CbsProduceStatusStreamEntity> {

}
