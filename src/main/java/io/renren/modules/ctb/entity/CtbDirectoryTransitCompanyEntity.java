package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 国内运输公司名录表（报关行副本）
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@TableName("ctb_directory_transit_company")
public class CtbDirectoryTransitCompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 国内运输公司名称
	 */
	private String name;
	/**
	 * 联系人
	 */
	private String contactor;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 国内运输公司地址
	 */
	private String address;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 0为不可用1为可用
	 */
	private Integer available;
	/**
	 * saas业务分离，关联ctb_company_id
	 */
	private Long ctbTenantId;

}
