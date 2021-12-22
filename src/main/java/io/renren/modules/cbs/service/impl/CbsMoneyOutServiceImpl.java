package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.enumeration.DocumentControlEnum;
import io.renren.common.enumeration.MoneyEnum;
import io.renren.common.exception.RRException;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsImgMoneyOutDao;
import io.renren.modules.cbs.dao.CbsMoneyOutDao;
import io.renren.modules.cbs.entity.CbsImgMoneyOutEntity;
import io.renren.modules.cbs.entity.CbsMoneyOutEntity;
import io.renren.modules.cbs.service.CbsMoneyOutService;
import io.renren.modules.cbs.vo.ContractVo;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("cbsMoneyOutService")
public class CbsMoneyOutServiceImpl extends ServiceImpl<CbsMoneyOutDao, CbsMoneyOutEntity> implements CbsMoneyOutService {

    private CbsMoneyOutDao moneyOutDao;
    private CbsImgMoneyOutDao cbsImgMoneyOutDao;
    private CommonFunction commonFunction;

    public CbsMoneyOutServiceImpl(CbsImgMoneyOutDao cbsImgMoneyOutDao, CommonFunction commonFunction) {
        this.cbsImgMoneyOutDao = cbsImgMoneyOutDao;
        this.commonFunction = commonFunction;
    }

    @Autowired
    public void setMoneyOutDao(CbsMoneyOutDao moneyOutDao) {
        this.moneyOutDao = moneyOutDao;
    }

    @Override
    public PageUtils queryIndex(CbsMoneyOutEntity entity) {
        IPage<CbsMoneyOutEntity> page = moneyOutDao.queryIndex(new QueryPage<CbsMoneyOutEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public CbsMoneyOutEntity getMapById(Long id) {
        CbsMoneyOutEntity entity = CbsMoneyOutEntity.builder().id(id).searchForm(ContractVo.builder().build()).build();
        IPage<CbsMoneyOutEntity> page = moneyOutDao.queryIndex(new QueryPage<CbsMoneyOutEntity>().getPage(entity), entity);
        return page.getRecords().get(0);
    }

    @Override
    public CbsMoneyOutEntity simpleDetailById(Long id) {
        return moneyOutDao.simpleDetailById(id);
    }

    @Override
    @Transactional
    public void updateMoneyImg(CbsMoneyOutEntity CbsMoneyOutEntity) {
        CbsMoneyOutEntity entity = moneyOutDao.selectById(CbsMoneyOutEntity.getId());
        // 状态2或3才可以继续
        if (MoneyEnum.CONFIRM_STATUS_2.getCode() == entity.getStatus() || MoneyEnum.CONFIRM_STATUS_3.getCode() == entity.getStatus()) {
            // 先删除后插入cbs_img_money_in
            /*cbsImgMoneyOutDao.delete(new QueryWrapper<CbsImgMoneyOutEntity>().eq("fk_money_out_id", entity.getId()));
            for (CbsImgMoneyOutEntity cbsImgMoneyOutEntity : CbsMoneyOutEntity.getImgMoneyOutList()) {
                cbsImgMoneyOutEntity.setFkMoneyOutId(entity.getId());
                cbsImgMoneyOutDao.insert(cbsImgMoneyOutEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgMoneyOutEntity cbsImgMoneyOutEntity : CbsMoneyOutEntity.getImgMoneyOutList()) {
                imgList.add(cbsImgMoneyOutEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_MONEY_OUT.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_money_out_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
            // 更新cbs_money_in状态为3
            moneyOutDao.updateById(CbsMoneyOutEntity.builder().id(entity.getId()).status(MoneyEnum.CONFIRM_STATUS_3.getCode()).build());
        } else {
            throw new RRException("状态不符合要求,请确核后操作");
        }
    }

    @Override
    public void confirmAnnex(CbsMoneyOutEntity CbsMoneyOutEntity) {
        CbsMoneyOutEntity entity = moneyOutDao.selectById(CbsMoneyOutEntity.getId());
        if (MoneyEnum.CONFIRM_STATUS_3.getCode() == entity.getStatus()) {
            moneyOutDao.updateById(CbsMoneyOutEntity.builder().id(entity.getId()).status(MoneyEnum.CONFIRM_STATUS_4.getCode()).build());
        } else {
            throw new RRException("状态不符合要求，请先上传附件");
        }
    }
}