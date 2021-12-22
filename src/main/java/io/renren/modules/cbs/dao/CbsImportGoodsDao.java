package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsImportGoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 原材进货记录
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-18 15:18:27
 */
@Mapper
@Repository
public interface CbsImportGoodsDao extends BaseMapper<CbsImportGoodsEntity> {
    List<CbsImportGoodsEntity> listByImportId(Long importId);

    CbsImportGoodsEntity simpleDetail(Long id);

    List<Long> listIdsByImportIds(@Param("importIds") Long[] importIds);
}
