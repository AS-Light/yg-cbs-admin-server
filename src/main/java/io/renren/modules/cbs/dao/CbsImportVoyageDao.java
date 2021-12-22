package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsImportVoyageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 进口航次（变更）表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-18 15:18:27
 */
@Mapper
@Repository
public interface CbsImportVoyageDao extends BaseMapper<CbsImportVoyageEntity> {
    List<CbsImportVoyageEntity> listByImportId(Long importId);

    CbsImportVoyageEntity simpleDetail(Long id);
}
