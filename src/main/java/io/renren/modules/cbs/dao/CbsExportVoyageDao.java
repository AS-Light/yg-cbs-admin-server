package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsExportVoyageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 出口航次（变更）表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-18 15:18:27
 */
@Mapper
@Repository
public interface CbsExportVoyageDao extends BaseMapper<CbsExportVoyageEntity> {
    List<CbsExportVoyageEntity> listByExportId(Long exportId);

    CbsExportVoyageEntity simpleDetail(Long id);
}
