package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 收入/支出类型
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ctb_money_type")
public class CtbMoneyTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联服务对象公司（ctb_directory_service_company）id
	 */
	private Long fkServiceCompanyId;
	/**
	 * 收支项目名称
	 */
	private String name;
	/**
	 * 默认单价
	 */
	private BigDecimal defUnitPrice;
	/**
	 * 默认单位
	 */
	private String defUnit;
	/**
	 * 默认税点
	 */
	private Integer defTaxRate;
	/**
	 * I 收 O 支
	 */
	private String io;
	/**
	 * 是否可报销
	 */
	private Boolean isReimburse;
	/**
	 * 是否可用
	 */
	private Boolean available;
	/**
	 * saas业务分离
	 */
	private Long ctbTenantId;

}
