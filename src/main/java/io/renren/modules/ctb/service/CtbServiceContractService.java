package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbServiceContractEntity;

import java.util.Map;

/**
 * 报关行货运代理合同
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbServiceContractService extends IService<CtbServiceContractEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Long create(CtbServiceContractEntity entity);

    String generatePDF(Long id);
}

