package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.enumeration.MoneyEnum;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.ctb.dao.CtbImgMoneyInDao;
import io.renren.modules.ctb.dao.CtbMoneyInDao;
import io.renren.modules.ctb.entity.CtbImgMoneyInEntity;
import io.renren.modules.ctb.entity.CtbMoneyInEntity;
import io.renren.modules.ctb.service.CtbMoneyInService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("ctbMoneyInService")
public class CtbMoneyInServiceImpl extends ServiceImpl<CtbMoneyInDao, CtbMoneyInEntity> implements CtbMoneyInService {

    private CtbMoneyInDao ctbMoneyInDao;
    private CtbImgMoneyInDao ctbImgMoneyInDao;

    public CtbMoneyInServiceImpl(CtbMoneyInDao ctbMoneyInDao, CtbImgMoneyInDao ctbImgMoneyInDao) {
        this.ctbMoneyInDao = ctbMoneyInDao;
        this.ctbImgMoneyInDao = ctbImgMoneyInDao;
    }

    @Override
    public PageUtils queryPage(CtbMoneyInEntity entity) {
        IPage<CtbMoneyInEntity> page = ctbMoneyInDao.queryIndex(new QueryPage<CtbMoneyInEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }


    @Override
    @Transactional
    public void updateMoneyImg(CtbMoneyInEntity ctbMoneyInEntity) {
        CtbMoneyInEntity entity = ctbMoneyInDao.selectById(ctbMoneyInEntity.getId());
        // 状态1或3才可以继续
        if (MoneyEnum.CONFIRM_STATUS_1.getCode() == entity.getStatus() || MoneyEnum.CONFIRM_STATUS_3.getCode() == entity.getStatus()) {
            // 先删除后插入 ctb_img_money_in
            ctbImgMoneyInDao.delete(new QueryWrapper<CtbImgMoneyInEntity>().eq("fk_money_in_id", entity.getId()));
            for (CtbImgMoneyInEntity ctbImgMoneyInEntity : ctbMoneyInEntity.getImgMoneyInList()) {
                ctbImgMoneyInEntity.setFkMoneyInId(entity.getId());
                ctbImgMoneyInDao.insert(ctbImgMoneyInEntity);
            }
            // 更新 cbs_money_in 状态为3
            ctbMoneyInDao.updateById(CtbMoneyInEntity.builder().id(entity.getId()).status(MoneyEnum.CONFIRM_STATUS_3.getCode()).build());
        } else {
            throw new RRException("状态不符合要求,请确核后操作");
        }
    }

    @Override
    public void confirmAnnex(CtbMoneyInEntity ctbMoneyInEntity) {
        CtbMoneyInEntity entity = ctbMoneyInDao.selectById(ctbMoneyInEntity.getId());
        if (MoneyEnum.CONFIRM_STATUS_3.getCode() == entity.getStatus()) {
            ctbMoneyInDao.updateById(CtbMoneyInEntity.builder().id(entity.getId()).status(MoneyEnum.CONFIRM_STATUS_4.getCode()).build());
        } else {
            throw new RRException("状态不符合要求，请先上传附件");
        }
    }


}