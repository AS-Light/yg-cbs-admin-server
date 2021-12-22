package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 出口货物清单
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@Data
@TableName("cbs_sell_goods")
public class CbsSellGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联cbs_sell
	 */
	private Long fkSellId;
	/**
	 * 商品料号（即合同商品编号）
	 */
	private Long goodsPartNo;
	/**
	 * 商品数量，可能包含小数
	 */
	private BigDecimal count;
	/**
	 * 已出库调拨商品数量
	 */
	private BigDecimal storeOutCount;
	/**
	 * 出库源商品料号（即从该料号库存出库）
	 */
	private Long outFromGoodsPartNo;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

	@ApiModelProperty("料号实体")
	@TableField(exist = false)
	private CbsGoodsPartNoEntity partNoEntity;
}
