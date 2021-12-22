package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import io.renren.modules.ctb.entity.CtbServiceContractEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 报关行货运代理合同
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbServiceContractDao extends BaseMapper<CtbServiceContractEntity> {

    @SqlParser(filter = true)
    void insertWithoutTenant(CtbServiceContractEntity entity);

    CtbServiceContractEntity detail(Long id);
}
