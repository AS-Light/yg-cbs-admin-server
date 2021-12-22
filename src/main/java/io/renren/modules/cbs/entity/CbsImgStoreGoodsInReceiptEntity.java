package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 入库签收证明图片表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-24 09:10:51
 */
@Data
@TableName("cbs_img_store_goods_in_receipt")
public class CbsImgStoreGoodsInReceiptEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * cbs_store_goods_in  ID
	 */
	private Long fkStoreGoodsInId;
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
