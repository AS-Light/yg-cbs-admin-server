package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsGoodsPartNoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-07 18:33:29
 */
@Mapper
@Repository
public interface CbsGoodsPartNoDao extends BaseMapper<CbsGoodsPartNoEntity> {
    CbsGoodsPartNoEntity selectByCode(Long partNo);

    @SqlParser(filter = true)
    CbsGoodsPartNoEntity selectByCodeWithoutBind(Long partNo);
}
