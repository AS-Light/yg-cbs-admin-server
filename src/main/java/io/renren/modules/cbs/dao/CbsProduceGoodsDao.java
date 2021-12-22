package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsProduceGoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 生产的商品缓存
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:47:33
 */
@Mapper
@Repository
public interface CbsProduceGoodsDao extends BaseMapper<CbsProduceGoodsEntity> {
	List<CbsProduceGoodsEntity> listByProduceId(Long produceId);

	List<Long> listIdsByProduceIds(@Param("produceIds") Long[] produceIds);
}
