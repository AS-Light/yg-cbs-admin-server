package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 国内关区（关别）代码
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@Data
@TableName("thr_decl_port")
public class ThrDeclPortEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 关区代码
	 */
	private String declPort;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 简称
	 */
	private String shortName;
	/**
	 * 备注
	 */
	private String note;

}
