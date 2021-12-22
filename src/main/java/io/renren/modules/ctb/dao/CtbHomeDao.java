package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.StatisticalDigital;
import io.renren.modules.ctb.entity.CtbHomeEntity;
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
public interface CtbHomeDao extends BaseMapper<CtbHomeEntity> {

    StatisticalDigital selectTotal(@Param("ctbTenantId") Long ctbTenantId, @Param("tableName") String tableName, @Param("completeStatus") Integer completeStatus);

    List<StatisticalDigital> incomeExpendList(@Param("ctbTenantId") Long ctbTenantId, @Param("months") String[] months, @Param("tableName") String tableName);

    List<StatisticalDigital> canadaTradeApplyToCustoms(@Param("ctbTenantId") Long ctbTenantId, @Param("months") String[] months, @Param("tableName") String tableName);

    StatisticalDigital selectTotalInOut(@Param("ctbTenantId") Long ctbTenantId, @Param("tableName") String tableName);
}
