package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.ctb.entity.CtbMoneyInEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 出口单产生的收入流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
@Mapper
@Repository
public interface CtbMoneyInDao extends BaseMapper<CtbMoneyInEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CtbMoneyInEntity entity);

    IPage<CtbMoneyInEntity> queryIndex(IPage<CtbMoneyInEntity> page, @Param(Constants.WRAPPER) CtbMoneyInEntity entity);

    List<CtbMoneyInEntity> selectByIds(@Param("ids") List<Long> ids);
}
