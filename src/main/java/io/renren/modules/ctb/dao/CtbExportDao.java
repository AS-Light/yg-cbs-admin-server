package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.ctb.entity.CtbExportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 进口单（主）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbExportDao extends BaseMapper<CtbExportEntity> {
    @SqlParser(filter = true)
    void insertWithoutTenant(CtbExportEntity entity);

    @SqlParser(filter = true)
    void updateByIdWithoutTenant(CtbExportEntity entity);

    IPage<CtbExportEntity> queryIndex(IPage<CtbExportEntity> page, @Param(Constants.WRAPPER) CtbExportEntity entity);

    CtbExportEntity detail(Long id);

    @SqlParser(filter = true)
    CtbExportEntity detailWithoutBindAndTenant(Long id);

    CtbExportEntity simpleDetail(Long id);

    CtbExportEntity simpleDetailWithGoodsItems(Long id);
}
