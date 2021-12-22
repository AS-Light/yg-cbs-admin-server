package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsMoneyTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收入/支出类型
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
@Mapper
@Repository
public interface CbsMoneyTypeDao extends BaseMapper<CbsMoneyTypeEntity> {
    CbsMoneyTypeEntity selectByCode(Integer type);

    List<CbsMoneyTypeEntity> includeSelfBuilt();
}
