package io.renren.modules.cst.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cst.entity.CstNptsEmlConsumeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 加工贸易手册单耗表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-14 10:42:32
 */
@Mapper
@Repository
public interface CstNptsEmlConsumeDao extends BaseMapper<CstNptsEmlConsumeEntity> {
    List<CstNptsEmlConsumeEntity> queryByNptsEmlId(Long nptsEmlId);
}
