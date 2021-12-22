package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 企业产品许可类别代码表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@Data
@TableName("thr_lic_type")
public class ThrLicTypeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 许可证类别代码
	 */
	private String licTypeCode;
	/**
	 * 
	 */
	private String name;
	/**
	 * 强制界别
	 */
	private String forceLevel;
	/**
	 * 证书类别
	 */
	private String certType;

}
