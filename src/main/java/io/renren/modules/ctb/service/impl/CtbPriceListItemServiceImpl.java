package io.renren.modules.ctb.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.QueryPage;
import io.renren.modules.ctb.dao.CtbPriceListDao;
import io.renren.modules.ctb.dao.CtbPriceListItemDao;
import io.renren.modules.ctb.dao.CtbServiceContractDao;
import io.renren.modules.ctb.entity.CtbPriceListEntity;
import io.renren.modules.ctb.entity.CtbPriceListItemEntity;
import io.renren.modules.ctb.entity.CtbServiceContractEntity;
import io.renren.modules.ctb.service.CtbPriceListItemService;
import io.renren.modules.oss.cloud.OSSFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;


@Service("ctbPriceListItemService")
public class CtbPriceListItemServiceImpl extends ServiceImpl<CtbPriceListItemDao, CtbPriceListItemEntity> implements CtbPriceListItemService {

    private CtbPriceListDao ctbPriceListDao;
    private CtbPriceListItemDao ctbPriceListItemDao;
    private CtbServiceContractDao ctbServiceContractDao;


    public CtbPriceListItemServiceImpl(CtbPriceListDao ctbPriceListDao, CtbPriceListItemDao ctbPriceListItemDao, CtbServiceContractDao ctbServiceContractDao) {
        this.ctbPriceListDao = ctbPriceListDao;
        this.ctbPriceListItemDao = ctbPriceListItemDao;
        this.ctbServiceContractDao = ctbServiceContractDao;
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbPriceListItemEntity> page = this.page(
                new Query<CtbPriceListItemEntity>().getPage(params),
                new QueryWrapper<CtbPriceListItemEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public PageUtils queryIndex(CtbPriceListItemEntity entity) {
        CtbPriceListEntity ctbPriceListEntity = ctbPriceListDao.selectOne(new QueryWrapper<CtbPriceListEntity>().eq("fk_contract_id", entity.getContractId()));
        if (ctbPriceListEntity == null) {
            throw new RRException("?????????");
        }
        entity.setFkPriceListId(ctbPriceListEntity.getId());
        QueryWrapper qw = new QueryWrapper<CtbPriceListItemEntity>();
        qw.eq("fk_price_list_id", entity.getFkPriceListId());
        qw.eq(StringUtils.isNotEmpty(entity.getName()), "name", entity.getName());
        IPage<CtbPriceListItemEntity> page = this.page(new QueryPage<CtbPriceListItemEntity>().getPage(entity), qw);
        return new PageUtils(page);
    }

    @Override
    public String generatePDF(Long id) {
        CtbPriceListEntity ctbPriceListEntity = ctbPriceListDao.selectOne(new QueryWrapper<CtbPriceListEntity>().eq("fk_contract_id", id));
        if (ctbPriceListEntity == null) {
            throw new RRException("???????????????");
        }
        List<CtbPriceListItemEntity> list = ctbPriceListItemDao.selectList(new QueryWrapper<CtbPriceListItemEntity>().eq("fk_price_list_id", ctbPriceListEntity.getId()));
        if (CollectionUtil.isEmpty(list)) {
            throw new RRException("???????????????");
        }
        CtbServiceContractEntity entity = ctbServiceContractDao.selectById(id);
        String fileName = "pdf_quotation_" + entity.getCode() + ".pdf";
        File file = new File(fileName);
        OutputStream out = null;
        FileInputStream input = null;
        try {
            out = new FileOutputStream(file);
            out.flush();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            // ???????????????4???
            PdfPTable table = new PdfPTable(4);
            // ???????????????????????????%100
            table.setWidthPercentage(100);
            // ?????????????????????
            table.setTotalWidth(500);
            // ?????????????????????????????????
            table.setTotalWidth(new float[]{100, 100, 100, 150});
            // ????????????
            table.setLockedWidth(false);
            // ??????????????????????????????
            table.setSpacingBefore(10f);
            // ??????????????????????????????
            table.setSpacingAfter(10f);
            // ??????????????????????????????
            table.getDefaultCell().setBorder(0);

            // ?????????????????????
            BaseFont chinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // ??????
            Font fontBold = new Font(chinese, 16, Font.BOLD);
            // ??????- ???
            Font fontBoldSmall = new Font(chinese, 11, Font.BOLD);
            // ??????
            Font fontNormal = new Font(chinese, 12, Font.NORMAL);
            PdfPCell title = new PdfPCell(new Phrase("???????????????", fontBold));
            // ????????????
            // cell1.setBorderColor(BaseColor.BLUE);
            // ??????????????????
            // cell1.setBackgroundColor(BaseColor.ORANGE);
            // ????????????????????????
            // cell1.setPaddingLeft(10);
            // ??????????????????
            // cell1.setRowspan(2);
            // ??????????????????
            title.setColspan(4);
            // ????????????
            title.setFixedHeight(30);
            // ??????????????????????????????
            title.setHorizontalAlignment(Element.ALIGN_CENTER);
            // ??????????????????
            title.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(title);

            // ????????????
            PdfPCell expenseItem = new PdfPCell(new Phrase("????????????", fontNormal));
            expenseItem.setHorizontalAlignment(Element.ALIGN_CENTER);
            expenseItem.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(expenseItem);
            // ??????
            PdfPCell cost = new PdfPCell(new Phrase("??????", fontNormal));
            cost.setHorizontalAlignment(Element.ALIGN_CENTER);
            cost.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cost);
            // ??????
            PdfPCell taxRate = new PdfPCell(new Phrase("??????", fontNormal));
            taxRate.setHorizontalAlignment(Element.ALIGN_CENTER);
            taxRate.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(taxRate);
            // ??????
            PdfPCell remark = new PdfPCell(new Phrase("??????", fontNormal));
            remark.setHorizontalAlignment(Element.ALIGN_CENTER);
            remark.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(remark);

            for (CtbPriceListItemEntity ctbPriceListItemEntity : list) {
                PdfPCell namePdfPCell = new PdfPCell(new Paragraph(ctbPriceListItemEntity.getName() == null ? "" : ctbPriceListItemEntity.getName(), fontNormal));
                namePdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pricePdfPCell = new PdfPCell(new Paragraph(ctbPriceListItemEntity.getPrice() == null ? "" : ctbPriceListItemEntity.getPrice().stripTrailingZeros().toPlainString()));
                pricePdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell taxRatePdfPCell = new PdfPCell(new Paragraph(ctbPriceListItemEntity.getTaxRate() == null ? "" : ctbPriceListItemEntity.getTaxRate().toString()));
                taxRatePdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell remarkPdfPCell = new PdfPCell(new Paragraph(ctbPriceListItemEntity.getRemark() == null ? "" : ctbPriceListItemEntity.getRemark(), fontNormal));
                remarkPdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(namePdfPCell);
                table.addCell(pricePdfPCell);
                table.addCell(taxRatePdfPCell);
                table.addCell(remarkPdfPCell);
            }

            // table??????
            PdfPCell tableRemark = new PdfPCell(new Phrase("??????", fontBoldSmall));
            tableRemark.setColspan(4);
            tableRemark.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableRemark.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(tableRemark);
            // table??????
            PdfPCell tableRemark1 = new PdfPCell(new Phrase("1.??????????????????????????????????????????????????????????????????6%??????", fontBoldSmall));
            tableRemark1.setColspan(4);
            table.addCell(tableRemark1);
            /*// table??????
            PdfPCell tableRemark2 = new PdfPCell(new Phrase("2.??????????????????????????? ?????????200???", fontBoldSmall));
            tableRemark2.setColspan(4);
            table.addCell(tableRemark2);
            // table??????
            PdfPCell tableRemark3 = new PdfPCell(new Phrase("3.???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????", fontBoldSmall));
            tableRemark3.setColspan(4);
            table.addCell(tableRemark3);
            // table??????
            PdfPCell tableRemark4 = new PdfPCell(new Phrase("4.???????????????????????????", fontBoldSmall));
            tableRemark4.setColspan(4);
            table.addCell(tableRemark4);*/

            document.add(table);
            document.close();

            // ????????????????????????????????????
            input = new FileInputStream(file);
            String url = OSSFactory.build().uploadSuffix(input, fileName);
            return url;
        } catch (IOException | DocumentException e) {
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
        return "";
    }
}