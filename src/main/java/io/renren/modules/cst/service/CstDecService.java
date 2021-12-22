package io.renren.modules.cst.service;

import io.renren.modules.cst.entity.CstDecEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 海关加工贸易手册保税核注清单对外接口
 */
public interface CstDecService {
    CstDecEntity detail(Long id);

    CstDecEntity detailByImportId(Long importId);

    CstDecEntity detailByExportId(Long exportId);

    List<CstDecEntity> listByContractId(Long contractId);

    Long save(CstDecEntity entity) throws RuntimeException;

    String generateDecXml(HttpServletResponse response, CstDecEntity entity);
}

