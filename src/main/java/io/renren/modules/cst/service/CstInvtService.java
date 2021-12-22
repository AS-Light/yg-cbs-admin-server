package io.renren.modules.cst.service;

import io.renren.modules.cst.entity.CstInvtEntity;
import io.renren.modules.cst.entity.CstNptsEmlEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 海关加工贸易手册保税核注清单对外接口
 */
public interface CstInvtService {
    CstInvtEntity detail(Long id);

    CstInvtEntity detailByImportId(Long importId);

    CstInvtEntity detailByExportId(Long exportId);

    List<CstInvtEntity> listByContractId(Long contractId);

    Long save(CstInvtEntity entity) throws RuntimeException;

    String generateInvtXml(HttpServletResponse response, CstInvtEntity entity);
}

