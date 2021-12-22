package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.ctb.entity.CtbMoneyOutEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 进口单产生的支出流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbMoneyOutDao extends BaseMapper<CtbMoneyOutEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CtbMoneyOutEntity entity);

    IPage<CtbMoneyOutEntity> queryIndex(IPage<CtbMoneyOutEntity> page, @Param(Constants.WRAPPER) CtbMoneyOutEntity entity);

    List<CtbMoneyOutEntity> selectByIds(@Param("ids") List<Long> ids);
}
