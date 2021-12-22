package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 进口单费用明细
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@TableName("ctb_order_processing_trade_price_item")
public class CtbOrderProcessingTradePriceItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联加贸单表（ctb_order_processing_trade）
     */
    private Long fkOrderProcessingTradeId;
    /**
     * 关联收支类型（ctb_money_type）
     */
    private Long fkMoneyTypeId;
    /**
     * 单位金额
     */
    private BigDecimal unitMoney;
    /**
     * 单位
     */
    private String unit;
    /**
     * 发生数量
     */
    private BigDecimal count;
    /**
     * 发生金额
     */
    private BigDecimal money;
    /**
     * 关联币制（thr_currency）默认142 人民币
     */
    private String currencyCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间，即作废时间
     */
    private Date updateTime;
    /**
     * 操作人
     */
    private Long operator;
    /**
     * 备注，非必填
     */
    private String rmk;

    @ApiModelProperty("收费类型实体")
    @TableField(exist = false)
    private CtbMoneyTypeEntity moneyTypeEntity;

    @ApiModelProperty("币制实体")
    @TableField(exist = false)
    private ThrCurrencyEntity currencyEntity;
}
