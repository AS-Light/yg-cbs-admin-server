package io.renren.modules.bind.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.bind.entity.BindCbsCtbImportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 关联表：cbs_contract | ctb_order_prodcessing_trade
 * 方法概要：
 * 1、列表（queryIndex）：获取列表时获取关联的列表详情，即关联出cbs_import和ctb_import相关信息
 * 2、详情（detail）：同时获取cbs_import和ctb_import详情，即两个detail
 * 3、带cbs信息详情（detailWithCbs）：只带有cbs的详情，方便ctb调用，dao层间调用
 * 4、带ctb信息详情（detailWithCtb）：只带有ctb的详情，方便cbs调用，dao层间调用
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:34:21
 */
@Mapper
public interface BindCbsCtbImportDao extends BaseMapper<BindCbsCtbImportEntity> {
    IPage<BindCbsCtbImportEntity> queryIndex(IPage<BindCbsCtbImportEntity> page, @Param(Constants.WRAPPER) BindCbsCtbImportEntity entity);

    BindCbsCtbImportEntity detail(Long id);

    BindCbsCtbImportEntity detailWithCbs(Long ctbImportId);

    BindCbsCtbImportEntity detailWithCtb(Long cbsImportId);
}
