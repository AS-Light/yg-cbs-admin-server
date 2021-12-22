package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cbs_produce_goods_stream 的子表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-28 15:32:13
 */
@Data
@TableName("cbs_production_process_step_item")
public class CbsProductionProcessStepItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联生产工艺步骤ID
	 */
	private Long fkProductionProcessStepId;
	/**
	 * 原始原料或最终产物ID
	 */
	private Long fkDirectoryGoodsId;
	/**
	 * 关联中间产物ID
	 */
	private Long fkDirectoryIntermediateProductId;
	/**
	 * 1、原料，2、产物 如果是开始节点的原料使用原始原料 如果是结束节点的产物使用最终产物 否则原料或产物都是中间产物
	 */
	private Integer type;
	/**
	 * 被使用的原料或被生产的产品数量（配比）
	 */
	private BigDecimal count;

}
