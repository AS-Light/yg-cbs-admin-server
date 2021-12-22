package io.renren.modules.ctb.dao;

import io.renren.modules.ctb.entity.CtbImgExportLicenseAgreementEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出口报关授权协议附件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbImgExportLicenseAgreementDao extends BaseMapper<CtbImgExportLicenseAgreementEntity> {
    List<CtbImgExportLicenseAgreementEntity> listByExportId(Long exportId);
}
