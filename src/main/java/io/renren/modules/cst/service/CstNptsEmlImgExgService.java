package io.renren.modules.cst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cst.entity.CstNptsEmlImgExgEntity;

import java.util.Map;

/**
 * 加工贸易手册原料表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-13 19:21:36
 */
public interface CstNptsEmlImgExgService extends IService<CstNptsEmlImgExgEntity> {

    PageUtils queryPage(Map<String, Object> params);

    CstNptsEmlImgExgEntity getOneByGdsMtno(CstNptsEmlImgExgEntity entity);
}

