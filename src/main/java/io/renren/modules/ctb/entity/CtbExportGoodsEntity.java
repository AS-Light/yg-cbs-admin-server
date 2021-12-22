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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 出口货物清单
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_export_goods")
public class CtbExportGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联ctb_export
	 */
	private Long fkExportId;
	/**
	 * 商品料号（即合同商品编号）
	 */
	private Long goodsPartNo;
	/**
	 * 商品数量，可能包含小数
	 */
	private BigDecimal count;
	/**
	 * 已调拨出库商品数量
	 */
	private BigDecimal storeOutCount;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

	@ApiModelProperty("料号实体")
	@TableField(exist = false)
	private CtbGoodsPartNoEntity partNoEntity;
}
