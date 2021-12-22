package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsDirectoryCustomsBrokerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报关行名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@Mapper
@Repository
public interface CbsDirectoryCustomsBrokerDao extends BaseMapper<CbsDirectoryCustomsBrokerEntity> {
    IPage<CbsDirectoryCustomsBrokerEntity> queryIndex(IPage<CbsDirectoryCustomsBrokerEntity> page, @Param(Constants.WRAPPER) CbsDirectoryCustomsBrokerEntity entity);

    List<CbsDirectoryCustomsBrokerEntity> listBound();
}
