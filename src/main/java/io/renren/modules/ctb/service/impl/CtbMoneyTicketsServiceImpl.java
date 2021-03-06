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
import io.renren.modules.ctb.dao.CtbImgMoneyTicketsDao;
import io.renren.modules.ctb.dao.CtbMoneyInDao;
import io.renren.modules.ctb.dao.CtbMoneyTicketsDao;
import io.renren.modules.ctb.entity.CtbImgMoneyTicketsEntity;
import io.renren.modules.ctb.entity.CtbMoneyInEntity;
import io.renren.modules.ctb.entity.CtbMoneyTicketsEntity;
import io.renren.modules.ctb.service.CtbMoneyTicketsService;
import io.renren.modules.oss.cloud.OSSFactory;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("ctbMoneyTicketsService")
public class CtbMoneyTicketsServiceImpl extends ServiceImpl<CtbMoneyTicketsDao, CtbMoneyTicketsEntity> implements CtbMoneyTicketsService {

    private CtbMoneyTicketsDao ctbMoneyTicketsDao;
    private CtbImgMoneyTicketsDao ctbImgMoneyTicketsDao;
    private CtbMoneyInDao ctbMoneyInDao;

    public CtbMoneyTicketsServiceImpl(CtbMoneyTicketsDao ctbMoneyTicketsDao, CtbImgMoneyTicketsDao ctbImgMoneyTicketsDao, CtbMoneyInDao ctbMoneyInDao) {
        this.ctbMoneyTicketsDao = ctbMoneyTicketsDao;
        this.ctbImgMoneyTicketsDao = ctbImgMoneyTicketsDao;
        this.ctbMoneyInDao = ctbMoneyInDao;
    }

    @Override
    public PageUtils queryPage(CtbMoneyTicketsEntity entity) {
        IPage<CtbMoneyTicketsEntity> page = ctbMoneyTicketsDao.queryIndex(new QueryPage<CtbMoneyTicketsEntity>().getPage(entity), entity);
        return new PageUtils(page);
    }


    @Override
    @Transactional
    public void updateMoneyImg(CtbMoneyTicketsEntity ctbMoneyTicketsEntity) {
        CtbMoneyTicketsEntity entity = ctbMoneyTicketsDao.selectById(ctbMoneyTicketsEntity.getId());
        // ?????????????????? ctb_img_money_in
        ctbImgMoneyTicketsDao.delete(new QueryWrapper<CtbImgMoneyTicketsEntity>().eq("fk_money_tickets_id", entity.getId()));
        for (CtbImgMoneyTicketsEntity ctbImgMoneyTicketsEntity : ctbMoneyTicketsEntity.getImgMoneyTicketsList()) {
            ctbImgMoneyTicketsEntity.setFkMoneyTicketsId(entity.getId());
            ctbImgMoneyTicketsDao.insert(ctbImgMoneyTicketsEntity);
        }
    }

    @Override
    public void confirmAnnex(CtbMoneyTicketsEntity ctbMoneyTicketsEntity) {

    }

    @Override
    @Transactional
    public void uploadInvoice(CtbMoneyTicketsEntity entity) {
        CtbMoneyTicketsEntity ctbMoneyTicketsEntity = uploadInvoiceVerification(entity);
        if (ctbMoneyTicketsEntity != null) {
            // ?????? ctb_money_tickets ?????? ID
            ctbMoneyTicketsDao.insert(ctbMoneyTicketsEntity);
            // ?????? ctb_img_money_tickets
            ctbImgMoneyTicketsDao.delete(new QueryWrapper<CtbImgMoneyTicketsEntity>().eq("fk_money_tickets_id", ctbMoneyTicketsEntity.getId()));
            for (String imgUrl : entity.getImgInvoiceList()) {
                ctbImgMoneyTicketsDao.insert(CtbImgMoneyTicketsEntity.builder()
                        .fkMoneyTicketsId(ctbMoneyTicketsEntity.getId())
                        .imgUrl(imgUrl)
                        .build());
            }
            // ctb_money_in ???????????? fk_money_tickets_id
            ctbMoneyInDao.update(CtbMoneyInEntity.builder().fkMoneyTicketsId(ctbMoneyTicketsEntity.getId()).build(), new QueryWrapper<CtbMoneyInEntity>().in("id", entity.getMoneyInIdList()));
        }
    }


    private CtbMoneyTicketsEntity uploadInvoiceVerification(CtbMoneyTicketsEntity ctbMoneyTicketsEntity) {
        List<CtbMoneyInEntity> entityList = ctbMoneyInDao.selectByIds(ctbMoneyTicketsEntity.getMoneyInIdList());
        if (entityList != null && entityList.size() > 0) {
            Long serviceCompanyId = entityList.get(0).getFkServiceCompanyId();
            BigDecimal money = BigDecimal.ZERO;
            BigDecimal tax = BigDecimal.ZERO;
            for (CtbMoneyInEntity entity : entityList) {
                if (entity.getFkServiceCompanyId() != serviceCompanyId) {
                    throw new RRException("???????????????????????????????????????????????????????????????");
                }
                if (entity.getIsReimbursement()) {
                    throw new RRException("???????????????????????????");
                }
                if (entity.getFkMoneyTicketsId() != null) {
                    throw new RRException("??????????????????????????????????????????????????????????????????");
                }
                money = money.add(entity.getCny());
                BigDecimal rate = entity.getCny().multiply(new BigDecimal(entity.getMoneyTypeEntity().getDefTaxRate())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
                tax = tax.add(rate);
            }
            return CtbMoneyTicketsEntity.builder()
                    .fkServiceCompanyId(serviceCompanyId)
                    .invoice(ctbMoneyTicketsEntity.getInvoice())
                    .money(money)
                    .tax(tax)
                    .operator(ctbMoneyTicketsEntity.getOperator())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public void confirmInvoice(CtbMoneyTicketsEntity ctbMoneyTicketsEntity) {
        CtbMoneyTicketsEntity entity = ctbMoneyTicketsDao.selectById(ctbMoneyTicketsEntity.getId());
        if (MoneyEnum.CONFIRM_STATUS_1.getCode() == entity.getStatus()) {
            ctbMoneyTicketsDao.updateById(CtbMoneyTicketsEntity.builder().id(entity.getId()).status(MoneyEnum.CONFIRM_STATUS_2.getCode()).build());
        } else {
            throw new RRException("?????????????????????");
        }
    }

    @Override
    public String uploadInvoiceExcel(HttpServletResponse response, CtbMoneyTicketsEntity entity) {
        List<CtbMoneyInEntity> entityList = ctbMoneyInDao.selectByIds(entity.getMoneyInIdList());
        if (entityList != null && entityList.size() > 0) {
            List<ExcelSheet<T>> list = new ArrayList<>();
            // ??????sheet
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, String> map = new LinkedHashMap<>();
            map.put("a", "????????????");
            map.put("b", "????????????");
            map.put("c", "????????????");
            map.put("d", "??????");
            map.put("e", "???????????????");
            map.put("f", "????????????");
            map.put("g", "?????????");
            Collection<Object> dataset = new ArrayList<>();
            for (CtbMoneyInEntity item : entityList) {
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
            sheet.setSheetName("????????????");
            sheet.setHeaders(map);
            sheet.setDataset(dataset);
            list.add(sheet);
            // ??????sheet end
            String fileName = "excel_invoice_" + new Date().getTime() + ".xls";
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