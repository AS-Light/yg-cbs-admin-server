package io.renren.modules.cst.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cst.entity.CstDecListEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 进出口报关单表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Mapper
@Repository
public interface CstDecListDao extends BaseMapper<CstDecListEntity> {
    List<CstDecListEntity> queryByDecId(Long decId);
}
