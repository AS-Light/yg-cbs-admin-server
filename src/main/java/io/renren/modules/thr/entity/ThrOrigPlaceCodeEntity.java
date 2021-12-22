package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 原产地区代码表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@Data
@TableName("thr_orig_place_code")
public class ThrOrigPlaceCodeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 原产地区代码
	 */
	private String origPlaceCode;
	/**
	 * iso2代码
	 */
	private String iso2;
	/**
	 * iso3代码
	 */
	private String iso3;
	/**
	 * 中文名
	 */
	private String nameCn;
	/**
	 * 英文名
	 */
	private String nameEn;
	/**
	 * 分类代码
	 */
	private String classCode;

}
