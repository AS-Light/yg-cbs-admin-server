package io.renren.modules.bind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.enumeration.BindEnum;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bind.dao.BindCbsCustomsBrokerCtbCompanyDao;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.renren.modules.bind.service.BindCbsCustomsBrokerCtbCompanyService;
import io.renren.modules.ctb.dao.CtbDirectoryServiceCompanyDao;
import io.renren.modules.ctb.dao.CtbImgServiceContractDao;
import io.renren.modules.ctb.dao.CtbServiceContractDao;
import io.renren.modules.ctb.entity.CtbDirectoryServiceCompanyEntity;
import io.renren.modules.ctb.entity.CtbImgServiceContractEntity;
import io.renren.modules.ctb.entity.CtbServiceContractEntity;
import io.renren.modules.org_cbs.entity.OrgCbsCompanyEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("bindCbsCustomsBrokerCtbCompanyService")
public class BindCbsCustomsBrokerCtbCompanyServiceImpl extends ServiceImpl<BindCbsCustomsBrokerCtbCompanyDao, BindCbsCustomsBrokerCtbCompanyEntity> implements BindCbsCustomsBrokerCtbCompanyService {


    private BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao;
    private CtbServiceContractDao ctbServiceContractDao;
    private CtbDirectoryServiceCompanyDao ctbDirectoryServiceCompanyDao;
    private CtbImgServiceContractDao ctbImgServiceContractDao;

    public BindCbsCustomsBrokerCtbCompanyServiceImpl(
            BindCbsCustomsBrokerCtbCompanyDao bindCbsCustomsBrokerCtbCompanyDao,
            CtbServiceContractDao ctbServiceContractDao,
            CtbDirectoryServiceCompanyDao ctbDirectoryServiceCompanyDao,
            CtbImgServiceContractDao ctbImgServiceContractDao
    ) {
        this.bindCbsCustomsBrokerCtbCompanyDao = bindCbsCustomsBrokerCtbCompanyDao;
        this.ctbServiceContractDao = ctbServiceContractDao;
        this.ctbDirectoryServiceCompanyDao = ctbDirectoryServiceCompanyDao;
        this.ctbImgServiceContractDao = ctbImgServiceContractDao;
    }

    @Override
    public PageUtils queryIndex(BindCbsCustomsBrokerCtbCompanyEntity entity) {
        return null;
    }

    @Override
    public BindCbsCustomsBrokerCtbCompanyEntity detail(Long id) {
        return bindCbsCustomsBrokerCtbCompanyDao.detail(id);
    }

    @Override
    @Transactional
    public Long createBind(OrgCbsCompanyEntity cbsCompanyEntity, Long cbsDirectoryId, Long ctbCompanyId) {
        CtbDirectoryServiceCompanyEntity ctbDirectoryServiceCompanyEntity = CtbDirectoryServiceCompanyEntity.builder().build();
        BeanUtils.copyProperties(cbsCompanyEntity, ctbDirectoryServiceCompanyEntity);
        ctbDirectoryServiceCompanyEntity.setCtbTenantId(ctbCompanyId);
        ctbDirectoryServiceCompanyDao.insertWithoutTenant(ctbDirectoryServiceCompanyEntity);

        BindCbsCustomsBrokerCtbCompanyEntity bindCbsCustomsBrokerCtbCompanyEntity = BindCbsCustomsBrokerCtbCompanyEntity.builder()
                .fkCbsCompanyId(cbsCompanyEntity.getId())
                .fkCtbCompanyId(ctbCompanyId)
                .fkCbsCustomBrokerId(cbsDirectoryId)
                .fkCtbServiceCompanyId(ctbDirectoryServiceCompanyEntity.getId())
                .build();
        bindCbsCustomsBrokerCtbCompanyDao.insert(bindCbsCustomsBrokerCtbCompanyEntity);
        return bindCbsCustomsBrokerCtbCompanyEntity.getId();
    }

    @Override
    public CtbServiceContractEntity selectServiceContract(BindCbsCustomsBrokerCtbCompanyEntity entity) {
        QueryWrapper<BindCbsCustomsBrokerCtbCompanyEntity> qw = new QueryWrapper<>();
        qw.eq("fk_ctb_service_company_id", entity.getFkCtbServiceCompanyId());
        qw.eq("fk_ctb_company_id", entity.getFkCtbCompanyId());
        BindCbsCustomsBrokerCtbCompanyEntity bind = bindCbsCustomsBrokerCtbCompanyDao.selectOne(qw);
        if (bind != null && bind.getFkCtbServiceContractId() != null) {
            return ctbServiceContractDao.selectById(bind.getFkCtbServiceContractId());
        } else {
            throw new RRException("暂无绑定关系");
        }
    }

    @Override
    public void initiateConfirmation(BindCbsCustomsBrokerCtbCompanyEntity entity) {
        QueryWrapper<BindCbsCustomsBrokerCtbCompanyEntity> qw = new QueryWrapper<>();
        qw.eq("fk_ctb_service_company_id", entity.getFkCtbServiceCompanyId());
        qw.eq("fk_ctb_company_id", entity.getFkCtbCompanyId());
        BindCbsCustomsBrokerCtbCompanyEntity bind = bindCbsCustomsBrokerCtbCompanyDao.selectOne(qw);
        if (bind != null && bind.getFkCtbServiceContractId() != null) {
            bindCbsCustomsBrokerCtbCompanyDao.updateById(BindCbsCustomsBrokerCtbCompanyEntity.builder().id(bind.getId()).status(BindEnum.STATUS_2.getCode()).build());
        } else {
            throw new RRException("没有合同");
        }
    }

    @Override
    @Transactional
    public void confirmContract(BindCbsCustomsBrokerCtbCompanyEntity entity) {
        QueryWrapper<BindCbsCustomsBrokerCtbCompanyEntity> qw = new QueryWrapper<>();
        qw.eq("fk_ctb_service_company_id", entity.getFkCtbServiceCompanyId());
        qw.eq("fk_ctb_company_id", entity.getFkCtbCompanyId());
        BindCbsCustomsBrokerCtbCompanyEntity bindEntity = bindCbsCustomsBrokerCtbCompanyDao.selectOne(qw);
        List<CtbImgServiceContractEntity> serviceContractEntityList = ctbImgServiceContractDao.selectList(new QueryWrapper<CtbImgServiceContractEntity>().eq("fk_service_contract_id", bindEntity.getFkCtbServiceCompanyId()));
        if (bindEntity != null && bindEntity.getFkCtbServiceContractId() != null && serviceContractEntityList != null && !serviceContractEntityList.isEmpty()) {
            bindCbsCustomsBrokerCtbCompanyDao.updateById(BindCbsCustomsBrokerCtbCompanyEntity.builder().id(bindEntity.getId()).status(BindEnum.STATUS_4.getCode()).build());
        } else {
            throw new RRException("没有合同");
        }
    }
}