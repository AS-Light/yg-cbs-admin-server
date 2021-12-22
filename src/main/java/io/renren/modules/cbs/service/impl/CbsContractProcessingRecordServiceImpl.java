package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.cbs.dao.CbsContractProcessingRecordDao;
import io.renren.modules.cbs.dao.CbsImgContractProcessingRecordDao;
import io.renren.modules.cbs.entity.CbsContractProcessingRecordEntity;
import io.renren.modules.cbs.entity.CbsImgContractProcessingRecordEntity;
import io.renren.modules.cbs.service.CbsContractProcessingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("CbsContractProcessingRecordService")
public class CbsContractProcessingRecordServiceImpl extends ServiceImpl<CbsContractProcessingRecordDao, CbsContractProcessingRecordEntity> implements CbsContractProcessingRecordService {

    @Autowired
    private CbsContractProcessingRecordDao ContractProcessingRecordDao;

    @Autowired
    private CbsImgContractProcessingRecordDao imgContractProcessingRecordDao;

    @Override
    public PageUtils queryPage(CbsContractProcessingRecordEntity entity) {
        QueryWrapper qw = new QueryWrapper<CbsContractProcessingRecordEntity>();
        IPage<CbsContractProcessingRecordEntity> page = this.page(new QueryPage<CbsContractProcessingRecordEntity>().getPage(entity), qw);
        List<CbsContractProcessingRecordEntity> list = page.getRecords();
        for (CbsContractProcessingRecordEntity c : list) {
            futureTaskOne(c);
        }
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public CbsContractProcessingRecordEntity selectById(Long id) {
        CbsContractProcessingRecordEntity contract = ContractProcessingRecordDao.selectById(id);
        return futureTaskOne(contract);
    }

    /**
     * @Description: 根据合同ID查询备案实体，当前是1对1，后期可能改
     * @Author: ChenNing
     * @Date: 11:26 2019/8/16
     */
    @Override
    public CbsContractProcessingRecordEntity selectByContractId(Long id) {
        CbsContractProcessingRecordEntity contract = ContractProcessingRecordDao.selectOne(new QueryWrapper<CbsContractProcessingRecordEntity>().eq("fk_contract_id", id));
        if (contract != null) {
            return futureTaskOne(contract);
        } else {
            return null;
        }
    }

    /**
     * @Description: 备案多线程处理：备案图片
     * @Author: ChenNing
     * @Date: 14:53 2019/8/15
     */
    public CbsContractProcessingRecordEntity futureTaskOne(CbsContractProcessingRecordEntity c) {
        List<CbsImgContractProcessingRecordEntity> imgContractProcessingRecordList = imgContractProcessingRecordDao.selectList(new QueryWrapper<CbsImgContractProcessingRecordEntity>().eq("fk_contract_processing_record_id", c.getId()));
        c.setImgContractProcessingRecordList(imgContractProcessingRecordList);
        return c;
    }


}