package io.renren.modules.cst.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.cst.entity.CstNptsEmlConsumeEntity;

import java.util.Map;

/**
 * 加工贸易手册单耗表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-14 10:42:32
 */
public interface CstNptsEmlConsumeService extends IService<CstNptsEmlConsumeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

