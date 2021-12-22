package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.ctb.vo.OrderProcessingTradeVo;
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 出口单产生的收入流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("CtbMoneyTicketsEntity")
@TableName("ctb_money_tickets")
public class CtbMoneyTicketsEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 服务的企业公司ID
     */
    private Long fkServiceCompanyId;
    /**
     * 发票抬头
     */
    private String invoice;
    /**
     * 发生金额
     */
    private BigDecimal money;
    /**
     * 税
     */
    private BigDecimal tax;
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
     * saas业务分离，关联ctb_company_id
     */
    private Long ctbTenantId;

    /**
     * 1：未核2：已核
     */
    private Integer status;

    @ApiModelProperty("服务企业公司")
    @TableField(exist = false)
    private String cbsCompanyName;

    @ApiModelProperty("附件实体列表")
    @TableField(exist = false)
    private List<CtbImgMoneyTicketsEntity> imgMoneyTicketsList;

    @ApiModelProperty("操作人")
    @TableField(exist = false)
    private OrgCtbUserEntity operatorEntity;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private OrderProcessingTradeVo searchForm;

    @ApiModelProperty("moneyInIdList")
    @TableField(exist = false)
    private List<Long> moneyInIdList;

    @ApiModelProperty("发票路径list")
    @TableField(exist = false)
    private List<String> imgInvoiceList;



}
