/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.org_cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.sys.entity.SysCbsMenuEntity;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@Alias("OrgCbsMenuEntity")
@TableName("org_cbs_menu")
public class OrgCbsMenuEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Long fkSysCbsMenuId;

    private Long tenantId;

    /**
     * ztree属性
     */
    @TableField(exist = false)
    private Boolean open;

    @TableField(exist = false)
    private List<?> list;

    @TableField(exist = false)
    private SysCbsMenuEntity sysCbsMenuEntity;

    /**
     * 菜单ID
     */
    @TableField(exist = false)
    private Long menuId;

    /**
     * 父菜单ID，一级菜单为0
     */
    @TableField(exist = false)
    private Long parentId;

    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 菜单名称
     */
    @TableField(exist = false)
    private String name;

    /**
     * 菜单URL
     */
    @TableField(exist = false)
    private String url;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @TableField(exist = false)
    private String perms;

    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    @TableField(exist = false)
    private Integer type;

    /**
     * 菜单图标
     */
    @TableField(exist = false)
    private String icon;

    /**
     * 排序
     */
    @TableField(exist = false)
    private Integer orderNum;


}
