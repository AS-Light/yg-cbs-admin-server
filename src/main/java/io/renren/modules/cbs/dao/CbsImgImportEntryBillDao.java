package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsImgImportEntryBillEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 进口报关单图片表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-24 15:20:25
 */
@Mapper
@Repository
public interface CbsImgImportEntryBillDao extends BaseMapper<CbsImgImportEntryBillEntity> {
    List<CbsImgImportEntryBillEntity> listByImportId(Long importId);
}
