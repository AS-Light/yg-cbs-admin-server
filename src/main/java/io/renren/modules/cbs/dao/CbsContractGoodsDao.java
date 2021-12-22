package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsContractGoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 原材料名录表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 10:19:45
 */
@Mapper
@Repository
public interface CbsContractGoodsDao extends BaseMapper<CbsContractGoodsEntity> {
    List<CbsContractGoodsEntity> selectByContractId(Long contractId);

    CbsContractGoodsEntity detail(Long id);
}
