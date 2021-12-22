package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbGoodsPartNoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
@Mapper
@Repository
public interface CtbGoodsPartNoDao extends BaseMapper<CtbGoodsPartNoEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CtbGoodsPartNoEntity entity);

    CtbGoodsPartNoEntity selectByCode(Long partNo);

    @SqlParser(filter = true)
    CtbGoodsPartNoEntity selectByCodeWithoutBind(Long partNo);
}
