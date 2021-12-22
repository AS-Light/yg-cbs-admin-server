package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 合同过程中对应角色（成员）表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-16 16:24:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ctb_import_member")
public class CtbImportMemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 1、原料提供者 2、成品接收者 3、经营单位 4、加工单位 10、国内原料提供者
	 */
	private Integer type;
	/**
	 * 关联合同ID
	 */
	private Long fkImportId;
	/**
	 * 关联合作伙伴ID
	 */
	private Long fkPartnerId;

	@ApiModelProperty("ctb_partner")
	@TableField(exist = false)
	private CtbPartnerEntity partnerEntity;
}
