package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 报关行名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cbs_directory_customs_broker")
public class CbsDirectoryCustomsBrokerEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 报关行名称
     */
    private String name;
    /**
     * 联系人
     */
    private String contactor;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 报关行地址
     */
    private String address;
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

    @ApiModelProperty("报关行的服务企业表的ID ctb_directory_service_company ")
    @TableField(exist = false)
    private Long fkCtbServiceCompanyId;

    @ApiModelProperty("ctb_service_contract")
    @TableField(exist = false)
    private Long fkCtbServiceContractId;

    @ApiModelProperty("ctb_service_contract")
    @TableField(exist = false)
    private Integer status;

    @ApiModelProperty("bind")
    @TableField(exist = false)
    private BindCbsCustomsBrokerCtbCompanyEntity bindEntity;
}
