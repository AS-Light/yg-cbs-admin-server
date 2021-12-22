package io.renren.modules.org_ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.org_ctb.entity.OrgCtbAccountEntity;

import java.util.Map;

/**
 * 结算账户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
public interface OrgCtbAccountService extends IService<OrgCtbAccountEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

