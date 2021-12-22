package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbDirectoryServiceCompanyEntity;

import java.util.List;
import java.util.Map;

/**
 * 报关行服务公司名录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
public interface CtbDirectoryServiceCompanyService extends IService<CtbDirectoryServiceCompanyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryIndex(CtbDirectoryServiceCompanyEntity entity);

    List<CtbDirectoryServiceCompanyEntity> listForChoice(CtbDirectoryServiceCompanyEntity entity);

    List<CtbDirectoryServiceCompanyEntity> selfEditingList(CtbDirectoryServiceCompanyEntity entity);
}

