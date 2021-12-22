package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsProductionProcessEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 生产工艺表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-28 15:32:13
 */
@Mapper
public interface CbsProductionProcessDao extends BaseMapper<CbsProductionProcessEntity> {
    CbsProductionProcessEntity detail(Long id);
}
