package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsImgSellReceiptEntity;
import io.renren.modules.cbs.entity.CbsSellEntity;
import io.renren.modules.cbs.entity.CbsSellStatusStreamEntity;

import java.util.List;

/**
 * 商品入库表，
 * 继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，
 * 原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
public interface CbsSellService extends IService<CbsSellEntity> {

    PageUtils queryIndex(CbsSellEntity entity);

    List<CbsSellEntity> queryForStoreOut();

    Long saveReturnId(CbsSellEntity entity) throws RuntimeException;

    CbsSellEntity detail(Long id);

    Object updateAllById(CbsSellEntity entity);

    void submit(CbsSellStatusStreamEntity statusStreamEntity) throws RuntimeException;

    Integer submitBack(CbsSellStatusStreamEntity statusStreamEntity);

    void check(CbsSellStatusStreamEntity statusStreamEntity, Long operator) throws RuntimeException;

    void updateReceipt(Long sellId, List<CbsImgSellReceiptEntity> imgReceiptList, Long operator);

    void checkReceipt(Long sellId, Long operator) throws RuntimeException;

    void deleteByIds(Long[] ids);
}

