package io.renren.common.utils;


import io.renren.modules.cbs.dao.CbsDocumentControlDao;
import io.renren.modules.cbs.dao.SynCbsCtbImportAndExportImgDao;
import io.renren.modules.cbs.vo.SynImportAndExportImgVo;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import io.renren.modules.ctb.dao.CtbDocumentControlDao;
import io.renren.modules.ctb.dao.CtbMoneyInDao;
import io.renren.modules.ctb.dao.CtbMoneyOutDao;
import io.renren.modules.ctb.entity.CtbMoneyInEntity;
import io.renren.modules.ctb.entity.CtbMoneyOutEntity;
import io.renren.modules.ctb.vo.PriceItemVo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 公用方法
 * @Author: ChenNing
 * @Date: 14:22 2019/8/15
 */
@Component
public class CommonFunction {

    private CbsDocumentControlDao documentControlDao;
    private CtbDocumentControlDao ctbDocumentControlDao;
    private SynCbsCtbImportAndExportImgDao synCbsCtbImportAndExportImgDao;
    private CtbMoneyInDao moneyInDao;
    private CtbMoneyOutDao moneyOutDao;

    public CommonFunction(CbsDocumentControlDao documentControlDao,
                          CtbDocumentControlDao ctbDocumentControlDao,
                          SynCbsCtbImportAndExportImgDao synCbsCtbImportAndExportImgDao, CtbMoneyInDao moneyInDao, CtbMoneyOutDao moneyOutDao) {
        this.documentControlDao = documentControlDao;
        this.ctbDocumentControlDao = ctbDocumentControlDao;
        this.synCbsCtbImportAndExportImgDao = synCbsCtbImportAndExportImgDao;
        this.moneyInDao = moneyInDao;
        this.moneyOutDao = moneyOutDao;
    }

    /**
     * @Description: 转16进制
     * @Author: ChenNing
     * @Date: 14:23 2019/8/15
     */
    public static String intToHex(int n) {
        StringBuilder sb = new StringBuilder(8);
        String a;
        char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        while (n != 0) {
            sb = sb.append(b[n % 16]);
            n = n / 16;
        }
        a = sb.reverse().toString();
        return a;
    }

    /**
     * @description 统一处理附件上传
     * @author chenning
     * @date 2020/3/27 11:10
     */
    @Transactional
    public void unifiedUploading(UnifiedUploadingVo vo) {
        // 获取要删除的附件表ID
        vo.setIdList(documentControlDao.selectBusinessIds(vo));
        // 删除附件表
        documentControlDao.deleteAnnexByBusinessId(vo);
        // 删除文控表
        if (vo.getIdList() != null && vo.getIdList().size() > 0) {
            documentControlDao.deleteDCByBusinessIds(vo);
        }
        for (String img : vo.getImgList()) {
            vo.setImg(img);
            // 批量插入附件表
            documentControlDao.insertAnnex(vo);
            // 批量插入文控表
            documentControlDao.insertDC(vo);
        }
    }

    /**
     * @description Ctb统一处理附件上传
     * @author chenning
     * @date 2020/3/27 11:10
     */
    @Transactional
    public void ctbUnifiedUploading(io.renren.modules.ctb.vo.UnifiedUploadingVo vo) {
        // 获取要删除的附件表ID
        vo.setIdList(ctbDocumentControlDao.selectBusinessIds(vo));
        // 删除附件表
        ctbDocumentControlDao.deleteAnnexByBusinessId(vo);
        // 删除文控表
        if (vo.getIdList() != null && vo.getIdList().size() > 0) {
            ctbDocumentControlDao.deleteDCByBusinessIds(vo);
        }
        for (String img : vo.getImgList()) {
            vo.setImg(img);
            // 批量插入附件表
            ctbDocumentControlDao.insertAnnex(vo);
            // 批量插入文控表
            ctbDocumentControlDao.insertDC(vo);
        }
    }


    /**
     * @description cbs2ctb 进出口附件同步
     * @author chenning
     * @date 2020/3/27 11:10
     */
    @Transactional
    public void synCbsCtbImportAndExportImg(SynImportAndExportImgVo vo) {
        // 根据表名查询 cbs_img_import_contract_ship(originalTable) 的list信息 ，条件是 fk_import_id(fkField) = originalFkId
        List<String> imgList = synCbsCtbImportAndExportImgDao.selectByField(vo);
        // 删除 ctb_img_import_contract_ship(targetTable),条件是 fk_import_id(fkField) = targetFkId
        synCbsCtbImportAndExportImgDao.deleteByField(vo);
        // 插入以上 list ctb_img_import_contract_ship(targetTable)
        for (String img : imgList) {
            vo.setImg(img);
            synCbsCtbImportAndExportImgDao.insertImg(vo);
        }
    }

    /**
     * @description 查询 price_item list -> money in out
     * @author chenning
     * @date 2020/3/27 11:10
     */
    @Transactional
    public void processCtbPriceItemList(PriceItemVo vo) {
        List<PriceItemVo> priceItemList = ctbDocumentControlDao.selectPriceItemList(vo);
        for (PriceItemVo item : priceItemList) {
            BigDecimal cny = item.getExchangeRate().multiply(item.getMoney()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            if ("I".equals(item.getIo())) {
                moneyInDao.insert(CtbMoneyInEntity.builder()
                        .fkServiceCompanyId(vo.getServiceCompanyId())
                        .orderType(vo.getOrderType())
                        .fkOrderId(vo.getParameterId())
                        .fkMoneyTypeId(item.getFkMoneyTypeId())
                        .title(vo.getName())
                        .unitMoney(item.getUnitMoney())
                        .unit(item.getUnit())
                        .count(item.getCount())
                        .money(item.getMoney())
                        .currencyCode(item.getCurrencyCode())
                        .operator(vo.getOperator())
                        .cny(cny)
                        .isReimbursement(item.getIsReimbursement())
                        .build());
            } else {
                moneyOutDao.insert(CtbMoneyOutEntity.builder()
                        .fkServiceCompanyId(vo.getServiceCompanyId())
                        .orderType(vo.getOrderType())
                        .fkOrderId(vo.getParameterId())
                        .fkMoneyTypeId(item.getFkMoneyTypeId())
                        .title(vo.getName())
                        .unitMoney(item.getUnitMoney())
                        .unit(item.getUnit())
                        .count(item.getCount())
                        .money(item.getMoney())
                        .currencyCode(item.getCurrencyCode())
                        .operator(vo.getOperator())
                        .cny(cny)
                        .isReimbursement(item.getIsReimbursement())
                        .build());
            }
        }
    }

    @Transactional
    public void processCtbPriceItemListWithoutTenant(PriceItemVo vo, Long ctbTenantId) {
        List<PriceItemVo> priceItemList = ctbDocumentControlDao.selectPriceItemList(vo);
        for (PriceItemVo item : priceItemList) {
            BigDecimal cny = item.getExchangeRate().multiply(item.getMoney()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            if ("I".equals(item.getIo())) {
                moneyInDao.insertWithoutTenant(CtbMoneyInEntity.builder()
                        .fkServiceCompanyId(vo.getServiceCompanyId())
                        .orderType(vo.getOrderType())
                        .fkOrderId(vo.getParameterId())
                        .fkMoneyTypeId(item.getFkMoneyTypeId())
                        .title(vo.getName())
                        .unitMoney(item.getUnitMoney())
                        .unit(item.getUnit())
                        .count(item.getCount())
                        .money(item.getMoney())
                        .currencyCode(item.getCurrencyCode())
                        .operator(vo.getOperator())
                        .cny(cny)
                        .isReimbursement(item.getIsReimbursement())
                        .ctbTenantId(ctbTenantId)
                        .build());
            } else {
                moneyOutDao.insertWithoutTenant(CtbMoneyOutEntity.builder()
                        .fkServiceCompanyId(vo.getServiceCompanyId())
                        .orderType(vo.getOrderType())
                        .fkOrderId(vo.getParameterId())
                        .fkMoneyTypeId(item.getFkMoneyTypeId())
                        .title(vo.getName())
                        .unitMoney(item.getUnitMoney())
                        .unit(item.getUnit())
                        .count(item.getCount())
                        .money(item.getMoney())
                        .currencyCode(item.getCurrencyCode())
                        .operator(vo.getOperator())
                        .cny(cny)
                        .isReimbursement(item.getIsReimbursement())
                        .ctbTenantId(ctbTenantId)
                        .build());
            }
        }
    }

}
