package io.renren.modules.cbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cbs.entity.CbsDocumentControlEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-27 10:02:18
 */
public interface CbsDocumentControlService extends IService<CbsDocumentControlEntity> {

    PageUtils queryPage(CbsDocumentControlEntity params);
}

