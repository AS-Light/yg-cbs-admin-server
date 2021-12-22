package io.renren.modules.bind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.renren.modules.ctb.entity.CtbServiceContractEntity;
import io.renren.modules.org_cbs.entity.OrgCbsCompanyEntity;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 17:06:26
 */
public interface BindCbsCustomsBrokerCtbCompanyService extends IService<BindCbsCustomsBrokerCtbCompanyEntity> {

    /**
     * 列表（queryIndex）
     **/
    PageUtils queryIndex(BindCbsCustomsBrokerCtbCompanyEntity entity);

    /**
     * 详情（detail）
     **/
    BindCbsCustomsBrokerCtbCompanyEntity detail(Long id);

    Long createBind(OrgCbsCompanyEntity cbsCompanyEntity, Long cbsDirectoryId, Long ctbCompanyId);

    CtbServiceContractEntity selectServiceContract(BindCbsCustomsBrokerCtbCompanyEntity entity);

    void initiateConfirmation(BindCbsCustomsBrokerCtbCompanyEntity entity);

    void confirmContract(BindCbsCustomsBrokerCtbCompanyEntity build);
}

