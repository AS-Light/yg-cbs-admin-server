package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 生产出库表，与cbs_store_goods_out表为1对1继承关系
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Data
@TableName("cbs_store_goods_out_produce")
public class CbsStoreGoodsOutProduceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联cbs_store_goods_out表，构成继承关系
	 */
	private Long fkStoreGoodsOutId;
	/**
	 * 关联生产表（cbs_produce）ID
	 */
	private Long fkProduceId;

	@ApiModelProperty("商品出库父表")
	@TableField(exist = false)
	private CbsStoreGoodsOutEntity goodsOutEntity;
}
