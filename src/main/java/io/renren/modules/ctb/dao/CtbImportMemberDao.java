package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImportMemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同过程中对应角色（成员）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-16 16:24:00
 */
@Mapper
@Repository
public interface CtbImportMemberDao extends BaseMapper<CtbImportMemberEntity> {
    List<CtbImportMemberEntity> listByImportId(Long importId);
}
