package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsProductionProcessStepEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 生产工艺步骤表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-28 15:32:13
 */
@Mapper
public interface CbsProductionProcessStepDao extends BaseMapper<CbsProductionProcessStepEntity> {
    List<CbsProductionProcessStepEntity> listByProductionProcessId(Long productionProcessId);
}
