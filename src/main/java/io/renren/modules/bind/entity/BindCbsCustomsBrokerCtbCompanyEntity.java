package io.renren.modules.bind.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.cbs.entity.CbsDirectoryCustomsBrokerEntity;
import io.renren.modules.ctb.entity.CtbDirectoryServiceCompanyEntity;
import io.renren.modules.ctb.entity.CtbServiceContractEntity;
import io.renren.modules.org_cbs.entity.OrgCbsCompanyEntity;
import io.renren.modules.org_ctb.entity.OrgCtbCompanyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.io.Serializable;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 17:06:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("bind_cbs_customs_broker_ctb_company")
public class BindCbsCustomsBrokerCtbCompanyEntity implements Serializable {
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
     * 关联甲方公司报关行，即cbs公司报关行名录表（cbs_directory_custom_broker）
     */
    private Long fkCbsCustomBrokerId;
    /**
     * 关联乙方公司服务企业，即ctb公司服务企业名录表（ctb_directory_service_company）
     */
    private Long fkCtbServiceCompanyId;
    /**
     * 关联甲乙双方服务合同，即ctb服务合同名录（ctb_service_contract）
     */
    private Long fkCtbServiceContractId;

    /**
     * 1、未绑定（默认值，初建，或一方发起另一方不同意） 2、甲方发起绑定 3、乙方发起绑定 4、已绑定 99、无效（已删除，即软删除）
     */
    private Integer status;

    @ApiModelProperty("org_cbs_company")
    @TableField(exist = false)
    private OrgCbsCompanyEntity cbsCompanyEntity;

    @ApiModelProperty("org_ctb_company")
    @TableField(exist = false)
    private OrgCtbCompanyEntity ctbCompanyEntity;

    @ApiModelProperty("cbs_custom_broker")
    @TableField(exist = false)
    private CbsDirectoryCustomsBrokerEntity cbsCustomsBrokerEntity;

    @ApiModelProperty("ctb_service_company")
    @TableField(exist = false)
    private CtbDirectoryServiceCompanyEntity ctbServiceCompanyEntity;

    @ApiModelProperty("ctb_service_contract")
    @TableField(exist = false)
    private CtbServiceContractEntity ctbServiceContractEntity;
}
