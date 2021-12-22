package io.renren.modules.ctb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import io.renren.common.utils.JavaBeanUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.ctb.dao.CtbServiceContractDao;
import io.renren.modules.ctb.dao.CtbServiceContractTempleteDao;
import io.renren.modules.ctb.entity.CtbServiceContractEntity;
import io.renren.modules.ctb.entity.CtbServiceContractTempleteEntity;
import io.renren.modules.ctb.service.CtbServiceContractService;
import io.renren.modules.oss.cloud.OSSFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;


@Service("ctbServiceContractService")
public class CtbServiceContractServiceImpl extends ServiceImpl<CtbServiceContractDao, CtbServiceContractEntity> implements CtbServiceContractService {

    private CtbServiceContractDao ctbServiceContractDao;
    private CtbServiceContractTempleteDao ctbServiceContractTempleteDao;

    public CtbServiceContractServiceImpl(CtbServiceContractDao ctbServiceContractDao, CtbServiceContractTempleteDao ctbServiceContractTempleteDao) {
        this.ctbServiceContractDao = ctbServiceContractDao;
        this.ctbServiceContractTempleteDao = ctbServiceContractTempleteDao;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CtbServiceContractEntity> page = this.page(
                new Query<CtbServiceContractEntity>().getPage(params),
                new QueryWrapper<CtbServiceContractEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public Long create(CtbServiceContractEntity entity) {
        ctbServiceContractDao.insertWithoutTenant(entity);
        entity.setPdf(generatePDF(entity.getId()));
        ctbServiceContractDao.updateById(entity);
        return entity.getId();
    }

    @Override
    public String generatePDF(Long id) {
        CtbServiceContractEntity entity = ctbServiceContractDao.selectById(id);
        String fileName = "pdf_contract_" + entity.getCode() + ".pdf";
        File file = new File(fileName);
        OutputStream out = null;
        FileInputStream input = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            CtbServiceContractTempleteEntity ctbServiceContractTempleteEntity = ctbServiceContractTempleteDao.selectOne(new QueryWrapper<CtbServiceContractTempleteEntity>().eq("ctb_tenant_id", entity.getCtbTenantId()));
            PdfReader reader = new PdfReader(ctbServiceContractTempleteEntity.getTempletePdf());
            /* 将要生成的目标PDF文件名称 */
            PdfStamper ps = new PdfStamper(reader, bos);
            ps.getUnderContent(1);

            /* 使用中文字体 */
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            ArrayList<BaseFont> fontList = new ArrayList<>();
            fontList.add(bf);

            /* 取出报表模板中的所有字段 */
            AcroFields fields = ps.getAcroFields();
            fields.setSubstitutionFonts(fontList);
            fillData(fields, JavaBeanUtil.object2Map(entity));

            /* 必须要调用这个，否则文档不会生成的 */
            ps.setFormFlattening(true);
            ps.close();

            // 临时路径，上传阿里后删除
            out = new FileOutputStream(file);
            out.write(bos.toByteArray());
            out.flush();
            input = new FileInputStream(file);
            String url = OSSFactory.build().uploadSuffix(input, fileName);
            return url;
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
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

    public static void fillData(AcroFields fields, Map<String, Object> data)
            throws IOException, DocumentException {
        for (String key : data.keySet()) {
            String value = data.get(key) == null ? "" : data.get(key).toString();
            fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的
        }
    }


}