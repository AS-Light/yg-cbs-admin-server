package io.renren.modules.cst.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cst.entity.CstDecEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CstDecDao extends BaseMapper<CstDecEntity> {
    CstDecEntity detail(Long id);

    CstDecEntity detailByImportId(Long id);

    CstDecEntity detailByExportId(Long id);

    List<CstDecEntity> queryByContractId(Long contractId);
}
