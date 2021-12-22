package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.ctb.dao.CtbImgServiceContractDao;
import io.renren.modules.ctb.entity.CtbImgServiceContractEntity;
import io.renren.modules.ctb.service.CtbImgServiceContractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("ctbImgServiceContractService")
public class CtbImgServiceContractServiceImpl extends ServiceImpl<CtbImgServiceContractDao, CtbImgServiceContractEntity> implements CtbImgServiceContractService {

    private CtbImgServiceContractDao ctbImgServiceContractDao;

    public CtbImgServiceContractServiceImpl(CtbImgServiceContractDao ctbImgServiceContractDao) {
        this.ctbImgServiceContractDao = ctbImgServiceContractDao;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbImgServiceContractEntity> page = this.page(
                new Query<CtbImgServiceContractEntity>().getPage(params),
                new QueryWrapper<CtbImgServiceContractEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CtbImgServiceContractEntity> contractAnnexInfo(Long id) {
        return ctbImgServiceContractDao.selectList(new QueryWrapper<CtbImgServiceContractEntity>().eq("fk_service_contract_id", id));
    }

}