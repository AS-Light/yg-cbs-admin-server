package io.renren.modules.bind.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.bind.entity.BindCbsContractCtbProcessingTradeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 关联表：cbs_contract | ctb_order_prodcessing_trade
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:34:21
 */
@Mapper
@Repository
public interface BindCbsContractCtbProcessingTradeDao extends BaseMapper<BindCbsContractCtbProcessingTradeEntity> {

    IPage<BindCbsContractCtbProcessingTradeEntity> queryIndex(IPage<BindCbsContractCtbProcessingTradeEntity> page, @Param(Constants.WRAPPER) BindCbsContractCtbProcessingTradeEntity entity);

    BindCbsContractCtbProcessingTradeEntity detail(Long id);

    BindCbsContractCtbProcessingTradeEntity detailWithCbs(Long ctbOrderProcessingTradeId);

    BindCbsContractCtbProcessingTradeEntity detailWithCtb(Long cbsContractId);
}
