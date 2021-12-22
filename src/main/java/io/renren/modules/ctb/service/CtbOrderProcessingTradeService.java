package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeEntity;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeStatusStreamEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 报关行加贸（手册）业务表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbOrderProcessingTradeService extends IService<CtbOrderProcessingTradeEntity> {

    PageUtils queryIndex(CtbOrderProcessingTradeEntity entity);

    Long saveReturnId(CtbOrderProcessingTradeEntity entity);

    CtbOrderProcessingTradeEntity detail(Long id);

    Integer updateAllById(CtbOrderProcessingTradeEntity entity);

    void updateEmlCode(CtbOrderProcessingTradeEntity entity);

    Integer deleteOne(CtbOrderProcessingTradeEntity entity);

    void submit(CtbOrderProcessingTradeStatusStreamEntity entity) throws RuntimeException;

    Integer submitBack(CtbOrderProcessingTradeStatusStreamEntity entity);

    void check(CtbOrderProcessingTradeStatusStreamEntity entity) throws RuntimeException;

    HashMap<String, Object> deleteByIds(Long[] ids);

    String AEOExportExcel(HttpServletResponse response, CtbOrderProcessingTradeEntity entity);

    void distributionManager(Long orderProcessingTradeId, Long nowManager, Long operator);
}

