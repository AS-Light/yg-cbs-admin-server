package io.renren.modules.ctb.dao;

import io.renren.modules.ctb.entity.CtbExportMemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 合同过程中对应角色（成员）表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-16 16:24:00
 */
@Mapper
public interface CtbExportMemberDao extends BaseMapper<CtbExportMemberEntity> {
	List<CtbExportMemberEntity> listByExportId(Long exportId);
}
