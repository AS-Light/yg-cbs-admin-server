package io.renren.modules.cst.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.cst.dao.CstNptsEmlHeaderDao;
import io.renren.modules.cst.entity.CstNptsEmlHeaderEntity;
import io.renren.modules.cst.service.CstNptsEmlHeaderService;
import org.springframework.stereotype.Service;


@Service("cstNptsEmlHeaderService")
public class CstNptsEmlHeaderServiceImpl extends ServiceImpl<CstNptsEmlHeaderDao, CstNptsEmlHeaderEntity> implements CstNptsEmlHeaderService {
    @Override
    public CstNptsEmlHeaderEntity selectByContractId(Long contractId) {
        return getOne(new QueryWrapper<CstNptsEmlHeaderEntity>().eq("fk_contract_id", contractId));
    }

    @Override
    public void updateEmlNo(CstNptsEmlHeaderEntity entity) {
        update(new UpdateWrapper<CstNptsEmlHeaderEntity>()
                .eq("id", entity.getId())
                .set("seq_no", entity.getSeqNo())
                .set("eml_no", entity.getEmlNo()));
    }
}