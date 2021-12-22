package io.renren.modules.cbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.enumeration.ContractEnum;
import io.renren.modules.cbs.dao.CbsHomeDao;
import io.renren.modules.cbs.entity.CbsHomeEntity;
import io.renren.modules.cbs.entity.StatisticalContract;
import io.renren.modules.cbs.entity.StatisticalDigital;
import io.renren.modules.cbs.service.CbsHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("cbsHomeService")
public class CbsHomeServiceImpl extends ServiceImpl<CbsHomeDao, CbsHomeEntity> implements CbsHomeService {

    @Autowired
    private CbsHomeDao cbsHomeDao;

    @Override
    public CbsHomeEntity total(Long tenantId) {
        List<StatisticalDigital> contractList = cbsHomeDao.selectTotalList(tenantId);
        CbsHomeEntity home = new CbsHomeEntity();
        // 组织合同的相关总数
        for (StatisticalDigital statisticalDigital : contractList) {
            if (ContractEnum.CONTRACT_TYPE_1.getCode() == statisticalDigital.getType()) {
                home.setContractFeed(statisticalDigital);
            } else if (ContractEnum.CONTRACT_TYPE_2.getCode() == statisticalDigital.getType()) {
                home.setContractIncoming(statisticalDigital);
            } else if (ContractEnum.CONTRACT_TYPE_3.getCode() == statisticalDigital.getType()) {
                home.setContractImport(statisticalDigital);
            } else if (ContractEnum.CONTRACT_TYPE_4.getCode() == statisticalDigital.getType()) {
                home.setContractExport(statisticalDigital);
            } else if (ContractEnum.CONTRACT_TYPE_5.getCode() == statisticalDigital.getType()) {
                home.setContractDomesticPurchase(statisticalDigital);
            } else if (ContractEnum.CONTRACT_TYPE_6.getCode() == statisticalDigital.getType()) {
                home.setContractDomesticSales(statisticalDigital);
            }
        }

        home.setDeliveryImport(cbsHomeDao.selectTotal(tenantId, "cbs_import", 14));
        home.setDeliveryExport(cbsHomeDao.selectTotal(tenantId, "cbs_export", 14));
        home.setDeliveryDomesticImport(cbsHomeDao.selectTotal(tenantId, "cbs_purchase", 5));
        home.setDeliveryDomesticExport(cbsHomeDao.selectTotal(tenantId, "cbs_sell", 5));
        home.setProduce(cbsHomeDao.selectTotal(tenantId, "cbs_produce",7));
        home.setWarehouseIn(cbsHomeDao.selectTotal(tenantId, "cbs_store_goods_in", 4));
        home.setWarehouseOut(cbsHomeDao.selectTotal(tenantId, "cbs_store_goods_out", 4));

        // task其他总数
//        FutureTask<StatisticalDigital> task1 = (FutureTask<StatisticalDigital>) Constant.threadPoolExecutor.submit(new FutureTask(() -> cbsHomeDao.selectTotal(tenantId, "cbs_export", 14)));
//        FutureTask<StatisticalDigital> task2 = (FutureTask<StatisticalDigital>) Constant.threadPoolExecutor.submit(new FutureTask(() -> cbsHomeDao.selectTotal(tenantId, "cbs_import", 14)));
//        FutureTask<StatisticalDigital> task3 = (FutureTask<StatisticalDigital>) Constant.threadPoolExecutor.submit(new FutureTask(() -> cbsHomeDao.selectTotal(tenantId, "cbs_purchase", 5)));
//        FutureTask<StatisticalDigital> task4 = (FutureTask<StatisticalDigital>) Constant.threadPoolExecutor.submit(new FutureTask(() -> cbsHomeDao.selectTotal(tenantId, "cbs_sell", 5)));
//        FutureTask<StatisticalDigital> task5 = (FutureTask<StatisticalDigital>) Constant.threadPoolExecutor.submit(new FutureTask(() -> cbsHomeDao.selectTotal(tenantId, "cbs_produce",7)));
//        FutureTask<StatisticalDigital> task6 = (FutureTask<StatisticalDigital>) Constant.threadPoolExecutor.submit(new FutureTask(() -> cbsHomeDao.selectTotal(tenantId, "cbs_store_goods_in", 4)));
//        FutureTask<StatisticalDigital> task7 = (FutureTask<StatisticalDigital>) Constant.threadPoolExecutor.submit(new FutureTask(() -> cbsHomeDao.selectTotal(tenantId, "cbs_store_goods_out", 4)));
//
//        StatisticalDigital entity1;
//        StatisticalDigital entity2;
//        StatisticalDigital entity3;
//        StatisticalDigital entity4;
//        StatisticalDigital entity5;
//        StatisticalDigital entity6;
//        StatisticalDigital entity7;
//
//        try {
//            entity1 = task1.get();
//            entity2 = task2.get();
//            entity3 = task3.get();
//            entity4 = task4.get();
//            entity5 = task5.get();
//            entity6 = task6.get();
//            entity7 = task7.get();
//        } catch (InterruptedException e) {
//            throw new RRException(e.getMessage());
//        } catch (ExecutionException e) {
//            throw new RRException(e.getMessage());
//        } finally {
//            task1.cancel(true);
//            task2.cancel(true);
//            task3.cancel(true);
//            task4.cancel(true);
//            task5.cancel(true);
//            task6.cancel(true);
//            task7.cancel(true);
//            Constant.threadPoolExecutor.remove(task1);
//            Constant.threadPoolExecutor.remove(task2);
//            Constant.threadPoolExecutor.remove(task3);
//            Constant.threadPoolExecutor.remove(task4);
//            Constant.threadPoolExecutor.remove(task5);
//            Constant.threadPoolExecutor.remove(task6);
//            Constant.threadPoolExecutor.remove(task7);
//        }
//
//        home.setDeliveryImport(entity1);
//        home.setDeliveryExport(entity2);
//        home.setDeliveryDomesticImport(entity3);
//        home.setDeliveryDomesticExport(entity4);
//        home.setProduce(entity5);
//        home.setWarehouseIn(entity6);
//        home.setWarehouseOut(entity7);

        return home;
    }

    @Override
    public CbsHomeEntity expected(Long tenantId, String[] months) {
        List<StatisticalDigital> incomeList = cbsHomeDao.incomeExpendList(tenantId, months, "cbs_money_in_expected");
        List<StatisticalDigital> expendList = cbsHomeDao.incomeExpendList(tenantId, months, "cbs_money_out_expected");
        CbsHomeEntity cbsHomeEntity = new CbsHomeEntity();
        cbsHomeEntity.setIncomeList(incomeList);
        cbsHomeEntity.setExpendList(expendList);
        return cbsHomeEntity;
    }

    @Override
    public CbsHomeEntity actual(Long tenantId, String[] months) {
        List<StatisticalDigital> incomeList = cbsHomeDao.incomeExpendList(tenantId, months, "cbs_money_in");
        List<StatisticalDigital> expendList = cbsHomeDao.incomeExpendList(tenantId, months, "cbs_money_out");
        CbsHomeEntity cbsHomeEntity = new CbsHomeEntity();
        cbsHomeEntity.setIncomeList(incomeList);
        cbsHomeEntity.setExpendList(expendList);
        return cbsHomeEntity;
    }

    @Override
    public CbsHomeEntity upcoming(Long tenantId) {
        CbsHomeEntity cbsHomeEntity = new CbsHomeEntity();
        // 合同
        List<StatisticalContract> upcomingContract = cbsHomeDao.upcomingContract(tenantId);
        cbsHomeEntity.setUpcomingContract(upcomingContract);
        // 收发货
        List<StatisticalContract> upcomingImport = cbsHomeDao.upcomingDelivery(tenantId, 2, "cbs_import");
        List<StatisticalContract> upcomingExport = cbsHomeDao.upcomingDelivery(tenantId, 1, "cbs_export");
        List<StatisticalContract> upcomingPurchase = cbsHomeDao.upcomingDelivery(tenantId, 3, "cbs_purchase");
        List<StatisticalContract> upcomingSell = cbsHomeDao.upcomingDelivery(tenantId, 4, "cbs_sell");
        cbsHomeEntity.setUpcomingImport(upcomingImport);
        cbsHomeEntity.setUpcomingExport(upcomingExport);
        cbsHomeEntity.setUpcomingPurchase(upcomingPurchase);
        cbsHomeEntity.setUpcomingSell(upcomingSell);
        // 生产
        List<StatisticalContract> upcomingProduce = cbsHomeDao.upcomingDelivery(tenantId, 1, "cbs_produce");
        cbsHomeEntity.setUpcomingProduce(upcomingProduce);
        return cbsHomeEntity;
    }
}