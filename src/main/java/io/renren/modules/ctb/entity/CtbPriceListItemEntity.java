package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 报关行报价单表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@TableName("ctb_price_list_item")
public class CtbPriceListItemEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联报价单（ctb_price_list）id
     */
    private Long fkPriceListId;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 费用
     */
    private BigDecimal price;
    /**
     * 税率（整数，计算时除100），最大不超过100
     */
    private Integer taxRate;
    /**
     * 是否可报销
     */
    private Boolean isReimburse;
    /**
     * 单位
     */
    private String unit;
    /**
     * 备注
     */
    private String remark;

    @ApiModelProperty("合同ID ctb_service_contract")
    @TableField(exist = false)
    private Long contractId;

}
