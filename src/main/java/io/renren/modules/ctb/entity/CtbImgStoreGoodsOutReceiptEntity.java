package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 出库签收证明图片表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@Data
@TableName("ctb_img_store_goods_out_receipt")
public class CtbImgStoreGoodsOutReceiptEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * ctb_store_goods_out  ID
	 */
	private Long fkStoreGoodsOutId;
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
