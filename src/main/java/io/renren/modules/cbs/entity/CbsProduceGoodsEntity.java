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
 * 生产的商品缓存
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:47:33
 */
@Data
@TableName("cbs_produce_goods")
public class CbsProduceGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 关联生成单表（cbs_produce）ID
	 */
	private Long fkProduceId;
	/**
	 * 商品料号（即合同商品编号）
	 */
	private Long goodsPartNo;
	/**
	 * 商品类型：1原料2产品
	 */
	private Integer goodsType;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 操作人
	 */
	private Long operator;
	/**
	 * 商品生产总数量
	 */
	private BigDecimal totalCount;
	/**
	 * 商品库存总数量
	 */
	private BigDecimal storeCount;
	/**
	 * 计划数量
	 */
	private BigDecimal planCount;

	@ApiModelProperty("料号实体")
	@TableField(exist = false)
	private CbsGoodsPartNoEntity partNoEntity;
}
