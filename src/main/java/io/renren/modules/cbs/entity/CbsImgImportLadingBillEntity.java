package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.renren.common.utils.BaseEntity;
import lombok.Data;

/**
 * 进口提单图片表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-24 15:20:25
 */
@Data
@TableName("cbs_img_import_lading_bill")
public class CbsImgImportLadingBillEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联进口单主表（cbs_contract）ID
	 */
	private Long fkImportId;
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
