package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.bind.entity.BindCbsContractCtbProcessingTradeEntity;
import io.renren.modules.cbs.vo.ContractVo;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cbs_contract")
@Alias(value = "CbsContractEntity")
public class CbsContractEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 外键币种Code，（thr_currency）
     */
    private String currencyCode;
    /**
     * 类型   0：缺省   1：进口合同包含进出两合同   2：出口合同包含国内采购合同 3：简单进口合同 4：简单出口合同 11、加工合同进口子合同 12、加工合同出口子合同 13、加工合同国内采购子合同
     */
    private Integer type;
    /**
     * 父合同ID，关联本表查询，树形结构
     */
    private Long parentId;
    /**
     * 合同单号，由创建时间、我方ID、对方ID等构成的固定位数16进制长编码（装逼用）
     */
    private String contractCode;
    /**
     * 合同标题
     */
    private String title;
    /**
     * 简介
     */
    private String introduction;

    /**
     * 合同额
     */
    private BigDecimal money;

    /**
     * 状态  1、编辑中 2、审核中 3、审核失败 4、审核通过 99、中止（垃圾箱）11、完成提审 12、完成退审 13、已完成 -1、子合同随根合同状态，不可单独改变
     */
    private Integer status;
    /**
     * 电子口岸返回预录入统一编号后，之后暂存或申报数据时必填
     */
    private String seqNo;
    /**
     * 手册编号
     */
    private String emlNo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 操作人
     */
    private Long operator;

    private Long tenantId;

    @ApiModelProperty("多个合同类型，入参")
    @TableField(exist = false)
    private List<Integer> typeList;

    @ApiModelProperty("多个状态类型，入参")
    @TableField(exist = false)
    private List<Integer> statusList;

    @ApiModelProperty("子合同")
    @TableField(exist = false)
    private List<CbsContractEntity> children;

    @ApiModelProperty("关联方实体列表")
    @TableField(exist = false)
    private List<CbsContractMemberEntity> memberEntityList;

    @ApiModelProperty("币种实体")
    @TableField(exist = false)
    private ThrCurrencyEntity currencyEntity;

    @ApiModelProperty("海关备案文件图片表list")
    @TableField(exist = false)
    private List<CbsImgContractProcessingRecordEntity> imgContractProcessingRecordList;

    @ApiModelProperty("合同商品表list")
    @TableField(exist = false)
    private List<CbsContractGoodsEntity> contractGoodsList;

    @ApiModelProperty("上传用 合同图片Url list")
    @TableField(exist = false)
    private List<String> imgContractList;

    @ApiModelProperty("合同图片表list")
    @TableField(exist = false)
    private List<CbsImgContractEntity> imgContractEntityList;

    @ApiModelProperty("cbs_import")
    @TableField(exist = false)
    private List<CbsImportEntity> importEntityList;

    @ApiModelProperty("cbs_export")
    @TableField(exist = false)
    private List<CbsExportEntity> exportEntityList;

    @ApiModelProperty("cbs_purchase")
    @TableField(exist = false)
    private List<CbsPurchaseEntity> purchaseEntityList;

    @ApiModelProperty("cbs_sell")
    @TableField(exist = false)
    private List<CbsSellEntity> sellEntityList;

    @ApiModelProperty("cbs_produce")
    @TableField(exist = false)
    private List<CbsProduceEntity> produceEntity;

    @ApiModelProperty("报关单号")
    @TableField(exist = false)
    private String billCode;

    @ApiModelProperty("商品码")
    @TableField(exist = false)
    private String goodsCode;

    @ApiModelProperty("HS码")
    @TableField(exist = false)
    private String hsCode;


    @ApiModelProperty("是否存在子合同0无1有")
    @TableField(exist = false)
    private Integer hasSubcontract;
    @ApiModelProperty("子合同ID")
    @TableField(exist = false)
    private List<Object> subcontractIds;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private ContractVo searchForm;

    @ApiModelProperty("绑定CTB")
    @TableField(exist = false)
    private BindCbsContractCtbProcessingTradeEntity bindEntity;
}
