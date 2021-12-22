package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsContractMemberEntity;

import java.util.Map;

/**
 * 合同过程中对应角色（成员）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-25 11:14:59
 */
public interface CbsContractMemberService extends IService<CbsContractMemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    CbsContractMemberEntity detail(Long id);
}

