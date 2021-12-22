package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ctb.entity.CtbImgMoneyTicketsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 箱单附件图片表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
@Repository
public interface CtbImgMoneyTicketsDao extends BaseMapper<CtbImgMoneyTicketsEntity> {
    List<CtbImgMoneyTicketsEntity> listByMoneyTicketsId(Long moneyTicketsId);
}
