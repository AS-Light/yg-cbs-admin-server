package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品入库表，
继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，
原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@Data
@TableName("ctb_store_goods_in_item")
public class CtbStoreGoodsInItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;

	/**
	 * 关联ctb_store_goods_in
	 */
	private Long fkStoreGoodsInId;
	/**
	 * 商品
	 */
	private Long fkGoodsId;
	/**
	 * 入库产品数量
	 */
	private BigDecimal goodsInCount;
	/**
	 * 库存产品数量
	 */
	private BigDecimal goodsStoreCount;
	/**
	 * 创建时间
	 */
	private Date createTime;

	@ApiModelProperty("商品实体")
	@TableField(exist = false)
	private CtbDirectoryGoodsEntity goodsEntity;

}
