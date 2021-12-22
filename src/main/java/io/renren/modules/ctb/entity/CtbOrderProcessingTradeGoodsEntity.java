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
 * 加贸商品名录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_order_processing_trade_goods")
public class CtbOrderProcessingTradeGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联加贸单表（ctb_order_processing_trade）
	 */
	private Long fkOrderProcessingTradeId;
	/**
	 * 商品在加贸单中序号
	 */
	private Integer indexInOrderProcessingTrade;
	/**
	 * 1、进口料 2、出口产品 3、国内料
	 */
	private Integer type;

	@ApiModelProperty("料号商品实体")
	@TableField(exist = false)
	private CtbGoodsPartNoEntity partNoEntity;
}
