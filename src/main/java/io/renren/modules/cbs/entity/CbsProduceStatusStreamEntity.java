package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 生产计划状态变更表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-06 11:40:38
 */
@Data
@TableName("cbs_produce_status_stream")
public class CbsProduceStatusStreamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联生产计划表
	 */
	private Long fkProduceId;
	/**
	 * 操作人
	 */
	private Long operator;
	/**
	 * 上一状态（变更前）
	 */
	private Integer lastStatus;
	/**
	 * 状态 1、创建未启动 2、启动提审 3、启动退审 4、已启动 5、完成提审 6、完成退审 7、已完成
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;

	@ApiModelProperty("（明知有问题的情况下）强制变更状态")
	@TableField(exist = false)
	private Boolean forceToChange;
}
