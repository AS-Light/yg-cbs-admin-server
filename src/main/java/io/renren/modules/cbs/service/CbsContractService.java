package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsContractEntity;
import io.renren.modules.cbs.entity.CbsContractStatusStreamEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 合同表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-09 11:16:37
 */
public interface CbsContractService extends IService<CbsContractEntity> {

    PageUtils queryIndex(CbsContractEntity entity);

    Long saveReturnId(CbsContractEntity entity);

    CbsContractEntity detail(Long id);

    Integer updateAllById(CbsContractEntity entity);

    void updateEmlCode(CbsContractEntity entity);

    Integer deleteOne(CbsContractEntity entity);

    void submit(CbsContractStatusStreamEntity entity) throws RuntimeException;

    Integer submitBack(CbsContractStatusStreamEntity entity);

    void check(CbsContractStatusStreamEntity entity) throws RuntimeException;

    HashMap<String, Object> submitComplete(CbsContractStatusStreamEntity entity);

    Integer submitCompleteBack(CbsContractStatusStreamEntity entity);

    HashMap<String, Object> checkComplete(CbsContractStatusStreamEntity entity);

    PageUtils listForType(CbsContractEntity entity);

    List<CbsContractEntity> listForCreateImport();

    List<CbsContractEntity> listForCreateExport();

    List<CbsContractEntity> listForCreatePurchase();

    List<CbsContractEntity> listForCreateSell();

    List<CbsContractEntity> listForCreateProduce();

    PageUtils selectContractByImportBillCode(CbsContractEntity entity);

    PageUtils selectContractByExportBillCode(CbsContractEntity entity);

    PageUtils selectContractByGoodsCode(CbsContractEntity entity);

    PageUtils selectContractByHsCode(CbsContractEntity entity);

    PageUtils subcontractList(CbsContractEntity entity);

    HashMap<String, Object> deleteByIds(Long[] ids);

    String AEOExportExcel(HttpServletResponse response, CbsContractEntity entity);

    void synToCtb(Long id);
}

