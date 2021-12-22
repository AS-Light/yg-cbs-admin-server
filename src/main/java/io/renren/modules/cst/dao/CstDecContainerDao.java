package io.renren.modules.cst.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cst.entity.CstDecContainerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报关单集装箱
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Mapper
@Repository
public interface CstDecContainerDao extends BaseMapper<CstDecContainerEntity> {
    List<CstDecContainerEntity> queryByDecId(Long decId);
}
