package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeMemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 加贸单编辑过程中对应角色（成员）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbOrderProcessingTradeMemberDao extends BaseMapper<CtbOrderProcessingTradeMemberEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CtbOrderProcessingTradeMemberEntity entity);

    List<CtbOrderProcessingTradeMemberEntity> listByOrderProcessingTradeId(Long orderProcessingTradeId);

    CtbOrderProcessingTradeMemberEntity detail(Long id);

    void deleteByOrderIdWithoutTenant(Long orderId);
}
