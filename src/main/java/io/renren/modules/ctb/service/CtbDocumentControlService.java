package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbDocumentControlEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-16 15:09:03
 */
public interface CtbDocumentControlService extends IService<CtbDocumentControlEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

