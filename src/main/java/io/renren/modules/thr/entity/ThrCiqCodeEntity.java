package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 14:42:22
 */
@Data
@TableName("thr_ciq_code")
public class ThrCiqCodeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * HS编码
	 */
	private String hsCode;
	/**
	 * CIQ代码
	 */
	private String ciqCode;
	/**
	 * CIQ名称
	 */
	private String name;

}
