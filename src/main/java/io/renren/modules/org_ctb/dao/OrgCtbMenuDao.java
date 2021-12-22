/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_ctb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.org_ctb.entity.OrgCtbMenuEntity;
import io.renren.modules.sys.entity.SysCtbMenuEntity;
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
public interface OrgCtbMenuDao extends BaseMapper<OrgCtbMenuEntity> {

    /**
     * 获取全部权限，包含权限内容
     */
    List<OrgCtbMenuEntity> selectSimpleDetailList();

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<OrgCtbMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<OrgCtbMenuEntity> queryNotButtonList();

    /**
     * @Description: 查询该公司下所有权限菜单
     * @Param:
     * @return:
     * @Author: chenning
     * @Date: 2019/12/22
     */
    List<SysCtbMenuEntity> selectSysCtbMenuList();
}
