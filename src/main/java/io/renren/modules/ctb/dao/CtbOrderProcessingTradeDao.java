package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 报关行加贸（手册）业务表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbOrderProcessingTradeDao extends BaseMapper<CtbOrderProcessingTradeEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CtbOrderProcessingTradeEntity entity);

    @SqlParser(filter = true)
    void updateByIdWithoutTenant(CtbOrderProcessingTradeEntity entity);

    IPage<CtbOrderProcessingTradeEntity> queryIndex(IPage<CtbOrderProcessingTradeEntity> page, @Param(Constants.WRAPPER) CtbOrderProcessingTradeEntity entity);

    @SqlParser(filter = true)
    CtbOrderProcessingTradeEntity selectByIdWithoutTenant(Long id);

    CtbOrderProcessingTradeEntity simpleDetail(Long id);

    CtbOrderProcessingTradeEntity detail(Long id);

    @SqlParser(filter = true)
    CtbOrderProcessingTradeEntity detailWithoutBindAndTenant(Long id);

    CtbOrderProcessingTradeEntity simpleDetailWithGoods(Long id);

    IPage<CtbOrderProcessingTradeEntity> selectByGoodsCode(IPage<CtbOrderProcessingTradeEntity> page, @Param(Constants.WRAPPER) CtbOrderProcessingTradeEntity entity);

    IPage<CtbOrderProcessingTradeEntity> selectByHsCode(IPage<CtbOrderProcessingTradeEntity> page, @Param(Constants.WRAPPER) CtbOrderProcessingTradeEntity entity);
}
