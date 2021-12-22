package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsExportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 出口单（主）表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@Mapper
@Repository
public interface CbsExportDao extends BaseMapper<CbsExportEntity> {
    @SqlParser(filter = true)
    void updateByIdWithoutTenant(CbsExportEntity entity);

    IPage<CbsExportEntity> queryIndex(IPage<CbsExportEntity> page, @Param(Constants.WRAPPER) CbsExportEntity entity);

    List<CbsExportEntity> queryByContractIdWithGoods(Long contractId);

    CbsExportEntity selectById(Long id);

    CbsExportEntity detail(Long id);

    @SqlParser(filter = true)
    CbsExportEntity detailWithoutBindAndTenant(Long id);

    List<CbsExportEntity> queryForStoreOut();

    CbsExportEntity simpleDetail(Long id);

    CbsExportEntity simpleDetailWithGoodsItems(Long id);

    List<CbsExportEntity> listByContractId(Long contractId);
}
