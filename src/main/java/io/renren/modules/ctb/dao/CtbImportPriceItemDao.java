package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImportPriceItemEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 进口单费用明细
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImportPriceItemDao extends BaseMapper<CtbImportPriceItemEntity> {
    List<CtbImportPriceItemEntity> listByImportId(Long importId);
}
