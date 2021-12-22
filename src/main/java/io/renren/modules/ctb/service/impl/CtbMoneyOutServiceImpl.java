package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.ExcelUtil.ExcelSheet;
import io.renren.common.ExcelUtil.ExcelUtil;
import io.renren.common.ExcelUtil.Model;
import io.renren.common.enumeration.MoneyEnum;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.QueryPage;
import io.renren.modules.ctb.dao.CtbImgMoneyOutDao;
import io.renren.modules.ctb.dao.CtbMoneyInDao;
import io.renren.modules.ctb.dao.CtbMoneyOutDao;
import io.renren.modules.ctb.entity.CtbImgMoneyOutEntity;
import io.renren.modules.ctb.entity.CtbMoneyInEntity;
import io.renren.modules.ctb.entity.CtbMoneyInOutEntity;
import io.renren.modules.ctb.entity.CtbMoneyOutEntity;
import io.renren.modules.ctb.service.CtbMoneyInOutService;
import io.renren.modules.ctb.service.CtbMoneyOutService;
import io.renren.modules.oss.cloud.OSSFactory;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("ctbMoneyOutService")
public class CtbMoneyOutServiceImpl extends ServiceImpl<CtbMoneyOutDao, CtbMoneyOutEntity> implements CtbMoneyOutService {

    private CtbMoneyOutDao ctbMoneyOutDao;
    private CtbMoneyInDao ctbMoneyInDao;
    private CtbMoneyInOutService ctbMoneyInOutService;
    private CtbImgMoneyOutDao ctbImgMoneyOutDao;

    public CtbMoneyOutServiceImpl(CtbMoneyOutDao ctbMoneyOutDao, CtbMoneyInDao ctbMoneyInDao, CtbMoneyInOutService ctbMoneyInOutService, CtbImgMoneyOutDao ctbImgMoneyOutDao) {
        this.ctbMoneyOutDao = ctbMoneyOutDao;
        this.ctbMoneyInDao = ctbMoneyInDao;
        this.ctbMoneyInOutService = ctbMoneyInOutService;
        this.ctbImgMoneyOutDao = ctbImgMoneyOutDao;
    }

    @Override
    public PageUtils queryPage(CtbMoneyOutEntity entity) {
        IPage<CtbMoneyOutEntity> page = ctbMoneyOutDao.queryIndex(new QueryPage<CtbMoneyOutEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void reimbursement(List<Long> asList, Long operator) {
        // 验证
        List<CtbMoneyOutEntity> entityList = ctbMoneyOutDao.selectByIds(asList);
        if (entityList != null && entityList.size() > 0) {
            Long id = entityList.get(0).getFkServiceCompanyId();
            for (CtbMoneyOutEntity entity : entityList) {
                if (entity.getFkServiceCompanyId() != id) {
                    throw new RRException("不属于同一服务企业，不可批量报销，请重新确认");
                }
                if (entity.getIsReimbursement()) {
                    throw new RRException("有有报销项目，不可重复报销，请重新确认");
                }
            }
        }
        List<CtbMoneyInOutEntity> listCtbMoneyInOutEntity = new ArrayList<>();
        // 更新out表isReimbursement为1
        ctbMoneyOutDao.update(CtbMoneyOutEntity.builder().isReimbursement(true).build(), new QueryWrapper<CtbMoneyOutEntity>().in("id", asList));
        // 合并成一条数据 -> 插入到in表
        BigDecimal money = BigDecimal.ZERO;
        List<CtbMoneyOutEntity> list = ctbMoneyOutDao.selectBatchIds(asList);
        for (CtbMoneyOutEntity ctbMoneyOutEntity : list) {
            money = money.add(ctbMoneyOutEntity.getCny());
            listCtbMoneyInOutEntity.add(CtbMoneyInOutEntity.builder().moneyOutId(ctbMoneyOutEntity.getId()).build());
        }
        CtbMoneyInEntity ctbMoneyInEntity = CtbMoneyInEntity.builder()
                .fkServiceCompanyId(list.get(0).getFkServiceCompanyId())
                .title("企业报销收入")
                .unitMoney(money)
                .unit("单")
                .count(BigDecimal.ONE)
                .money(money)
                .currencyCode("142")
                .operator(operator)
                .cny(money)
                .isReimbursement(true)
                .build();
        ctbMoneyInDao.insert(ctbMoneyInEntity);
        // 插入关联表 ctb_money_in_out
        for (CtbMoneyInOutEntity ctbMoneyInOutEntity : listCtbMoneyInOutEntity) {
            ctbMoneyInOutEntity.setMoneyInId(ctbMoneyInEntity.getId());
        }
        ctbMoneyInOutService.saveBatch(listCtbMoneyInOutEntity);
    }

    @Override
    public void updateMoneyImg(CtbMoneyOutEntity ctbMoneyOutEntity) {
        CtbMoneyOutEntity entity = ctbMoneyOutDao.selectById(ctbMoneyOutEntity.getId());
        // 先删除后插入 ctb_img_money_in
        ctbImgMoneyOutDao.delete(new QueryWrapper<CtbImgMoneyOutEntity>().eq("fk_money_out_id", entity.getId()));
        for (CtbImgMoneyOutEntity ctbImgMoneyOutEntity : ctbMoneyOutEntity.getImgMoneyOutList()) {
            ctbImgMoneyOutEntity.setFkMoneyOutId(entity.getId());
            ctbImgMoneyOutDao.insert(ctbImgMoneyOutEntity);
        }
        // 更新 cbs_money_in 状态为3
        ctbMoneyOutDao.updateById(CtbMoneyOutEntity.builder().id(entity.getId()).status(MoneyEnum.CONFIRM_STATUS_3.getCode()).build());
    }

    @Override
    public void confirmAnnex(CtbMoneyOutEntity ctbMoneyOutEntity) {
        CtbMoneyOutEntity entity = ctbMoneyOutDao.selectById(ctbMoneyOutEntity.getId());
        if (MoneyEnum.CONFIRM_STATUS_3.getCode() == entity.getStatus()) {
            ctbMoneyOutDao.updateById(CtbMoneyOutEntity.builder().id(entity.getId()).status(MoneyEnum.CONFIRM_STATUS_4.getCode()).build());
        } else {
            throw new RRException("状态不符合要求，请先上传附件");
        }
    }

    @Override
    public String reimbursementExcel(HttpServletResponse response, List<Long> asList) {
        // 验证
        List<CtbMoneyOutEntity> entityList = ctbMoneyOutDao.selectByIds(asList);
        if (entityList != null && entityList.size() > 0) {
            Long id = entityList.get(0).getFkServiceCompanyId();
            for (CtbMoneyOutEntity entity : entityList) {
                if (entity.getFkServiceCompanyId() != id) {
                    throw new RRException("不属于同一服务企业，不可批量报销，请重新确认");
                }
                if (entity.getIsReimbursement()) {
                    throw new RRException("有有报销项目，不可重复报销，请重新确认");
                }
            }
            List<ExcelSheet<T>> list = new ArrayList<>();
            // 组装sheet
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, String> map = new LinkedHashMap<>();
            map.put("a", "从属单号");
            map.put("b", "收费类型");
            map.put("c", "发生金额");
            map.put("d", "币制");
            map.put("e", "人民币金额");
            map.put("f", "创建时间");
            map.put("g", "操作人");
            Collection<Object> dataset = new ArrayList<>();
            for (CtbMoneyOutEntity item : entityList) {
                dataset.add(new Model(
                        item.getTitle(),
                        item.getMoneyTypeEntity() == null ? null : item.getMoneyTypeEntity().getName(),
                        item.getMoney().toPlainString(),
                        item.getCurrencyEntity() == null ? null : item.getCurrencyEntity().getNameCn(),
                        item.getCny().toPlainString(),
                        item.getCreateTime().toString(),
                        item.getOperatorEntity() == null ? null : item.getOperatorEntity().getUsername(),
                        null, null));
            }
            ExcelSheet sheet = new ExcelSheet();
            sheet.setSheetName("实报实销");
            sheet.setHeaders(map);
            sheet.setDataset(dataset);
            list.add(sheet);
            // 组装sheet end
            String fileName = "excel_reimbursement_" + new Date().getTime() + ".xls";
            File file = new File(fileName);
            OutputStream out = null;
            FileInputStream input = null;
            try {
                out = new FileOutputStream(file);
                ExcelUtil.exportExcel(list, out);
                input = new FileInputStream(file);
                String url = OSSFactory.build().uploadSuffix(input, fileName);
                return url;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    file.delete();
                    if (out != null) {
                        out.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}