package io.renren.modules.cst.service;

import io.renren.modules.cst.entity.CstNptsEmlEntity;
import io.renren.modules.cst.entity.CstNptsEmlImgExgEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * 海关加工贸易手册对外接口
 */
public interface CstNptsEmlService {
    CstNptsEmlEntity detail(Long id);

    CstNptsEmlEntity detailByContractId(Long contractId);

    Long save(CstNptsEmlEntity entity) throws RuntimeException;

    String generateNptsEmlXml(HttpServletResponse response, CstNptsEmlEntity entity);

    CstNptsEmlImgExgEntity selectGoodsByPartNo(Long partNo);
}

