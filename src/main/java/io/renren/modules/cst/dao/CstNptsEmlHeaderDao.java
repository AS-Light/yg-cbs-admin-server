package io.renren.modules.cst.dao;

import io.renren.modules.cst.entity.CstNptsEmlHeaderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 海关加工贸易手册备案表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-13 19:21:36
 */
@Mapper
@Repository
public interface CstNptsEmlHeaderDao extends BaseMapper<CstNptsEmlHeaderEntity> {
	
}
