package io.renren.modules.ctb.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import io.renren.modules.ctb.entity.CtbPriceListTempleteEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 报关行报价单模板，原则上报关行只有一个模板，有一个ctb_tenant_id=-1的模板作为公共模板
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Mapper
public interface CtbPriceListTempleteDao extends BaseMapper<CtbPriceListTempleteEntity> {

    @SqlParser(filter = true)
    CtbPriceListTempleteEntity selectByTenantId(Long tenantId);
}
