/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_cbs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.org_cbs.entity.OrgCbsMenuEntity;
import io.renren.modules.sys.entity.SysCbsMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
@Repository
public interface OrgCbsMenuDao extends BaseMapper<OrgCbsMenuEntity> {

    /**
     * 获取全部权限，包含权限内容
     */
    List<OrgCbsMenuEntity> selectSimpleDetailList();

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<OrgCbsMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<OrgCbsMenuEntity> queryNotButtonList();

    /**
     * @Description: 查询该公司下所有权限菜单
     * @Param:
     * @return:
     * @Author: chenning
     * @Date: 2019/12/22
     */
    List<SysCbsMenuEntity> selectSysCbsMenuList();
}
