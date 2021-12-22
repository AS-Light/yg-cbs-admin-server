package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import io.renren.modules.cbs.entity.CbsDirectoryGoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 原材料名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 10:29:32
 */
@Mapper
@Repository
public interface CbsDirectoryGoodsDao extends BaseMapper<CbsDirectoryGoodsEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CbsDirectoryGoodsEntity entity);

    CbsDirectoryGoodsEntity detail(Long id);

    @SqlParser(filter = true)
    CbsDirectoryGoodsEntity detailWithoutTenant(Long id);
}
