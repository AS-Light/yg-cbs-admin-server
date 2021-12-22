package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsProduceGoodsStreamEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 生产流水
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:15:37
 */
@Mapper
@Repository
public interface CbsProduceGoodsStreamDao extends BaseMapper<CbsProduceGoodsStreamEntity> {

    List<CbsProduceGoodsStreamEntity> listSimpleDetailByProduceId(Long produceId);

    CbsProduceGoodsStreamEntity detail(Long id);

}
