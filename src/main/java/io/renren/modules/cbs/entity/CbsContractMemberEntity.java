package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 合同过程中对应角色（成员）表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-25 11:14:59
 */
@Data
@TableName("cbs_contract_member")
public class CbsContractMemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 1、原料提供者 2、成品接收者 3、经营单位 4、加工单位
	 */
	private Integer type;
	/**
	 * 关联合同ID
	 */
	private Long fkContractId;
	/**
	 * 关联合作伙伴ID
	 */
	private Long fkPartnerId;

	@ApiModelProperty("伙伴实体")
	@TableField(exist = false)
	private CbsPartnerEntity partnerEntity;
}
