package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbDirectoryGoodsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 原材料名录表（报关行副本）
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbDirectoryGoodsDao extends BaseMapper<CtbDirectoryGoodsEntity> {

    @SqlParser(filter = true)
    void insertWithoutTenant(CtbDirectoryGoodsEntity entity);

    CtbDirectoryGoodsEntity detail(Long id);
}
