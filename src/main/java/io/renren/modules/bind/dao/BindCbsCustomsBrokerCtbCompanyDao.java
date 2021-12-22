package io.renren.modules.bind.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 17:06:26
 */
@Mapper
@Repository
public interface BindCbsCustomsBrokerCtbCompanyDao extends BaseMapper<BindCbsCustomsBrokerCtbCompanyEntity> {

    IPage<BindCbsCustomsBrokerCtbCompanyEntity> queryIndex(IPage<BindCbsCustomsBrokerCtbCompanyEntity> page, @Param(Constants.WRAPPER) BindCbsCustomsBrokerCtbCompanyEntity entity);

    BindCbsCustomsBrokerCtbCompanyEntity detail(Long id);

    BindCbsCustomsBrokerCtbCompanyEntity detailWithCbs(Long id);

    BindCbsCustomsBrokerCtbCompanyEntity detailWithCtb(Long id);

    BindCbsCustomsBrokerCtbCompanyEntity detailByBothId(@Param("cbsCompanyId") Long cbsCompanyId, @Param("ctbCompanyId") Long ctbCompanyId);

    BindCbsCustomsBrokerCtbCompanyEntity detailWithCbsByCtbDirectoryId(Long ctbDirectoryId);

    BindCbsCustomsBrokerCtbCompanyEntity detailWithCtbByCbsDirectoryId(Long cbsDirectoryId);
}
