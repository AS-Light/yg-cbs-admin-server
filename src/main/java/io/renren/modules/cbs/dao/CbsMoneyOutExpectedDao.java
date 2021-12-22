package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsMoneyOutExpectedEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 合同审核通过后的预计支出流水
 *
 * @author ChenNing
 * @email record_7@126.com
 * @date 2020-02-18 11:16:49
 */
@Mapper
@Repository
public interface CbsMoneyOutExpectedDao extends BaseMapper<CbsMoneyOutExpectedEntity> {

    IPage<CbsMoneyOutExpectedEntity> queryIndex(IPage<CbsMoneyOutExpectedEntity> page, @Param(Constants.WRAPPER) CbsMoneyOutExpectedEntity entity);
}
