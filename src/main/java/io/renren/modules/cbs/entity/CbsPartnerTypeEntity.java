package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * cbs_partner的type表 ，同一个partner可以有多种类型
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-25 11:46:38
 */
@Data
@TableName("cbs_partner_type")
public class CbsPartnerTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联cbs_partner
	 */
	private Long fkPartnerId;
	/**
	 * 1、采购（料件）来源方 2、销售（成品）对象 3、进出口（经营）单位 4、生产（加工）单位
	 */
	private Integer type;

}
