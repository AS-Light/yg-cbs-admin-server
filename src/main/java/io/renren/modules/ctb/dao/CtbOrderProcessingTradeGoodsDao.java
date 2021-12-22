package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 加贸商品名录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbOrderProcessingTradeGoodsDao extends BaseMapper<CtbOrderProcessingTradeGoodsEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CtbOrderProcessingTradeGoodsEntity entity);

    List<CtbOrderProcessingTradeGoodsEntity> selectByOrderProcessingTradeId(Long orderProcessingTradeId);

    CtbOrderProcessingTradeGoodsEntity detail(Long id);
}
