package io.renren.modules.ctb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ctb.entity.CtbImgServiceContractEntity;

import java.util.List;
import java.util.Map;

/**
 * 报关行服务企业的合同附件表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-21 16:09:24
 */
public interface CtbImgServiceContractService extends IService<CtbImgServiceContractEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CtbImgServiceContractEntity> contractAnnexInfo(Long id);
}

