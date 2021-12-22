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
 * cbs_produce_goods_stream 的子表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:15:37
 */
@Data
@TableName("cbs_produce_goods_stream_item")
@Alias("CbsProduceGoodsStreamItemEntity")
public class CbsProduceGoodsStreamItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * cbs_produce_goods_stream ID
	 */
	private Long fkProduceGoodsStreamId;
	/**
	 * 商品料号（即合同商品编号）
	 */
	private Long goodsPartNo;
	/**
	 * 入库产品数量
	 */
	private BigDecimal count;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 1原料，2产品
	 */
	private Integer type;

	@ApiModelProperty("料号实体")
	@TableField(exist = false)
	private CbsGoodsPartNoEntity partNoEntity;
}
