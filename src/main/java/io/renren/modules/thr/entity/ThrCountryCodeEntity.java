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
 * @date 2019-12-21 15:45:17
 */
@Data
@TableName("thr_country_code")
public class ThrCountryCodeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 国别代码
	 */
	private String code;
	/**
	 * 三位缩写
	 * */
	private String shortKey;
	/**
	 * 中文名（简称）
	 */
	private String nameCn;
	/**
	 * 英文名（简称）
	 */
	private String nameEn;
	/**
	 * 优／普税率
	 */
	private String rateType;

}
