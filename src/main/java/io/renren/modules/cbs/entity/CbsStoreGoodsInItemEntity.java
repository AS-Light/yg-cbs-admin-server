package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品入库表，
继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，
原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Data
@TableName("cbs_store_goods_in_item")
@Alias("CbsStoreGoodsInItemEntity")
public class CbsStoreGoodsInItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联仓库名录表（cbs_directory_store）ID
	 */
	private Long fkStoreGoodsInId;
	/**
	 * 商品料号（即合同商品编号）
	 */
	private Long goodsPartNo;
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

	@ApiModelProperty("料号实体")
	@TableField(exist = false)
	private CbsGoodsPartNoEntity partNoEntity;

}
