package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 出口箱单附件图片表
 * 
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-31 11:47:21
 */
@Data
@TableName("cbs_img_export_packing_list")
public class CbsImgExportPackingListEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联出口单主表（cbs_export）ID
	 */
	private Long fkExportId;
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
