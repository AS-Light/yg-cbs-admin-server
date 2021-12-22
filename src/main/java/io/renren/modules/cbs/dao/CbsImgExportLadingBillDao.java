package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsImgExportLadingBillEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 进口提单图片表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-31 11:47:21
 */
@Mapper
@Repository
public interface CbsImgExportLadingBillDao extends BaseMapper<CbsImgExportLadingBillEntity> {
    List<CbsImgExportLadingBillEntity> listByExportId(Long exportId);
}
