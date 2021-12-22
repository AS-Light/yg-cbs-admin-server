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
 * 加贸单编辑过程中对应角色（成员）表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_order_processing_trade_member")
public class CtbOrderProcessingTradeMemberEntity implements Serializable {
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
	 * 关联加贸单表（ctb_order_processing_trade）
	 */
	private Long fkOrderProcessingTradeId;
	/**
	 * 关联报关行合作伙伴名录副本（ctb_partner_id）ID
	 */
	private Long fkPartnerId;
	/**
	 * saas业务分离
	 */
	private Long ctbTenantId;

	@ApiModelProperty("伙伴实体")
	@TableField(exist = false)
	private CtbPartnerEntity partnerEntity;
}
