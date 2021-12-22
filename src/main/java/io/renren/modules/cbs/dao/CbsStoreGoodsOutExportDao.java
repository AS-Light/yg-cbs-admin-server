package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsStoreGoodsOutExportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 出口出库表，与cbs_store_goods_out表为1对1继承关系
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Mapper
@Repository
public interface CbsStoreGoodsOutExportDao extends BaseMapper<CbsStoreGoodsOutExportEntity> {
    CbsStoreGoodsOutExportEntity selectByExportId(Long exportId);

    CbsStoreGoodsOutExportEntity selectByStoreOutId(Long storeOutId);

    List<CbsStoreGoodsOutExportEntity> listByContract(Long contractId);
}
