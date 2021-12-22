package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.ctb.entity.CtbImportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 进口单（主）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbImportDao extends BaseMapper<CtbImportEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CtbImportEntity entity);

    @SqlParser(filter = true)
    void updateByIdWithoutTenant(CtbImportEntity entity);

    IPage<CtbImportEntity> queryIndex(IPage<CtbImportEntity> page, @Param(Constants.WRAPPER) CtbImportEntity entity);

    CtbImportEntity simpleDetail(Long id);

    CtbImportEntity detail(Long id);

    @SqlParser(filter = true)
    CtbImportEntity detailWithoutBindAndTenant(Long id);

    CtbImportEntity simpleDetailWithGoodsItems(Long id);
}
