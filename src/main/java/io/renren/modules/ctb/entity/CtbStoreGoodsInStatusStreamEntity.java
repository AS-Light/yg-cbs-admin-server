package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同父表单，继承表单如加工贸易合同cbs_contract_processing、国内采购合同单cbs_contract_purchase等
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@Data
@TableName("ctb_store_goods_in_status_stream")
public class CtbStoreGoodsInStatusStreamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 关联入库表
	 */
	private Long fkStoreGoodsInId;
	/**
	 * 操作人
	 */
	private Long operator;
	/**
	 * 上一状态（变更前）
	 */
	private Integer lastStatus;
	/**
	 * 当前状态
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

}
