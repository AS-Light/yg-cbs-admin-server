package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同父表单，继承表单如加工贸易合同ctb_contract_processing、国内采购合同单ctb_contract_purchase等
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-09 15:13:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ctb_order_processing_trade_manager_change_stream")
public class CtbOrderProcessingTradeManagerChangeStreamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联出口表
	 */
	private Long fkOrderProcessingTradeId;
	/**
	 * 操作人
	 */
	private Long operator;
	/**
	 * 上一负责人（变更前）
	 */
	private Long lastManager;
	/**
	 * 当前负责人
	 */
	private Long manager;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
