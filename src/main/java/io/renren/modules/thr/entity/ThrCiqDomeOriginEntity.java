package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 国内原产地编码
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@Data
@TableName("thr_ciq_dome_origin")
public class ThrCiqDomeOriginEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 国内原产地编码
	 */
	private String ciqDomeOriginCode;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 简称
	 */
	private String shortName;
	/**
	 * 地区性质标记
	 */
	private String areaNatureTag;

}
