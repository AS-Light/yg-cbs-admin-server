package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsImgExportPayTailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 尾款附件
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-18 15:16:54
 */
@Mapper
@Repository
public interface CbsImgExportPayTailDao extends BaseMapper<CbsImgExportPayTailEntity> {
    List<CbsImgExportPayTailEntity> listByExportId(Long exportId);
}
