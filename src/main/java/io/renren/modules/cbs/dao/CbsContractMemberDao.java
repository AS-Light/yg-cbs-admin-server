package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsContractMemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同过程中对应角色（成员）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-25 11:14:59
 */
@Mapper
@Repository
public interface CbsContractMemberDao extends BaseMapper<CbsContractMemberEntity> {
    List<CbsContractMemberEntity> listByContractId(Long contractId);

    CbsContractMemberEntity detail(Long id);
}
