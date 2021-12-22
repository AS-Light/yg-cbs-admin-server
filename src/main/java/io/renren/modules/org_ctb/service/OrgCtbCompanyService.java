package io.renren.modules.org_ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.org_ctb.entity.OrgCtbCompanyEntity;

import java.util.List;

/**
 * 用户公司表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-17 16:41:40
 */
public interface OrgCtbCompanyService extends IService<OrgCtbCompanyEntity> {

    PageUtils queryIndex(OrgCtbCompanyEntity entity);

    List<OrgCtbCompanyEntity> listWithCbsBind(Long cbsCompanyId, String ctbName);

    OrgCtbCompanyEntity selectByCode(String code);

    void saveCompany(OrgCtbCompanyEntity orgCtbCompany);

    void updateCompanyById(OrgCtbCompanyEntity orgCtbCompany);

    void updateCompanyInfoOnlyById(OrgCtbCompanyEntity orgCtbCompany);
}

