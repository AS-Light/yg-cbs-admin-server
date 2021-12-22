package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.renren.common.utils.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 原材进货记录
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-18 15:18:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cbs_export_voyage")
@Alias("CbsExportVoyageEntity")
public class CbsExportVoyageEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 关联出口表
     */
    private Long fkExportId;
    /**
     * 关联船务公司名录表（cbs_directory_ship_company）ID
     */
    private Long fkShipCompanyId;
    /**
     * 运输工具名称
     */
    private String transportName;
    /**
     * 航次号
     */
    private String voyageNo;
    /**
     * 提单号
     * 设置JsonProperty，否则json解析时会解析成全小写，原因不明
     */
    @JsonProperty("bLNo")
    private String bLNo;
    /**
     * 创建时间
     */
    private Date createTime;

    @ApiModelProperty("经停港，入参，变更航次用但是传入出口单")
    @TableField(exist = false)
    private String passPortCode;

    @ApiModelProperty("船务公司实体")
    @TableField(exist = false)
    private CbsDirectoryShipCompanyEntity shipCompanyEntity;
}
