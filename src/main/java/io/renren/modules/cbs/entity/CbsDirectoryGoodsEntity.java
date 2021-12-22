package io.renren.modules.cbs.entity;

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
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 原材料名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 10:29:32
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cbs_directory_goods")
@Alias("CbsDirectoryGoodsEntity")
public class CbsDirectoryGoodsEntity extends BaseEntity implements Serializable {
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
     * 商品编码
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
     * Tenant
     */
    private Long tenantId;

    @ApiModelProperty("关联HS编码表")
    @TableField(exist = false)
    private ThrHsCodeEntity hsCodeEntity;
}
