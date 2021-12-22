package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
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
 * 报关行服务公司名录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_directory_service_company")
public class CtbDirectoryServiceCompanyEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 公司名称
	 */
	private String name;
	/**
	 * 海关编码
	 */
	private String customsCode;
	/**
	 * 商检编码
	 */
	private String inspectionCode;
	/**
	 * 社会信用代码
	 */
	private String creditCode;
	/**
	 * AEO认证代码
	 */
	private String aeoCode;
	/**
	 * 联系人
	 */
	private String contactor;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 公司地址
	 */
	private String address;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 0为不可用1为可用
	 */
	private Integer available;
	/**
	 * 创建人
	 */
	private Long createUserId;
	/**
	 * saas业务分离
	 */
	private Long ctbTenantId;

    /**
     * 是否需要合并，企业绑定生成的是true，报关行自建的是false，企业绑定同步后是true
     */
    private Boolean needSyn;

    @ApiModelProperty("企业的报关行名录的ID cbs_directory_customs_broker ")
    @TableField(exist = false)
    private Long fkCbsCustomBrokerId;

    @ApiModelProperty("cbs tenantId")
    @TableField(exist = false)
    private Long tenantId;

    @ApiModelProperty("预删除的ID")
    @TableField(exist = false)
    private Long preDeleteId;

    @ApiModelProperty("报关行服务企业的合同")
    @TableField(exist = false)
    private BindCbsCustomsBrokerCtbCompanyEntity bind;

}
