package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsMoneyOutEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 进口单产生的支出流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
@Mapper
@Repository
public interface CbsMoneyOutDao extends BaseMapper<CbsMoneyOutEntity> {
    IPage<CbsMoneyOutEntity> queryIndex(IPage<CbsMoneyOutEntity> page, @Param(Constants.WRAPPER) CbsMoneyOutEntity entity);

    CbsMoneyOutEntity simpleDetailById(Long id);
}
