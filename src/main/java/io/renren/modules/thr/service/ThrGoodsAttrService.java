package io.renren.modules.thr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.thr.entity.ThrGoodsAttrEntity;

/**
 * 海关货物属性编码
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
public interface ThrGoodsAttrService extends IService<ThrGoodsAttrEntity> {

    PageUtils queryIndex(ThrGoodsAttrEntity entity);

    ThrGoodsAttrEntity getOneByCode(ThrGoodsAttrEntity entity);
}

