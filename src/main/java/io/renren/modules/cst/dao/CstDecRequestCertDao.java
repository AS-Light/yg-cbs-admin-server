package io.renren.modules.cst.dao;

import io.renren.modules.cst.entity.CstDecRequestCertEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 进出口报关单 申请单证信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Mapper
public interface CstDecRequestCertDao extends BaseMapper<CstDecRequestCertEntity> {
	
}
