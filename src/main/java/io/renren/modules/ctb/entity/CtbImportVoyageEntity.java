package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 进口航次变更记录，生产报关以最后一次为准
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ctb_import_voyage")
public class CtbImportVoyageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联进口表
	 */
	private Long fkImportId;
	/**
	 * 关联船务公司名录表（ctb_directory_ship_company）ID
	 */
	private Long fkShipCompanyId;
	/**
	 * 运输工具名称
	 */
	private String transportName;
	/**
	 * 航次号
	 */
	private String voyageNo;
	/**
	 * 提单号
	 */
	@JsonProperty("bLNo")
	private String bLNo;
	/**
	 * 创建时间（排序依据）
	 */
	private Date createTime;

	@ApiModelProperty("经停港，入参，变更航次用但是传入出口单")
	@TableField(exist = false)
	private String passPortCode;

	@ApiModelProperty("船务公司实体")
	@TableField(exist = false)
	private CtbDirectoryShipCompanyEntity shipCompanyEntity;
}
