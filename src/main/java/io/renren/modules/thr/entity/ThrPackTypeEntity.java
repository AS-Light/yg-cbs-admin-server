package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 危包规格代码、辅助包装代码
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@Data
@TableName("thr_pack_type")
public class ThrPackTypeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 危包类别代码
	 */
	private String packTypeCode;
	/**
	 * 名称，简称
	 */
	private String name;
	/**
	 * 名称，详细
	 */
	private String detail;

}
