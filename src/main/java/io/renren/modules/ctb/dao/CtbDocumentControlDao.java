package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.ctb.entity.CtbDocumentControlEntity;
import io.renren.modules.ctb.vo.PriceItemVo;
import io.renren.modules.ctb.vo.UnifiedUploadingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-16 15:09:03
 */
@Mapper
@Repository
public interface CtbDocumentControlDao extends BaseMapper<CtbDocumentControlEntity> {
    List<Long> selectBusinessIds(@Param(Constants.WRAPPER) UnifiedUploadingVo vo);

    Integer deleteAnnexByBusinessId(@Param(Constants.WRAPPER) UnifiedUploadingVo vo);

    Integer deleteDCByBusinessIds(@Param(Constants.WRAPPER) UnifiedUploadingVo vo);

    Integer insertAnnex(@Param(Constants.WRAPPER) UnifiedUploadingVo vo);

    Integer insertDC(@Param(Constants.WRAPPER) UnifiedUploadingVo vo);

    @SqlParser(filter = true)
    void deleteWithoutTenantId(@Param(Constants.WRAPPER) CtbDocumentControlEntity entity);

    @SqlParser(filter = true)
    List<PriceItemVo> selectPriceItemList(@Param(Constants.WRAPPER) PriceItemVo vo);
}
