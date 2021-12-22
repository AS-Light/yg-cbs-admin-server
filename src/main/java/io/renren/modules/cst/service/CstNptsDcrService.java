package io.renren.modules.cst.service;

import io.renren.modules.cst.entity.CstNptsDcrEntity;

/**
 * 海关加工贸易手册保税核注清单对外接口
 */
public interface CstNptsDcrService {
    CstNptsDcrEntity detail(Long id);

    CstNptsDcrEntity detailByContractId(CstNptsDcrEntity entity);

    Long save(CstNptsDcrEntity entity) throws RuntimeException;
}

