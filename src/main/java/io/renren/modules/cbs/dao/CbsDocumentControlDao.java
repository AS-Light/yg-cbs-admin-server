package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsContractEntity;
import io.renren.modules.cbs.entity.CbsDocumentControlEntity;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-27 10:02:18
 */
@Mapper
@Repository
public interface CbsDocumentControlDao extends BaseMapper<CbsDocumentControlEntity> {

    List<Long> selectBusinessIds(@Param(Constants.WRAPPER) UnifiedUploadingVo vo);

    Integer deleteAnnexByBusinessId(@Param(Constants.WRAPPER) UnifiedUploadingVo vo);

    Integer deleteDCByBusinessIds(@Param(Constants.WRAPPER) UnifiedUploadingVo vo);

    Integer insertAnnex(@Param(Constants.WRAPPER) UnifiedUploadingVo vo);

    Integer insertDC(@Param(Constants.WRAPPER) UnifiedUploadingVo vo);

    IPage<CbsDocumentControlEntity> queryIndex(IPage<CbsDocumentControlEntity> page, @Param(Constants.WRAPPER) CbsDocumentControlEntity entity);
}
