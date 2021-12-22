package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgExportContractEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出口合同图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgExportContractDao extends BaseMapper<CtbImgExportContractEntity> {
	List<CtbImgExportContractEntity> listByExportId(Long exportId);
}
