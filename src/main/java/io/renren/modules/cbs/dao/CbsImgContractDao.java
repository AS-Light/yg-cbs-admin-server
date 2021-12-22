package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsImgContractEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同图片表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-15 16:19:04
 */
@Mapper
@Repository
public interface CbsImgContractDao extends BaseMapper<CbsImgContractEntity> {
    List<CbsImgContractEntity> queryByContractId(Long contractId);
}
