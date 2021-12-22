package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgServiceContractEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 报关行服务企业的合同附件表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-21 16:09:24
 */
@Mapper
@Repository
public interface CtbImgServiceContractDao extends BaseMapper<CtbImgServiceContractEntity> {

}
