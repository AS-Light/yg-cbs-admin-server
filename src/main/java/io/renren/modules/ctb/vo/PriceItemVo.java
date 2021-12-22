package io.renren.modules.ctb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceItemVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tableName;
    private String parameterField;
    private Long parameterId;
    // 服务企业
    private Long serviceCompanyId;
    // 单类型
    private Integer orderType;


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

    /**
     * 0：不可报销，1：可以报销
     */
    private Boolean isReimbursement;

    /**
     * 100该币种兑换人民币汇率
     */
    private BigDecimal exchangeRate;

    /**
     * I 收 O 支
     */
    private String io;
    /**
     * 收支项目名称
     */
    private String name;

}
