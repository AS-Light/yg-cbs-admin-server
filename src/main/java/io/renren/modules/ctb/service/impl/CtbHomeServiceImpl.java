package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.cbs.entity.StatisticalDigital;
import io.renren.modules.ctb.dao.CtbHomeDao;
import io.renren.modules.ctb.entity.CtbHomeEntity;
import io.renren.modules.ctb.service.CtbHomeService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ctbHomeService")
public class CtbHomeServiceImpl extends ServiceImpl<CtbHomeDao, CtbHomeEntity> implements CtbHomeService {

    private CtbHomeDao ctbHomeDao;

    public CtbHomeServiceImpl(CtbHomeDao ctbHomeDao) {
        this.ctbHomeDao = ctbHomeDao;
    }

    @Override
    public CtbHomeEntity total(Long tenantId) {
        CtbHomeEntity home = new CtbHomeEntity();
        home.setCanadaTrade(ctbHomeDao.selectTotal(tenantId, "ctb_order_processing_trade", 4));
        home.setApplyToCustomsIn(ctbHomeDao.selectTotal(tenantId, "ctb_import", 14));
        home.setApplyToCustomsOut(ctbHomeDao.selectTotal(tenantId, "ctb_export", 14));
        home.setTotalRevenue(ctbHomeDao.selectTotalInOut(tenantId, "ctb_money_in"));
        return home;
    }

    @Override
    public CtbHomeEntity actual(Long tenantId, String[] months) {
        List<StatisticalDigital> incomeList = ctbHomeDao.incomeExpendList(tenantId, months, "ctb_money_in");
        List<StatisticalDigital> expendList = ctbHomeDao.incomeExpendList(tenantId, months, "ctb_money_out");
        CtbHomeEntity ctbHomeEntity = new CtbHomeEntity();
        ctbHomeEntity.setIncomeList(incomeList);
        ctbHomeEntity.setExpendList(expendList);
        return ctbHomeEntity;
    }

    @Override
    public CtbHomeEntity canadaTradeApplyToCustoms(Long tenantId, String[] months) {
        List<StatisticalDigital> orderProcessingTradeList = ctbHomeDao.canadaTradeApplyToCustoms(tenantId, months, "ctb_order_processing_trade");
        List<StatisticalDigital> importList = ctbHomeDao.canadaTradeApplyToCustoms(tenantId, months, "ctb_import");
        List<StatisticalDigital> exportList = ctbHomeDao.canadaTradeApplyToCustoms(tenantId, months, "ctb_export");
        CtbHomeEntity ctbHomeEntity = new CtbHomeEntity();
        ctbHomeEntity.setCanadaTradeArray(orderProcessingTradeList);
        ctbHomeEntity.setImportArray(importList);
        ctbHomeEntity.setExportArray(exportList);
        return ctbHomeEntity;
    }
}