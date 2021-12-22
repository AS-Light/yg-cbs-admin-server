package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeMemberEntity;

/**
 * 加贸单编辑过程中对应角色（成员）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbOrderProcessingTradeMemberService extends IService<CtbOrderProcessingTradeMemberEntity> {

    CtbOrderProcessingTradeMemberEntity detail(Long id);
}

