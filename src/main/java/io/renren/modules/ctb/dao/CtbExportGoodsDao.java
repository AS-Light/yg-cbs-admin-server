package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbExportGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出口货物清单
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbExportGoodsDao extends BaseMapper<CtbExportGoodsEntity> {
    List<CtbExportGoodsEntity> listByExportId(Long exportId);

    CtbExportGoodsEntity simpleDetail(Long id);

    List<Long> listIdsByExportIds(@Param("exportIds") Long[] exportIds);
}
