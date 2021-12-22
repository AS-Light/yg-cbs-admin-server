package io.renren.modules.org_cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.org_cbs.entity.OrgCbsCompanyEntity;

/**
 * 用户公司表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-17 16:41:40
 */
public interface OrgCbsCompanyService extends IService<OrgCbsCompanyEntity> {

    PageUtils queryIndex(OrgCbsCompanyEntity entity);

    OrgCbsCompanyEntity selectByCode(String code);

    void saveCompany(OrgCbsCompanyEntity orgCbsCompany);

    void updateCompanyById(OrgCbsCompanyEntity orgCbsCompany);

    void updateCompanyInfoOnlyById(OrgCbsCompanyEntity orgCbsCompany);
}

