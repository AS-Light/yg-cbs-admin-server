package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsPartnerEntity;
import io.renren.modules.ctb.entity.CtbPartnerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 交易对象（公司）名录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
@Mapper
public interface CtbPartnerDao extends BaseMapper<CtbPartnerEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CtbPartnerEntity entity);

    CtbPartnerEntity simpleDetail(Long id);

    @SqlParser(filter = true)
    CtbPartnerEntity simpleDetailWithoutTenant(Long id);

    CtbPartnerEntity simpleDetailByCode(String code);

    List<CtbPartnerEntity> listByTypes(@Param("types") List<Integer> types, @Param("name") String name);

    @SqlParser(filter = true)
    CtbPartnerEntity findSameToCbs(@Param("cbsEntity") CbsPartnerEntity ctbEntity, @Param("ctbTenantId") Long ctbTenantId);
}
