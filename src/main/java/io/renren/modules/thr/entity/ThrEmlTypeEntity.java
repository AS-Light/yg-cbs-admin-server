package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 加工贸易手册类型
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-16 11:36:01
 */
@Data
@TableName("thr_eml_type")
public class ThrEmlTypeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 类型编号
	 */
	private String code;
	/**
	 * 类型名
	 */
	private String name;

}
