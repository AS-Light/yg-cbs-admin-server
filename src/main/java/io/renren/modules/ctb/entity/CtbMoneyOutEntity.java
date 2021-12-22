package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.ctb.vo.OrderProcessingTradeVo;
import io.renren.modules.org_ctb.entity.OrgCtbAccountEntity;
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
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
 * 进口单产生的支出流水
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("CtbMoneyOutEntity")
@TableName("ctb_money_out")
public class CtbMoneyOutEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 服务企业
     */
    private Long fkServiceCompanyId;
    /**
     * 单类型 1、加贸 2、进口 3、出口
     */
    private Integer orderType;
    /**
     * 单id 依据单类型关联 ctb_order_processing_trade ctb_import ctb_export
     */
    private Long fkOrderId;
    /**
     * 关联收入支出类型（sys_money_type）
     */
    private Long fkMoneyTypeId;
    /**
     * 关联结算账户（org_ctb_account）
     */
    private Long fkAccountId;
    /**
     * 标题，必填项，即简要描述
     */
    private String title;
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
     * 关联币制（thr_currency）
     */
    private String currencyCode;
    /**
     * 1、未核 2、已核 99、3、 附件未核 4、附件已核
     */
    private Integer status;
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
     * 实际确核后人民币金额
     */
    private BigDecimal cny;
    /**
     * 0：可以报销，1：已经报销
     */
    private Boolean isReimbursement;
    /**
     * saas业务分离，关联ctb_company_id
     */
    private Long ctbTenantId;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private OrderProcessingTradeVo searchForm;

    @ApiModelProperty("服务企业实体")
    @TableField(exist = false)
    private CtbDirectoryServiceCompanyEntity serviceCompanyEntity;

    @ApiModelProperty("附件实体列表")
    @TableField(exist = false)
    private List<CtbImgMoneyOutEntity> imgMoneyOutList;
    @ApiModelProperty("账号实体")
    @TableField(exist = false)
    private OrgCtbAccountEntity accountEntity;
    @ApiModelProperty("收费类型实体")
    @TableField(exist = false)
    private CtbMoneyTypeEntity moneyTypeEntity;
    @ApiModelProperty("货币实体")
    @TableField(exist = false)
    private ThrCurrencyEntity currencyEntity;
    @ApiModelProperty("操作人")
    @TableField(exist = false)
    private OrgCtbUserEntity operatorEntity;

    @ApiModelProperty("服务的企业名称")
    @TableField(exist = false)
    private String cbsCompanyName;

}
