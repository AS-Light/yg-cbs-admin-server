package io.renren.modules.cst.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.cst.dao.CstNptsEmlImgExgDao;
import io.renren.modules.cst.entity.CstNptsEmlImgExgEntity;
import io.renren.modules.cst.service.CstNptsEmlImgExgService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cstNptsEmlImgExgService")
public class CstNptsEmlImgExgServiceImpl extends ServiceImpl<CstNptsEmlImgExgDao, CstNptsEmlImgExgEntity> implements CstNptsEmlImgExgService {

    @Override
    public CstNptsEmlImgExgEntity getOneByGdsMtno(CstNptsEmlImgExgEntity entity) {
        if (entity.getGdsMtno() != null) {
            QueryWrapper<CstNptsEmlImgExgEntity> qw = new QueryWrapper<CstNptsEmlImgExgEntity>().eq("gds_mtno", entity.getGdsMtno());
            return this.getOne(qw);
        } else {
            return null;
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CstNptsEmlImgExgEntity> page = this.page(
                new Query<CstNptsEmlImgExgEntity>().getPage(params),
                new QueryWrapper<CstNptsEmlImgExgEntity>()
        );

        return new PageUtils(page);
    }

}