package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 随附单证类型代码表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-06 18:13:02
 */
@Data
@TableName("thr_license_docus")
public class ThrLicenseDocusEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;

}
