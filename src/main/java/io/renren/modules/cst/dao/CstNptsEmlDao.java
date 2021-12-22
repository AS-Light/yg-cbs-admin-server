package io.renren.modules.cst.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cst.entity.CstNptsEmlEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CstNptsEmlDao extends BaseMapper<CstNptsEmlEntity> {
    CstNptsEmlEntity detail(Long id);

    CstNptsEmlEntity detailByContractId(Long contractId);
}
