package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbMoneyTypeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收入/支出类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbMoneyTypeDao extends BaseMapper<CtbMoneyTypeEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CtbMoneyTypeEntity entity);

    List<CtbMoneyTypeEntity> listByServiceCompanyId(Long serviceCompanyId);

    List<CtbMoneyTypeEntity> listDef();
}
