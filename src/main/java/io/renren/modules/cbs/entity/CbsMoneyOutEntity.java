package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.cbs.vo.ContractVo;
import io.renren.modules.org_cbs.entity.OrgCbsAccountEntity;
import io.renren.modules.org_cbs.entity.OrgCbsUserEntity;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
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
 * @date 2020-01-20 12:25:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("CbsMoneyOutEntity")
@TableName("cbs_money_out")
public class CbsMoneyOutEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 关联收入支出类型（cbs_money_type）
     */
    private Integer type;
    /**
     * 关联（自建）收入支出类型（cbs_money_type_oneself）
     */
    private Long fkTypeOneselfId;
    /**
     * 关联合同单（cbs_contract）
     */
    private Long fkContractId;
    /**
     * 关联结算账户（org_cbs_account）
     */
    private Long fkAccountId;
    /**
     * 标题，必填项，即简要描述
     */
    private String title;
    /**
     * 发生金额
     */
    private BigDecimal money;
    /**
     * 人民币金额
     */
    private BigDecimal cny;
    /**
     * 关联币制（thr_currency）
     */
    private String currencyCode;
    /**
     * 1、未核 2、已核 99、作废
     */
    private Integer status;
    /**
     * 是否自建收入
     */
    private Boolean isBuildOneself;
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
     * saas业务分离，关联cbs_company_id
     */
    private Long tenantId;

    @ApiModelProperty("关联合同实体")
    @TableField(exist = false)
    private CbsContractEntity contractEntity;

    @ApiModelProperty("账户实体")
    @TableField(exist = false)
    private OrgCbsAccountEntity accountEntity;

    @ApiModelProperty("收入类型实体")
    @TableField(exist = false)
    private CbsMoneyTypeEntity typeEntity;

    @ApiModelProperty("（自建）收入类型实体")
    @TableField(exist = false)
    private CbsMoneyTypeOneselfEntity oneselfTypeEntity;

    @ApiModelProperty("币制实体")
    @TableField(exist = false)
    private ThrCurrencyEntity currencyEntity;

    @ApiModelProperty("附件实体列表")
    @TableField(exist = false)
    private List<CbsImgMoneyOutEntity> imgMoneyOutList;

    @ApiModelProperty("操作人实体")
    @TableField(exist = false)
    private OrgCbsUserEntity operatorEntity;

    @ApiModelProperty("查询范围-开始时间")
    @TableField(exist = false)
    private String startDate;
    @ApiModelProperty("查询范围-结束时间")
    @TableField(exist = false)
    private String endDate;

    @ApiModelProperty("自编类型实体")
    @TableField(exist = false)
    private CbsMoneyTypeOneselfEntity selfEditingType;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private ContractVo searchForm;
}
