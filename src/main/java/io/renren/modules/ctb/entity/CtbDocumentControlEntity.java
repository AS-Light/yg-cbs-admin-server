package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-16 15:09:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_document_control")
public class CtbDocumentControlEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String businessTable;
	/**
	 * 
	 */
	private Long businessId;
	/**
	 * 
	 */
	private String businessFile;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * saas业务分离，关联ctb_company_id
	 */
	private Long ctbTenantId;

}
