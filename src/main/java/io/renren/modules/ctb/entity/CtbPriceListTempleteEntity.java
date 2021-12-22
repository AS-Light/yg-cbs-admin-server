package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 报关行报价单模板，原则上报关行只有一个模板，有一个ctb_tenant_id=-1的模板作为公共模板
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_price_list_templete")
public class CtbPriceListTempleteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 模板数据json
	 */
	private String templeteJson;
	/**
	 * 模板pdf位置
	 */
	private String templetePdf;
	/**
	 * saas分离
	 */
	private Long ctbTenantId;

}
