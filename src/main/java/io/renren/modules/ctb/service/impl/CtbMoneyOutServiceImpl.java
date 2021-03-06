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
        // ??????
        List<CtbMoneyOutEntity> entityList = ctbMoneyOutDao.selectByIds(asList);
        if (entityList != null && entityList.size() > 0) {
            Long id = entityList.get(0).getFkServiceCompanyId();
            for (CtbMoneyOutEntity entity : entityList) {
                if (entity.getFkServiceCompanyId() != id) {
                    throw new RRException("??????????????????????????????????????????????????????????????????");
                }
                if (entity.getIsReimbursement()) {
                    throw new RRException("?????????????????????????????????????????????????????????");
                }
            }
        }
        List<CtbMoneyInOutEntity> listCtbMoneyInOutEntity = new ArrayList<>();
        // ??????out???isReimbursement???1
        ctbMoneyOutDao.update(CtbMoneyOutEntity.builder().isReimbursement(true).build(), new QueryWrapper<CtbMoneyOutEntity>().in("id", asList));
        // ????????????????????? -> ?????????in???
        BigDecimal money = BigDecimal.ZERO;
        List<CtbMoneyOutEntity> list = ctbMoneyOutDao.selectBatchIds(asList);
        for (CtbMoneyOutEntity ctbMoneyOutEntity : list) {
            money = money.add(ctbMoneyOutEntity.getCny());
            listCtbMoneyInOutEntity.add(CtbMoneyInOutEntity.builder().moneyOutId(ctbMoneyOutEntity.getId()).build());
        }
        CtbMoneyInEntity ctbMoneyInEntity = CtbMoneyInEntity.builder()
                .fkServiceCompanyId(list.get(0).getFkServiceCompanyId())
                .title("??????????????????")
                .unitMoney(money)
                .unit("???")
                .count(BigDecimal.ONE)
                .money(money)
                .currencyCode("142")
                .operator(operator)
                .cny(money)
                .isReimbursement(true)
                .build();
        ctbMoneyInDao.insert(ctbMoneyInEntity);
        // ??????????????? ctb_money_in_out
        for (CtbMoneyInOutEntity ctbMoneyInOutEntity : listCtbMoneyInOutEntity) {
            ctbMoneyInOutEntity.setMoneyInId(ctbMoneyInEntity.getId());
        }
        ctbMoneyInOutService.saveBatch(listCtbMoneyInOutEntity);
    }

    @Override
    public void updateMoneyImg(CtbMoneyOutEntity ctbMoneyOutEntity) {
        CtbMoneyOutEntity entity = ctbMoneyOutDao.selectById(ctbMoneyOutEntity.getId());
        // ?????????????????? ctb_img_money_in
        ctbImgMoneyOutDao.delete(new QueryWrapper<CtbImgMoneyOutEntity>().eq("fk_money_out_id", entity.getId()));
        for (CtbImgMoneyOutEntity ctbImgMoneyOutEntity : ctbMoneyOutEntity.getImgMoneyOutList()) {
            ctbImgMoneyOutEntity.setFkMoneyOutId(entity.getId());
            ctbImgMoneyOutDao.insert(ctbImgMoneyOutEntity);
        }
        // ?????? cbs_money_in ?????????3
        ctbMoneyOutDao.updateById(CtbMoneyOutEntity.builder().id(entity.getId()).status(MoneyEnum.CONFIRM_STATUS_3.getCode()).build());
    }

    @Override
    public void confirmAnnex(CtbMoneyOutEntity ctbMoneyOutEntity) {
        CtbMoneyOutEntity entity = ctbMoneyOutDao.selectById(ctbMoneyOutEntity.getId());
        if (MoneyEnum.CONFIRM_STATUS_3.getCode() == entity.getStatus()) {
            ctbMoneyOutDao.updateById(CtbMoneyOutEntity.builder().id(entity.getId()).status(MoneyEnum.CONFIRM_STATUS_4.getCode()).build());
        } else {
            throw new RRException("??????????????????????????????????????????");
        }
    }

    @Override
    public String reimbursementExcel(HttpServletResponse response, List<Long> asList) {
        // ??????
        List<CtbMoneyOutEntity> entityList = ctbMoneyOutDao.selectByIds(asList);
        if (entityList != null && entityList.size() > 0) {
            Long id = entityList.get(0).getFkServiceCompanyId();
            for (CtbMoneyOutEntity entity : entityList) {
                if (entity.getFkServiceCompanyId() != id) {
                    throw new RRException("??????????????????????????????????????????????????????????????????");
                }
                if (entity.getIsReimbursement()) {
                    throw new RRException("?????????????????????????????????????????????????????????");
                }
            }
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
            sheet.setSheetName("????????????");
            sheet.setHeaders(map);
            sheet.setDataset(dataset);
            list.add(sheet);
            // ??????sheet end
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