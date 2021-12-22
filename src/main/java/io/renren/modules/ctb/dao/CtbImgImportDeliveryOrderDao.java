package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgImportDeliveryOrderEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 进口提货单图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgImportDeliveryOrderDao extends BaseMapper<CtbImgImportDeliveryOrderEntity> {
    List<CtbImgImportDeliveryOrderEntity> listByImportId(Long importId);
}
