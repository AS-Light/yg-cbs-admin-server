package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsImgImportLadingBillEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 进口提单图片表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-24 15:20:25
 */
@Mapper
@Repository
public interface CbsImgImportLadingBillDao extends BaseMapper<CbsImgImportLadingBillEntity> {
    List<CbsImgImportLadingBillEntity> listByImportId(Long importId);
}
