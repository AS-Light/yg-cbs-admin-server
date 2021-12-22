package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsImgExportDeliveryOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 出口提货单图片表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-31 11:47:21
 */
@Mapper
@Repository
public interface CbsImgExportDeliveryOrderDao extends BaseMapper<CbsImgExportDeliveryOrderEntity> {
    List<CbsImgExportDeliveryOrderEntity> listByExportId(Long exportId);
}
