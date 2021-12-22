package io.renren.modules.ctb.dao;

import io.renren.modules.ctb.entity.CtbImgOrderProcessingTradeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同图片表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbImgOrderProcessingTradeDao extends BaseMapper<CtbImgOrderProcessingTradeEntity> {
    List<CtbImgOrderProcessingTradeEntity> queryByOrderProcessingTradeId(Long orderProcessingTradeId);

    void deleteOrderByIdWithoutTenant(Long orderId);
}
