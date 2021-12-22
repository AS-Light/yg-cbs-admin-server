package io.renren.modules.bind.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.bind.entity.BindCbsCtbGoodsPartNoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 绑定cbs和ctb料号
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-23 13:27:59
 */
@Mapper
public interface BindCbsCtbGoodsPartNoDao extends BaseMapper<BindCbsCtbGoodsPartNoEntity> {
    BindCbsCtbGoodsPartNoEntity detailWithCbs(Long ctbGoodsPartNo);

    BindCbsCtbGoodsPartNoEntity detailWithCtb(Long cbsGoodsPartNo);
}
