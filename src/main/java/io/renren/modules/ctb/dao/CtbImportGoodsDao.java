package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImportGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 进口货物清单
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbImportGoodsDao extends BaseMapper<CtbImportGoodsEntity> {
    List<CtbImportGoodsEntity> listByImportId(Long importId);

    CtbImportGoodsEntity simpleDetail(Long id);

    List<Long> listIdsByImportIds(@Param("importIds") Long[] importIds);
}
