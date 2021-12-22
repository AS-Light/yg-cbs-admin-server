package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsImportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 进口单（主）表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@Mapper
@Repository
public interface CbsImportDao extends BaseMapper<CbsImportEntity> {
    @SqlParser(filter = true)
    void updateByIdWithoutTenant(CbsImportEntity entity);

    IPage<CbsImportEntity> queryIndex(IPage<CbsImportEntity> page, @Param(Constants.WRAPPER) CbsImportEntity entity);

    List<CbsImportEntity> queryByContractIdWithGoods(Long contractId);

    List<CbsImportEntity> queryForStoreIn();

    CbsImportEntity simpleDetail(Long id);

    CbsImportEntity simpleDetailWithGoodsItems(Long id);

    CbsImportEntity detail(Long id);

    @SqlParser(filter = true)
    CbsImportEntity detailWithoutBindAndTenant(Long id);

    List<CbsImportEntity> listByContractId(Long contractId);
}
