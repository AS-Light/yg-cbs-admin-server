package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 生产原料还库表，与cbs_store_goods_in表为1对1继承关系
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Data
@TableName("cbs_store_goods_in_produce_back")
public class CbsStoreGoodsInProduceBackEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联cbs_store_goods_out表，构成继承关系
	 */
	private Long fkStoreGoodsInId;
	/**
	 * 关联生产表（cbs_produce）ID
	 */
	private Long fkProduceId;

	@ApiModelProperty("商品入库父表")
	@TableField(exist = false)
	private CbsStoreGoodsInEntity goodsInEntity;
}
