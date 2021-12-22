package io.renren.modules.cst.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cst.entity.CstNptsEmlImgExgEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 加工贸易手册原料表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-13 19:21:36
 */
@Mapper
@Repository
public interface CstNptsEmlImgExgDao extends BaseMapper<CstNptsEmlImgExgEntity> {
    List<CstNptsEmlImgExgEntity> queryByNptsEmlId(Long nptsemlId);
}
