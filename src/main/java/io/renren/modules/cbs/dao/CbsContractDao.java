package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.cbs.entity.CbsContractEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合同表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-09 11:16:37
 */
@Mapper
@Repository
public interface CbsContractDao extends BaseMapper<CbsContractEntity> {
    @SqlParser(filter = true)
    void updateByIdWithoutTenant(CbsContractEntity entity);

    IPage<CbsContractEntity> queryIndex(IPage<CbsContractEntity> page, @Param(Constants.WRAPPER) CbsContractEntity entity);

    @SqlParser(filter = true)
    CbsContractEntity selectByIdWithoutTenant(Long id);

    CbsContractEntity simpleDetail(Long id);

    CbsContractEntity detail(Long id);

    @SqlParser(filter = true)
    CbsContractEntity detailWithoutBindAndTenant(Long id);

    CbsContractEntity simpleDetailWithGoods(Long id);

    List<CbsContractEntity> queryChildrenByParentId(Long parentId);

    List<CbsContractEntity> queryChildrenDetailByParentId(Long parentId);

    List<CbsContractEntity> listForCreateImport();

    List<CbsContractEntity> listForCreateExport();

    List<CbsContractEntity> listForCreatePurchase();

    List<CbsContractEntity> listForCreateSell();

    List<CbsContractEntity> listForCreateProduce();

    IPage<CbsContractEntity> selectContractByImportBillCode(IPage<CbsContractEntity> page, @Param(Constants.WRAPPER) CbsContractEntity entity);

    IPage<CbsContractEntity> selectContractByExportBillCode(IPage<CbsContractEntity> page, @Param(Constants.WRAPPER) CbsContractEntity entity);

    IPage<CbsContractEntity> selectContractByGoodsCode(IPage<CbsContractEntity> page, @Param(Constants.WRAPPER) CbsContractEntity entity);

    IPage<CbsContractEntity> selectContractByHsCode(IPage<CbsContractEntity> page, @Param(Constants.WRAPPER) CbsContractEntity entity);
}
