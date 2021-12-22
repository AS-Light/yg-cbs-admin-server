package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsMoneyTypeOneselfEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 收入/支出类型
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-09 10:52:57
 */
@Mapper
@Repository
public interface CbsMoneyTypeOneselfDao extends BaseMapper<CbsMoneyTypeOneselfEntity> {
	
}
