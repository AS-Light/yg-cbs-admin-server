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
            throw new RRException("无数据");
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
            throw new RRException("暂无报价单");
        }
        List<CtbPriceListItemEntity> list = ctbPriceListItemDao.selectList(new QueryWrapper<CtbPriceListItemEntity>().eq("fk_price_list_id", ctbPriceListEntity.getId()));
        if (CollectionUtil.isEmpty(list)) {
            throw new RRException("暂无报价单");
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
            // 添加表格，4列
            PdfPTable table = new PdfPTable(4);
            // 设置表格宽度比例为%100
            table.setWidthPercentage(100);
            // 设置表格的宽度
            table.setTotalWidth(500);
            // 也可以每列分别设置宽度
            table.setTotalWidth(new float[]{100, 100, 100, 150});
            // 锁住宽度
            table.setLockedWidth(false);
            // 设置表格上面空白宽度
            table.setSpacingBefore(10f);
            // 设置表格下面空白宽度
            table.setSpacingAfter(10f);
            // 设置表格默认为无边框
            table.getDefaultCell().setBorder(0);

            // 构建每个单元格
            BaseFont chinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // 加粗
            Font fontBold = new Font(chinese, 16, Font.BOLD);
            // 加粗- 小
            Font fontBoldSmall = new Font(chinese, 11, Font.BOLD);
            // 正常
            Font fontNormal = new Font(chinese, 12, Font.NORMAL);
            PdfPCell title = new PdfPCell(new Phrase("普货报价单", fontBold));
            // 边框颜色
            // cell1.setBorderColor(BaseColor.BLUE);
            // 设置背景颜色
            // cell1.setBackgroundColor(BaseColor.ORANGE);
            // 设置距左边的距离
            // cell1.setPaddingLeft(10);
            // 设置占用两行
            // cell1.setRowspan(2);
            // 设置占用三列
            title.setColspan(4);
            // 设置高度
            title.setFixedHeight(30);
            // 设置内容水平居中显示
            title.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 设置垂直居中
            title.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(title);

            // 费用项目
            PdfPCell expenseItem = new PdfPCell(new Phrase("费用项目", fontNormal));
            expenseItem.setHorizontalAlignment(Element.ALIGN_CENTER);
            expenseItem.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(expenseItem);
            // 费用
            PdfPCell cost = new PdfPCell(new Phrase("费用", fontNormal));
            cost.setHorizontalAlignment(Element.ALIGN_CENTER);
            cost.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cost);
            // 税率
            PdfPCell taxRate = new PdfPCell(new Phrase("税率", fontNormal));
            taxRate.setHorizontalAlignment(Element.ALIGN_CENTER);
            taxRate.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(taxRate);
            // 备注
            PdfPCell remark = new PdfPCell(new Phrase("备注", fontNormal));
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

            // table备注
            PdfPCell tableRemark = new PdfPCell(new Phrase("备注", fontBoldSmall));
            tableRemark.setColspan(4);
            tableRemark.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableRemark.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(tableRemark);
            // table备注
            PdfPCell tableRemark1 = new PdfPCell(new Phrase("1.以上报价不含商业保险，如需开增值税发票，加收6%税点", fontBoldSmall));
            tableRemark1.setColspan(4);
            table.addCell(tableRemark1);
            /*// table备注
            PdfPCell tableRemark2 = new PdfPCell(new Phrase("2.海关对货物验估审价 代理费200元", fontBoldSmall));
            tableRemark2.setColspan(4);
            table.addCell(tableRemark2);
            // table备注
            PdfPCell tableRemark3 = new PdfPCell(new Phrase("3.换单费、滞箱费、洗箱费、返箱费、开单费、查验费、港杂费、港建费、箱费、堆存费等费用实报实销", fontBoldSmall));
            tableRemark3.setColspan(4);
            table.addCell(tableRemark3);
            // table备注
            PdfPCell tableRemark4 = new PdfPCell(new Phrase("4.未尽事宜，另行商议", fontBoldSmall));
            tableRemark4.setColspan(4);
            table.addCell(tableRemark4);*/

            document.add(table);
            document.close();

            // 临时路径，上传阿里后删除
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