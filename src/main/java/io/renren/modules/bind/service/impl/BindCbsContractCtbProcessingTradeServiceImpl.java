package io.renren.modules.bind.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bind.dao.BindCbsContractCtbProcessingTradeDao;
import io.renren.modules.bind.dao.BindCbsCtbGoodsPartNoDao;
import io.renren.modules.bind.dao.BindCbsCustomsBrokerCtbCompanyDao;
import io.renren.modules.bind.entity.BindCbsContractCtbProcessingTradeEntity;
import io.renren.modules.bind.entity.BindCbsCtbGoodsPartNoEntity;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.renren.modules.bind.service.BindCbsContractCtbProcessingTradeService;
import io.renren.modules.cbs.dao.CbsContractDao;
import io.renren.modules.cbs.dao.CbsContractStatusStreamDao;
import io.renren.modules.cbs.dao.CbsDirectoryGoodsDao;
import io.renren.modules.cbs.dao.CbsGoodsPartNoDao;
import io.renren.modules.cbs.entity.*;
import io.renren.modules.ctb.dao.*;
import io.renren.modules.ctb.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service("bindCbsContractCtbProcessingTradeService")
public class BindCbsContractCtbProcessingTradeServiceImpl extends ServiceImpl<BindCbsContractCtbProcessingTradeDao, BindCbsContractCtbProcessingTradeEntity> implements BindCbsContractCtbProcessingTradeService {

    private BindCbsContractCtbProcessingTradeDao bindCbsContractCtbProcessingTradeDao;
    private BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao;
    private BindCbsCtbGoodsPartNoDao bindCbsCtbGoodsPartNoDao;
    private CbsContractDao cbsContractDao;
    private CbsContractStatusStreamDao cbsContractStatusStreamDao;
    private CtbOrderProcessingTradeDao ctbOrderProcessingTradeDao;
    private CtbOrderProcessingTradeStatusStreamDao ctbOrderProcessingTradeStatusStreamDao;
    private CtbOrderProcessingTradeGoodsDao ctbOrderProcessingTradeGoodsDao;
    private CbsGoodsPartNoDao cbsGoodsPartNoDao;
    private CtbGoodsPartNoDao ctbGoodsPartNoDao;
    private CbsDirectoryGoodsDao cbsDirectoryGoodsDao;
    private CtbDirectoryGoodsDao ctbDirectoryGoodsDao;
    private CtbImgOrderProcessingTradeDao ctbImgOrderProcessingTradeDao;
    private CtbDocumentControlDao ctbDocumentControlDao;
    private CtbOrderProcessingTradeMemberDao ctbOrderProcessingTradeMemberDao;
    private CtbPartnerDao ctbPartnerDao;
    private CtbPartnerTypeDao ctbPartnerTypeDao;

    public BindCbsContractCtbProcessingTradeServiceImpl(
            BindCbsContractCtbProcessingTradeDao bindCbsContractCtbProcessingTradeDao,
            BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao,
            BindCbsCtbGoodsPartNoDao bindCbsCtbGoodsPartNoDao,
            CbsContractDao cbsContractDao,
            CbsContractStatusStreamDao cbsContractStatusStreamDao,
            CtbOrderProcessingTradeDao ctbOrderProcessingTradeDao,
            CtbOrderProcessingTradeStatusStreamDao ctbOrderProcessingTradeStatusStreamDao,
            CtbOrderProcessingTradeGoodsDao ctbOrderProcessingTradeGoodsDao,
            CbsGoodsPartNoDao cbsGoodsPartNoDao,
            CtbGoodsPartNoDao ctbGoodsPartNoDao,
            CbsDirectoryGoodsDao cbsDirectoryGoodsDao,
            CtbDirectoryGoodsDao ctbDirectoryGoodsDao,
            CtbImgOrderProcessingTradeDao ctbImgOrderProcessingTradeDao,
            CtbDocumentControlDao ctbDocumentControlDao,
            CtbOrderProcessingTradeMemberDao ctbOrderProcessingTradeMemberDao,
            CtbPartnerDao ctbPartnerDao,
            CtbPartnerTypeDao ctbPartnerTypeDao
    ) {
        this.bindCbsContractCtbProcessingTradeDao = bindCbsContractCtbProcessingTradeDao;
        this.bindCbsCustomsBrokerCtbCompanyDao = bindCbsCustomsBrokerCtbCompanyDao;
        this.bindCbsCtbGoodsPartNoDao = bindCbsCtbGoodsPartNoDao;
        this.cbsContractDao = cbsContractDao;
        this.cbsContractStatusStreamDao = cbsContractStatusStreamDao;
        this.ctbOrderProcessingTradeDao = ctbOrderProcessingTradeDao;
        this.ctbOrderProcessingTradeStatusStreamDao = ctbOrderProcessingTradeStatusStreamDao;
        this.ctbOrderProcessingTradeGoodsDao = ctbOrderProcessingTradeGoodsDao;
        this.cbsGoodsPartNoDao = cbsGoodsPartNoDao;
        this.ctbGoodsPartNoDao = ctbGoodsPartNoDao;
        this.cbsDirectoryGoodsDao = cbsDirectoryGoodsDao;
        this.ctbDirectoryGoodsDao = ctbDirectoryGoodsDao;
        this.ctbImgOrderProcessingTradeDao = ctbImgOrderProcessingTradeDao;
        this.ctbDocumentControlDao = ctbDocumentControlDao;
        this.ctbOrderProcessingTradeMemberDao = ctbOrderProcessingTradeMemberDao;
        this.ctbPartnerDao = ctbPartnerDao;
        this.ctbPartnerTypeDao = ctbPartnerTypeDao;
    }

    @Override
    public PageUtils queryIndex(BindCbsContractCtbProcessingTradeEntity entity) {
        return null;
    }

    @Override
    @Transactional
    public HashMap<String, Object> cbsSynToCtb(Long cbsContractId, Long cbsCompanyId, Long ctbCompanyId, Long operator) {
        CbsContractEntity cbsContractEntity = cbsContractDao.detail(cbsContractId);
        HashMap<String, Object> resultMap = checkCbsContractGoods(cbsContractEntity);
        if (resultMap.containsKey("code")) {
            return resultMap;
        }

        BindCbsCustomsBrokerCtbCompanyEntity bindCbsCustomsBrokerCtbCompanyEntity = bindCbsCustomsBrokerCtbCompanyDao.detailByBothId(cbsCompanyId, ctbCompanyId);
        CtbOrderProcessingTradeEntity ctbOrderProcessingTradeEntity = null;
        BindCbsContractCtbProcessingTradeEntity bindCbsContractCtbProcessingTradeEntity = null;
        // 如果是第一次同步，创建Ctb实体和Bind实体
        if (cbsContractEntity.getBindEntity() == null) {
            // 创建Ctb实体
            ctbOrderProcessingTradeEntity = CtbOrderProcessingTradeEntity.builder().
                    title(cbsContractEntity.getTitle()).
                    type(cbsContractEntity.getType()).
                    currencyCode(cbsContractEntity.getCurrencyCode()).
                    fkServiceCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbServiceCompanyId()).
                    ctbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId()).
                    imgOrderProcessingTradeEntityList(new ArrayList<>()).
                    orderProcessingTradeGoodsList(new ArrayList<>()).
                    memberEntityList(new ArrayList<>()).
                    status(1).
                    operator(operator).
                    createTime(new Date()).
                    build();
            ctbOrderProcessingTradeDao.insertWithoutTenant(ctbOrderProcessingTradeEntity);
            // 创建statusStream
            ctbOrderProcessingTradeStatusStreamDao.insert(CtbOrderProcessingTradeStatusStreamEntity.builder()
                    .fkOrderProcessingTradeId(ctbOrderProcessingTradeEntity.getId())
                    .remark("企业委托创建")
                    .lastStatus(0)
                    .status(1)
                    .createTime(new Date())
                    .build());
            // 创建Bind实体
            bindCbsContractCtbProcessingTradeEntity = BindCbsContractCtbProcessingTradeEntity.builder().
                    fkCbsCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCbsCompanyId()).
                    fkCtbCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId()).
                    fkCbsContractId(cbsContractEntity.getId()).
                    fkCtbProcessingTradeId(ctbOrderProcessingTradeEntity.getId()).
                    isWorkByCtb(true).
                    build();
            bindCbsContractCtbProcessingTradeDao.insert(bindCbsContractCtbProcessingTradeEntity);
            // 合并到CbsContract中
            cbsContractEntity = cbsContractDao.detail(cbsContractId);
        } else {
            ctbOrderProcessingTradeEntity = cbsContractEntity.getBindEntity().getCtbOrderProcessingTradeEntity();
            // 创建statusStream
            ctbOrderProcessingTradeStatusStreamDao.insert(CtbOrderProcessingTradeStatusStreamEntity.builder()
                    .fkOrderProcessingTradeId(ctbOrderProcessingTradeEntity.getId())
                    .remark("企业同步数据")
                    .lastStatus(ctbOrderProcessingTradeEntity.getStatus())
                    .status(1)
                    .createTime(new Date())
                    .build());
            // 复制标题、类型、币制，更新
            ctbOrderProcessingTradeEntity.setTitle(cbsContractEntity.getTitle());
            ctbOrderProcessingTradeEntity.setFkServiceCompanyId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbServiceCompanyId());
            ctbOrderProcessingTradeEntity.setType(cbsContractEntity.getType());
            ctbOrderProcessingTradeEntity.setCurrencyCode(cbsContractEntity.getCurrencyCode());
            ctbOrderProcessingTradeEntity.setStatus(1);
            ctbOrderProcessingTradeDao.updateByIdWithoutTenant(ctbOrderProcessingTradeEntity);
            // 获取Bind
            bindCbsContractCtbProcessingTradeEntity = cbsContractEntity.getBindEntity();
            bindCbsContractCtbProcessingTradeEntity.setIsWorkByCtb(true);
            bindCbsContractCtbProcessingTradeDao.updateById(bindCbsContractCtbProcessingTradeEntity);
        }

        // 删除原有Ctb交易方（名录和交易方）
        for (CtbOrderProcessingTradeMemberEntity ctbOrderProcessingTradeMemberEntity : ctbOrderProcessingTradeEntity.getMemberEntityList()) {
            // 删除member
            ctbOrderProcessingTradeMemberDao.deleteById(ctbOrderProcessingTradeMemberEntity.getId());
        }
        // 同步Ctb交易方（名录和交易方）
        for (CbsContractMemberEntity cbsContractMemberEntity : cbsContractEntity.getMemberEntityList()) {
            // 名录
            CbsPartnerEntity cbsPartnerEntity = cbsContractMemberEntity.getPartnerEntity();
            CtbPartnerEntity ctbPartnerEntity = ctbPartnerDao.findSameToCbs(cbsPartnerEntity, bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
            if (ctbPartnerEntity == null) {
                ctbPartnerEntity = new CtbPartnerEntity(cbsPartnerEntity);
                ctbPartnerEntity.setCtbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
                ctbPartnerDao.insertWithoutTenant(ctbPartnerEntity);
                for (CbsPartnerTypeEntity cbsPartnerTypeEntity : cbsPartnerEntity.getTypeList()) {
                    CtbPartnerTypeEntity ctbPartnerTypeEntity = new CtbPartnerTypeEntity(cbsPartnerTypeEntity, ctbPartnerEntity.getId());
                    ctbPartnerTypeDao.insert(ctbPartnerTypeEntity);
                }
            }

            // 交易方
            CtbOrderProcessingTradeMemberEntity ctbOrderProcessingTradeMemberEntity = CtbOrderProcessingTradeMemberEntity.builder().
                    fkOrderProcessingTradeId(ctbOrderProcessingTradeEntity.getId()).
                    fkPartnerId(ctbPartnerEntity.getId()).
                    type(cbsContractMemberEntity.getType()).
                    ctbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId()).
                    build();
            ctbOrderProcessingTradeMemberDao.insertWithoutTenant(ctbOrderProcessingTradeMemberEntity);
        }

        // 同步合同商品
        List<CbsContractGoodsEntity> cbsContractGoodsEntityList = new ArrayList<>();
        for (CbsContractEntity cbsContractChildEntity : cbsContractEntity.getChildren()) {
            for (CbsContractGoodsEntity cbsContractGoodsEntity : cbsContractChildEntity.getContractGoodsList()) {
                if (cbsContractChildEntity.getType() == 11) {
                    cbsContractGoodsEntity.setType(1);
                } else if (cbsContractChildEntity.getType() == 12) {
                    cbsContractGoodsEntity.setType(2);
                } else {
                    cbsContractGoodsEntity.setType(3);
                }
                cbsContractGoodsEntityList.add(cbsContractGoodsEntity);
            }
        }
        // 1、删除原有绑定关系
        for (CtbOrderProcessingTradeGoodsEntity ctbOrderProcessingTradeGoodsEntity : ctbOrderProcessingTradeEntity.getOrderProcessingTradeGoodsList()) {
            // 删除加贸商品
            ctbOrderProcessingTradeGoodsDao.deleteById(ctbOrderProcessingTradeGoodsEntity.getId());
            // 删除料号
            ctbGoodsPartNoDao.deleteById(ctbOrderProcessingTradeGoodsEntity.getPartNoEntity().getId());
            if (ctbOrderProcessingTradeGoodsEntity.getPartNoEntity().getBindEntity() != null) {
                // 删除绑定关系
                bindCbsCtbGoodsPartNoDao.deleteById(ctbOrderProcessingTradeGoodsEntity.getPartNoEntity().getBindEntity().getId());
            }
        }
        for (CbsContractGoodsEntity cbsGoodsEntity : cbsContractGoodsEntityList) {
            CbsGoodsPartNoEntity cbsGoodsPartNoEntity = cbsGoodsEntity.getPartNoEntity();
            // 2、创建新绑定关系
            // 创建商品名录
            CtbDirectoryGoodsEntity ctbDirectoryGoodsEntity = CtbDirectoryGoodsEntity.builder().build();
            BeanUtils.copyProperties(cbsGoodsPartNoEntity.getGoodsEntity(), ctbDirectoryGoodsEntity);
            ctbDirectoryGoodsEntity.setCtbTenantId(bindCbsCustomsBrokerCtbCompanyEntity.getFkCtbCompanyId());
            ctbDirectoryGoodsDao.insertWithoutTenant(ctbDirectoryGoodsEntity);
            // 创建加贸关联商品
            CtbOrderProcessingTradeGoodsEntity ctbOrderProcessingTradeGoodsEntity = CtbOrderProcessingTradeGoodsEntity.builder().
                    fkOrderProcessingTradeId(ctbOrderProcessingTradeEntity.getId()).
                    type(cbsGoodsEntity.getType()).
                    build();
            ctbOrderProcessingTradeGoodsDao.insertWithoutTenant(ctbOrderProcessingTradeGoodsEntity);
            // 创建新料号
            CtbGoodsPartNoEntity ctbGoodsPartNoEntity = CtbGoodsPartNoEntity.builder().build();
            BeanUtils.copyProperties(cbsGoodsPartNoEntity, ctbGoodsPartNoEntity);
            ctbGoodsPartNoEntity.setFkGoodsId(ctbDirectoryGoodsEntity.getId());
            ctbGoodsPartNoEntity.setGoodsPartNo(ctbOrderProcessingTradeGoodsEntity.getId());
            ctbGoodsPartNoDao.insertWithoutTenant(ctbGoodsPartNoEntity);
            // 创建绑定关系
            BindCbsCtbGoodsPartNoEntity bindCbsCtbGoodsPartNoEntity = BindCbsCtbGoodsPartNoEntity.builder().
                    fkCbsGoodsPartNo(cbsGoodsPartNoEntity.getGoodsPartNo()).
                    fkCtbGoodsPartNo(ctbGoodsPartNoEntity.getGoodsPartNo()).
                    build();
            bindCbsCtbGoodsPartNoDao.insert(bindCbsCtbGoodsPartNoEntity);
        }

        // 删除合同附件
        ctbImgOrderProcessingTradeDao.deleteOrderByIdWithoutTenant(ctbOrderProcessingTradeEntity.getId());
        ctbDocumentControlDao.deleteWithoutTenantId(CtbDocumentControlEntity.builder().
                businessTable("ctb_img_order_processing_trade").
                businessId(ctbOrderProcessingTradeEntity.getId()).
                build());
        // 更新合同附件
        List<CbsImgContractEntity> cbsImgContractEntityList = new ArrayList<>();
        for (CbsContractEntity cbsContractChildEntity : cbsContractEntity.getChildren()) {
            for (CbsImgContractEntity cbsImgContractEntity : cbsContractChildEntity.getImgContractEntityList()) {
                if (cbsContractChildEntity.getType() == 11) {
                    cbsImgContractEntity.setType(1);
                } else if (cbsContractChildEntity.getType() == 12) {
                    cbsImgContractEntity.setType(2);
                } else {
                    cbsImgContractEntity.setType(3);
                }
                cbsImgContractEntityList.add(cbsImgContractEntity);
            }
        }
        for (CbsImgContractEntity cbsImgContractEntity : cbsImgContractEntityList) {
            CtbImgOrderProcessingTradeEntity ctbImgOrderProcessingTradeEntity = CtbImgOrderProcessingTradeEntity.builder().
                    imgUrl(cbsImgContractEntity.getImgUrl()).
                    type(cbsImgContractEntity.getType()).
                    build();
            ctbImgOrderProcessingTradeEntity.setFkOrderProcessingTradeId(ctbOrderProcessingTradeEntity.getId());
            ctbImgOrderProcessingTradeDao.insert(ctbImgOrderProcessingTradeEntity);
            ctbDocumentControlDao.insert(CtbDocumentControlEntity.builder().
                    businessTable("ctb_img_order_processing_trade").
                    businessId(ctbOrderProcessingTradeEntity.getId()).
                    businessFile(ctbImgOrderProcessingTradeEntity.getImgUrl()).
                    build());
        }
        resultMap = new HashMap<>();
        resultMap.put("code", 200);
        resultMap.put("bindId", bindCbsContractCtbProcessingTradeEntity.getId());
        return resultMap;
    }

    @Override
    @Transactional
    public Long ctbSynToCbs(Long ctbOrderProcessingTradeId) {
        CtbOrderProcessingTradeEntity ctbOrderProcessingTradeEntity = ctbOrderProcessingTradeDao.detail(ctbOrderProcessingTradeId);
        BindCbsContractCtbProcessingTradeEntity bindCbsContractCtbProcessingTradeEntity = ctbOrderProcessingTradeEntity.getBindEntity();
        // 更新手册预录入统一编号，以及手册备案编号
        CbsContractEntity cbsContractEntity = bindCbsContractCtbProcessingTradeEntity.getCbsContractEntity();
        cbsContractStatusStreamDao.insert(CbsContractStatusStreamEntity.builder()
                .fkContractId(cbsContractEntity.getId())
                .lastStatus(cbsContractEntity.getStatus())
                .status(2)
                .createTime(new Date())
                .remark("报关行同步反馈")
                .build());
        cbsContractEntity.setEmlNo(ctbOrderProcessingTradeEntity.getEmlNo());
        cbsContractEntity.setSeqNo(ctbOrderProcessingTradeEntity.getSeqNo());
        cbsContractEntity.setStatus(2);
        cbsContractDao.updateByIdWithoutTenant(cbsContractEntity);
        // 商品的复制原则：
        // 1、Ctb中存在的料号，必定是已绑定了Cbs已存在料号
        // 2、Ctb复制到Cbs不允许修改Cbs商品的数量、价格、总价
        // 3、Ctb复制到Cbs允许修改Cbs商品的税号
        for (CtbOrderProcessingTradeGoodsEntity ctbGoodsEntity : ctbOrderProcessingTradeEntity.getOrderProcessingTradeGoodsList()) {
            CtbGoodsPartNoEntity ctbGoodsPartNoEntity = ctbGoodsEntity.getPartNoEntity();
            CbsGoodsPartNoEntity cbsGoodsPartNoEntity = ctbGoodsPartNoEntity.getBindEntity().getCbsGoodsPartNoEntity();
            if (!ctbGoodsPartNoEntity.getGoodsEntity().getHsCode().equals(cbsGoodsPartNoEntity.getGoodsEntity().getHsCode())) {
                // 如果税号被变更，创建一个新的CbsDirectoryGoods
                CbsDirectoryGoodsEntity newCbsDirectoryGoodsEntity = CbsDirectoryGoodsEntity.builder().build();
                BeanUtils.copyProperties(ctbGoodsPartNoEntity.getGoodsEntity(), newCbsDirectoryGoodsEntity);
                newCbsDirectoryGoodsEntity.setTenantId(bindCbsContractCtbProcessingTradeEntity.getFkCbsCompanyId());
                cbsDirectoryGoodsDao.insertWithoutTenant(newCbsDirectoryGoodsEntity);
                // 更新料号中的新税号商品
                cbsGoodsPartNoEntity.setFkGoodsId(newCbsDirectoryGoodsEntity.getId());
                cbsGoodsPartNoDao.updateById(cbsGoodsPartNoEntity);
            }
        }
        bindCbsContractCtbProcessingTradeEntity.setIsWorkByCtb(false);
        bindCbsContractCtbProcessingTradeDao.updateById(bindCbsContractCtbProcessingTradeEntity);
        // 全部更新后重新查询一次获得最新的Bind实体
        return bindCbsContractCtbProcessingTradeEntity.getId();
    }

    @Override
    public BindCbsContractCtbProcessingTradeEntity detail(Long id) {
        return null;
    }

    private HashMap<String, Object> checkCbsContractGoods(CbsContractEntity cbsContractEntity) {
        HashMap<String, Object> resultMap = new HashMap<>();
        for (CbsContractEntity cbsChildContractEntity : cbsContractEntity.getChildren()) {
            if (cbsChildContractEntity.getType() == 11 || cbsChildContractEntity.getType() == 12) {
                if ((cbsChildContractEntity.getContractGoodsList() == null || cbsChildContractEntity.getContractGoodsList().isEmpty())) {
                    resultMap.put("code", 500);
                    resultMap.put("msg", "请先编辑合同进出口商品，报关行只有权对合同商品进行税号核对操作，不会对合同商品价格数量等内容进行操作，请知悉。");
                } else {
                    for (CbsContractGoodsEntity cbsContractGoodsEntity : cbsChildContractEntity.getContractGoodsList()) {
                        if (!checkCbsContractGoods(cbsContractGoodsEntity)) {
                            resultMap.put("code", 500);
                            resultMap.put("msg", "请先编辑合同进出口商品，合同进口或出口商品数量必须大于0，报关行只有权对合同商品进行税号核对操作，不会对合同商品价格数量等内容进行操作，请知悉。");
                        }
                    }
                }
            }
        }
        return resultMap;
    }

    private boolean checkCbsContractGoods(CbsContractGoodsEntity cbsContractGoodsEntity) {
        return cbsContractGoodsEntity.getPartNoEntity().getContractCount().compareTo(BigDecimal.ZERO) > 0;
    }
}