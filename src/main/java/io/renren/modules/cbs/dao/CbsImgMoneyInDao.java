package io.renren.modules.cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.cbs.entity.CbsImgMoneyInEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 箱单附件图片表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-09 11:28:51
 */
@Mapper
@Repository
public interface CbsImgMoneyInDao extends BaseMapper<CbsImgMoneyInEntity> {
    List<CbsImgMoneyInEntity> listByMoneyInId(Long moneyInId);
}
