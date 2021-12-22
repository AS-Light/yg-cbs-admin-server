package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.thr.entity.ThrHsCodeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 原材料名录表（报关行副本）
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_directory_goods")
public class CtbDirectoryGoodsEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 海关商品手册HS编码
     */
    private String hsCode;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品实际名称，包含大类、简写、型号等
     */
    private String nickname;
    /**
     * 商品单位编码
     */
    private String unitCode;
    /**
     * 商品单位
     */
    private String unit;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     * 0为不可用1为可用
     */
    private Integer available;
    /**
     * saas业务分离，关联ctb_company_id
     */
    private Long ctbTenantId;

    @ApiModelProperty("关联HS编码表")
    @TableField(exist = false)
    private ThrHsCodeEntity hsCodeEntity;
}
