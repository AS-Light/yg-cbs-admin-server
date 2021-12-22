package io.renren.modules.cst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.cst.entity.CstNptsEmlHeaderEntity;

/**
 * 海关加工贸易手册备案表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-13 19:21:36
 */
public interface CstNptsEmlHeaderService extends IService<CstNptsEmlHeaderEntity> {
    CstNptsEmlHeaderEntity selectByContractId(Long contractId);

    void updateEmlNo(CstNptsEmlHeaderEntity entity);
}

