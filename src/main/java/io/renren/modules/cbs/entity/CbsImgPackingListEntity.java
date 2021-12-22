package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.renren.common.utils.BaseEntity;
import lombok.Data;

/**
 * 箱单附件图片表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@Data
@TableName("cbs_img_packing_list")
public class CbsImgPackingListEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联进口单主表（cbs_import）ID
	 */
	private Long fkImportId;
	/**
	 * 图片地址（后缀）
	 */
	private String imgUrl;
	/**
	 * 在同一发票附件中的排序顺序
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
