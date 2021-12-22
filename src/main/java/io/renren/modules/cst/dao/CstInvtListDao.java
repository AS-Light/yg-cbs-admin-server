package io.renren.modules.cst.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cst.entity.CstInvtListEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 保税核注清单表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@Mapper
@Repository
public interface CstInvtListDao extends BaseMapper<CstInvtListEntity> {
    List<CstInvtListEntity> queryByInvtId(Long invtId);
}
