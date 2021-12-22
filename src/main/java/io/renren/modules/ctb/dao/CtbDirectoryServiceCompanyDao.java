package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbDirectoryServiceCompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报关行服务公司名录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbDirectoryServiceCompanyDao extends BaseMapper<CtbDirectoryServiceCompanyEntity> {

    @SqlParser(filter = true)
    int insertWithoutTenant(CtbDirectoryServiceCompanyEntity ctbDirectoryServiceCompany);

    @SqlParser(filter = true)
    CtbDirectoryServiceCompanyEntity selectByIdWithoutTenant(Long id);

    List<CtbDirectoryServiceCompanyEntity> selfEditingList(CtbDirectoryServiceCompanyEntity entity);
}
