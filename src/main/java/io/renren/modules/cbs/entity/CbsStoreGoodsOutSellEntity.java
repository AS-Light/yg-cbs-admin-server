package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 国内采购入库表，与cbs_store_goods_in表为1对1继承关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@Data
@TableName("cbs_store_goods_out_sell")
public class CbsStoreGoodsOutSellEntity implements Serializable {
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
	 * 关联国内销售表（cbs_sell）ID
	 */
	private Long fkSellId;

	@ApiModelProperty("商品出库父表")
	@TableField(exist = false)
	private CbsStoreGoodsOutEntity goodsOutEntity;

}
