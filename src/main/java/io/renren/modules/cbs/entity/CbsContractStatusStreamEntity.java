package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-16 16:45:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cbs_contract_status_stream")
public class CbsContractStatusStreamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联合同表
	 */
	private Long fkContractId;
	/**
	 * 操作人
	 */
	private Long operator;
	/**
	 * 修改前状态
	 */
	private Integer lastStatus;
	/**
	 * 修改后状态
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;

	@ApiModelProperty("（明知有问题的情况下）强制变更状态")
	@TableField(exist = false)
	private Boolean forceToChange;
}
