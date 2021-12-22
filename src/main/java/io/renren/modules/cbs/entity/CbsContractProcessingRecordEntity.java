package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * （合同）海关（手册）备案表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@Data
@TableName("cbs_contract_processing_record")
public class CbsContractProcessingRecordEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联合同单表（cbs_contract）ID
     */
    private Long fkContractId;
    /**
     * 海关备案编号
     */
    private String customsRecordNum;
    /**
     * 备案提交时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备案通过时间
     */
    private Date passTime;
    /**
     * 0：准备中（缺省） 1：审核中 2：已备案 99：备案失败
     */
    private Integer filingStatus;
    /**
     * 原因
     */
    private String reason;

    @ApiModelProperty("备案图片list")
    @TableField(exist = false)
    private List<CbsImgContractProcessingRecordEntity> imgContractProcessingRecordList;

}
