package io.renren.modules.ctb.dao;

import io.renren.modules.ctb.entity.CtbPriceListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 报关行报价单表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbPriceListDao extends BaseMapper<CtbPriceListEntity> {
    CtbPriceListEntity detail(Long id);

    CtbPriceListEntity detailByContractId(Long contractId);
}
