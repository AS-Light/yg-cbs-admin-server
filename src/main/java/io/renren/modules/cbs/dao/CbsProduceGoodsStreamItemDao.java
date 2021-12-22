package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsProduceGoodsStreamItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * cbs_produce_goods_stream 的子表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:15:37
 */
@Mapper
public interface CbsProduceGoodsStreamItemDao extends BaseMapper<CbsProduceGoodsStreamItemEntity> {
    List<CbsProduceGoodsStreamItemEntity> listSimpleDetailByStreamId(Long streamId);

    List<Long> listIdsByProduceStreamIds(Long[] streamIds);
}
