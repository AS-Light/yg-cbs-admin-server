package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.bind.entity.BindCbsCtbGoodsPartNoEntity;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
import io.renren.modules.thr.entity.ThrHsUnitEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_goods_part_no")
public class CtbGoodsPartNoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商品料号（即合同商品编号）
	 */
	private Long goodsPartNo;
	/**
	 * 关联商品名录表
	 */
	private Long fkGoodsId;
	/**
	 * 币制代码
	 */
	private String currencyCode;
	/**
	 * 结算单位
	 */
	private String unitCode;
	/**
	 * 单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 合同商品数量
	 */
	private BigDecimal contractCount;
	/**
	 * 合同总价
	 */
	private BigDecimal contractPrice;

	@ApiModelProperty("商品实体")
	@TableField(exist = false)
	private CtbDirectoryGoodsEntity goodsEntity;

	@ApiModelProperty("单位实体")
	@TableField(exist = false)
	private ThrHsUnitEntity unitEntity;

	@ApiModelProperty("币制实体")
	@TableField(exist = false)
	private ThrCurrencyEntity currencyEntity;

	@ApiModelProperty("绑定Ctb")
	@TableField(exist = false)
	private BindCbsCtbGoodsPartNoEntity bindEntity;
}
