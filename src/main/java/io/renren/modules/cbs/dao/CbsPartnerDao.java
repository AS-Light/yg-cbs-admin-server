package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsPartnerEntity;
import io.renren.modules.ctb.entity.CtbPartnerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 交易对象（公司）名录表
 */
@Mapper
@Repository
public interface CbsPartnerDao extends BaseMapper<CbsPartnerEntity> {
    CbsPartnerEntity simpleDetail(Long id);

    CbsPartnerEntity simpleDetailByCode(String code);

    List<CbsPartnerEntity> listByTypes(@Param("types") List<Integer> types, @Param("name") String name);

    @SqlParser(filter = true)
    CbsPartnerEntity findSameToCtb(@Param("ctbEntity") CtbPartnerEntity ctbEntity, @Param("tenantId") Long tenantId);
}
