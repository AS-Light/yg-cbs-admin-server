package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbExportVoyageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 进口航次变更记录，生产报关以最后一次为准
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbExportVoyageDao extends BaseMapper<CtbExportVoyageEntity> {
    List<CtbExportVoyageEntity> listByExportId(Long exportId);

    CtbExportVoyageEntity simpleDetail(Long id);
}
