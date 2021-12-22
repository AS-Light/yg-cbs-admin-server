package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 海关加工种类代码表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-16 12:59:16
 */
@Data
@TableName("thr_product_type")
public class ThrProductTypeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 代码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;

}
