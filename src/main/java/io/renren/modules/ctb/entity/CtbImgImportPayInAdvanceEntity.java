package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 首付款附件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@TableName("ctb_img_import_pay_in_advance")
public class CtbImgImportPayInAdvanceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联进口单主表（ctb_import）ID
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
