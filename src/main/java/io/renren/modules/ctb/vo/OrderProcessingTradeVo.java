package io.renren.modules.ctb.vo;

import io.renren.common.utils.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 合同表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias(value = "OrderProcessingTradeVo")
public class OrderProcessingTradeVo extends BaseEntity implements Serializable {
    /**
     * 委托企业ID
     */
    private Long fkServiceCompanyId;
    /**
     * 合同标题
     */
    private String title;
    /**
     * 合同单号，由创建时间、我方ID、对方ID等构成的固定位数16进制长编码（装逼用）
     */
    private String contractCode;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 料号
     */
    private String partNo;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 类型 0进口 1出口
     */
    private Integer inOutType;
    /**
     * 开始时间
     */
    private String startingTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 最小金额
     */
    private BigDecimal minimumAmount;
    /**
     * 最大金额
     */
    private BigDecimal maximumAmount;
    /**
     * 状态   0：准备中   1：待审核   2：审核通过 3：已完结 10：不通过  99：已废除
     */
    private Integer status;

    /**
     * 出发时间
     */
    private String departTime;
    /**
     * 抵达时间
     */
    private String arrivalTime;


    /**
     * 开始时间段
     */
    private String startingTimeOne;
    private String startingTimeTwo;
    /**
     * 结束时间段
     */
    private String endTimeOne;
    private String endTimeTwo;

    /**
     * 仓库ID
     */
    private Long storeId;

    /**
     * 有无票据 0无1有
     */
    private Integer note;
    /**
     * 币制code
     */
    private Long currencyCode;
    /**
     * 财务类型 0：ctb_money_type 1：ctb_money_type_oneself
     */
    private Integer financeType;
    /**
     * 财务类型ID
     */
    private Long financeTypeId;


    private String hsCode;
    /**
     * 0小于1大于
     */
    private Integer storeCount;

    /**
     * 委托企业name
     */
    private String serviceCompanyName;

    /**
     * 负责人
     */
    private Long manager;

}
