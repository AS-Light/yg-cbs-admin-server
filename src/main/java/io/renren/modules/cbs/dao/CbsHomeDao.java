package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsHomeEntity;
import io.renren.modules.cbs.entity.StatisticalContract;
import io.renren.modules.cbs.entity.StatisticalDigital;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 首页
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-09 11:16:37
 */
@Mapper
@Repository
public interface CbsHomeDao extends BaseMapper<CbsHomeEntity> {

    List<StatisticalDigital> selectTotalList(@Param("tenantId") Long tenantId);

    StatisticalDigital selectTotal(@Param("tenantId") Long tenantId, @Param("tableName") String tableName, @Param("completeStatus") Integer completeStatus);

    List<StatisticalDigital> incomeExpendList(@Param("tenantId") Long tenantId, @Param("months") String[] months, @Param("tableName") String tableName);

    List<StatisticalContract> upcomingContract(@Param("tenantId") Long tenantId);

    List<StatisticalContract> upcomingDelivery(@Param("tenantId") Long tenantId, @Param("type") Integer type, @Param("tableName") String tableName);
}
