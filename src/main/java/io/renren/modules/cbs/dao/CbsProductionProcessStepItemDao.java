package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsProductionProcessStepItemEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * cbs_produce_goods_stream 的子表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-28 15:32:13
 */
@Mapper
public interface CbsProductionProcessStepItemDao extends BaseMapper<CbsProductionProcessStepItemEntity> {
    List<CbsProductionProcessStepItemEntity> listByStepId(Long stepId);
}
