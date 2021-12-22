package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 国内收货提货单图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@Data
@TableName("cbs_img_purchase_delivery_order")
public class CbsImgPurchaseDeliveryOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联国内收货单表（cbs_purchase）ID
	 */
	private Long fkPurchaseId;
	/**
	 * 图片地址（后缀）
	 */
	private String imgUrl;
	/**
	 * 在同一（采购）合同中的排序顺序
	 */
	private Integer sort;
	/**
	 * 是否有效:0无效，1有效
	 */
	private Integer isValid;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

}
