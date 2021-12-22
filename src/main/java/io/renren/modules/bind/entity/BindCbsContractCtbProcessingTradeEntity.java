package io.renren.modules.bind.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.cbs.entity.CbsContractEntity;
import io.renren.modules.ctb.entity.CtbOrderProcessingTradeEntity;
import io.renren.modules.org_cbs.entity.OrgCbsCompanyEntity;
import io.renren.modules.org_ctb.entity.OrgCtbCompanyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 关联表：cbs_contract | ctb_order_prodcessing_trade
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:34:21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("bind_cbs_contract_ctb_processing_trade")
public class BindCbsContractCtbProcessingTradeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联甲方公司，即cbs公司表（org_cbs_company）
     */
    private Long fkCbsCompanyId;
    /**
     * 关联乙方公司，即ctb公司表（org_ctb_company）
     */
    private Long fkCtbCompanyId;
    /**
     * 关联cbs合同表（cbs_contract）
     */
    private Long fkCbsContractId;
    /**
     * 关联ctb加贸表（ctb_order_processing_trade）
     */
    private Long fkCtbProcessingTradeId;
    /**
     * 是否同步给报关行进行操作
     */
    private Boolean isWorkByCtb;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间（自动记录最后更新时间）
     */
    private Date updateTime;

    @ApiModelProperty("org_cbs_company")
    @TableField(exist = false)
    private OrgCbsCompanyEntity cbsCompanyEntity;

    @ApiModelProperty("org_ctb_company")
    @TableField(exist = false)
    private OrgCtbCompanyEntity ctbCompanyEntity;

    @ApiModelProperty("cbs_contract")
    @TableField(exist = false)
    private CbsContractEntity cbsContractEntity;

    @ApiModelProperty("ctb_order_processing_trade")
    @TableField(exist = false)
    private CtbOrderProcessingTradeEntity ctbOrderProcessingTradeEntity;
}
