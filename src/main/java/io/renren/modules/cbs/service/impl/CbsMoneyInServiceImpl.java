package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.enumeration.DocumentControlEnum;
import io.renren.common.enumeration.MoneyEnum;
import io.renren.common.exception.RRException;
import io.renren.common.utils.CommonFunction;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsImgMoneyInDao;
import io.renren.modules.cbs.dao.CbsMoneyInDao;
import io.renren.modules.cbs.entity.CbsImgMoneyInEntity;
import io.renren.modules.cbs.entity.CbsMoneyInEntity;
import io.renren.modules.cbs.service.CbsMoneyInService;
import io.renren.modules.cbs.vo.ContractVo;
import io.renren.modules.cbs.vo.UnifiedUploadingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("cbsMoneyInService")
public class CbsMoneyInServiceImpl extends ServiceImpl<CbsMoneyInDao, CbsMoneyInEntity> implements CbsMoneyInService {

    private CbsMoneyInDao moneyInDao;
    private CbsImgMoneyInDao cbsImgMoneyInDao;
    private CommonFunction commonFunction;

    public CbsMoneyInServiceImpl(CbsImgMoneyInDao cbsImgMoneyInDao, CommonFunction commonFunction) {
        this.cbsImgMoneyInDao = cbsImgMoneyInDao;
        this.commonFunction = commonFunction;
    }

    @Autowired
    public void setMoneyInDao(CbsMoneyInDao moneyInDao) {
        this.moneyInDao = moneyInDao;
    }

    @Override
    public PageUtils queryIndex(CbsMoneyInEntity entity) {
        IPage<CbsMoneyInEntity> page = moneyInDao.queryIndex(new QueryPage<CbsMoneyInEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    public CbsMoneyInEntity getMapById(Long id) {
        CbsMoneyInEntity entity = CbsMoneyInEntity.builder().id(id).searchForm(ContractVo.builder().build()).build();
        IPage<CbsMoneyInEntity> page = moneyInDao.queryIndex(new QueryPage<CbsMoneyInEntity>().getPage(entity), entity);
        return page.getRecords().get(0);
    }

    @Override
    public CbsMoneyInEntity simpleDetailById(Long id) {
        return moneyInDao.simpleDetailById(id);
    }

    @Override
    @Transactional
    public void updateMoneyImg(CbsMoneyInEntity cbsMoneyInEntity) {
        CbsMoneyInEntity entity = moneyInDao.selectById(cbsMoneyInEntity.getId());
        // 状态2或3才可以继续
        if (MoneyEnum.CONFIRM_STATUS_2.getCode() == entity.getStatus() || MoneyEnum.CONFIRM_STATUS_3.getCode() == entity.getStatus()) {
            // 先删除后插入cbs_img_money_in
            /*cbsImgMoneyInDao.delete(new QueryWrapper<CbsImgMoneyInEntity>().eq("fk_money_in_id", entity.getId()));
            for (CbsImgMoneyInEntity cbsImgMoneyInEntity : cbsMoneyInEntity.getImgMoneyInList()) {
                cbsImgMoneyInEntity.setFkMoneyInId(entity.getId());
                cbsImgMoneyInDao.insert(cbsImgMoneyInEntity);
            }*/
            List<String> imgList = new ArrayList<>();
            for (CbsImgMoneyInEntity cbsImgMoneyInEntity : cbsMoneyInEntity.getImgMoneyInList()) {
                imgList.add(cbsImgMoneyInEntity.getImgUrl());
            }
            commonFunction.unifiedUploading(UnifiedUploadingVo.builder()
                    .tableName(DocumentControlEnum.TYPE_MONEY_IN.getCode())
                    .contractId(entity.getFkContractId())
                    .field("fk_money_in_id")
                    .fieldValue(entity.getId())
                    .imgList(imgList)
                    .build());
            // 更新cbs_money_in状态为3
            moneyInDao.updateById(CbsMoneyInEntity.builder().id(entity.getId()).status(MoneyEnum.CONFIRM_STATUS_3.getCode()).build());
        } else {
            throw new RRException("状态不符合要求,请确核后操作");
        }
    }

    @Override
    public void confirmAnnex(CbsMoneyInEntity cbsMoneyInEntity) {
        CbsMoneyInEntity entity = moneyInDao.selectById(cbsMoneyInEntity.getId());
        if (MoneyEnum.CONFIRM_STATUS_3.getCode() == entity.getStatus()) {
            moneyInDao.updateById(CbsMoneyInEntity.builder().id(entity.getId()).status(MoneyEnum.CONFIRM_STATUS_4.getCode()).build());
        } else {
            throw new RRException("状态不符合要求，请先上传附件");
        }
    }

    @Override
    public PageUtils financeList(CbsMoneyInEntity entity) {
        IPage<CbsMoneyInEntity> page = moneyInDao.financeList(new QueryPage<CbsMoneyInEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }
}