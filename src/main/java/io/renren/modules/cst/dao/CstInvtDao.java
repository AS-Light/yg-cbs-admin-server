package io.renren.modules.cst.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cst.entity.CstInvtEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CstInvtDao extends BaseMapper<CstInvtEntity> {
    CstInvtEntity detail(Long id);

    CstInvtEntity detailByImportId(Long id);

    CstInvtEntity detailByExportId(Long id);

    List<CstInvtEntity> queryByContractId(Long contractId);
}
