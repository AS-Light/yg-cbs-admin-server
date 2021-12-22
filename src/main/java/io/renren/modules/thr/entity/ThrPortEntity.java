package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 国际口岸（亦表示：港口、启运口岸、经停口岸、国际抵达口岸等）
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-29 15:28:41
 */
@Data
@TableName("thr_port")
public class ThrPortEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 港口代码
	 */
	private String portCode;
	/**
	 * 中文名
	 */
	private String nameCn;
	/**
	 * 英文名
	 */
	private String nameEn;

}
