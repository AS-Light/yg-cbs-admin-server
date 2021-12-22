package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsImgImportPackingListEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 进口箱单附件图片表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-31 11:24:35
 */
@Mapper
@Repository
public interface CbsImgImportPackingListDao extends BaseMapper<CbsImgImportPackingListEntity> {
    List<CbsImgImportPackingListEntity> listByImportId(Long importId);
}
