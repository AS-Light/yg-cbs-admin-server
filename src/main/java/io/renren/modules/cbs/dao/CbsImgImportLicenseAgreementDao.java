package io.renren.modules.cbs.dao;

import io.renren.modules.cbs.entity.CbsImgImportLicenseAgreementEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 授权协议附件
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-18 15:16:54
 */
@Mapper
@Repository
public interface CbsImgImportLicenseAgreementDao extends BaseMapper<CbsImgImportLicenseAgreementEntity> {
    List<CbsImgImportLicenseAgreementEntity> listByImportId(Long importId);
}
