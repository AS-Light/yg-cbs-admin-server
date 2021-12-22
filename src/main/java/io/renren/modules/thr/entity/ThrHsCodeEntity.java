package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 14:42:21
 */
@Data
@TableName("thr_hs_code")
public class ThrHsCodeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * HS编码
	 */
	private String hsCode;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 出口税率
	 */
	private String exportTariff;
	/**
	 * 出口退税税率
	 */
	private String exportTaxRebateTariff;
	/**
	 * 出口暂定税率
	 */
	private String exportTemporaryTariff;
	/**
	 * 增值税率
	 */
	private String gainsTariff;
	/**
	 * 最惠国进口税率
	 */
	private String mfnImportTariff;
	/**
	 * 进口暂定税率
	 */
	private String importTemporaryTariff;
	/**
	 * 进口普通税率
	 */
	private String importTariff;
	/**
	 * 消费税率
	 */
	private String consumptionTariff;
	/**
	 * 第一法定单位
	 */
	private String firUnit;
	/**
	 * 第二法定单位
	 */
	private String secUnit;
	/**
	 * 第一法定单位代码
	 */
	private String firUnitCode;
	/**
	 * 第二法定单位代码
	 */
	private String secUnitCode;
	/**
	 * 监管条件代码缩写
	 */
	private String supervisionType;
	/**
	 * 监管条件详细信息
	 */
	private String supervisionInfo;
	/**
	 * 检验检疫类别代码缩写
	 */
	private String ciqType;
	/**
	 * 检验检疫类别详细信息
	 */
	private String ciqInfo;
	/**
	 * 申报要素
	 */
	private String reportElements;

	@ApiModelProperty("HS编码后接入CIQ编码")
	@TableField(exist = false)
	private List<ThrCiqCodeEntity> ciqCodeEntityList;
}
