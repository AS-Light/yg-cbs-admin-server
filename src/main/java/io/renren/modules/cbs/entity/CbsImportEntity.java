package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.bind.entity.BindCbsCtbImportEntity;
import io.renren.modules.cbs.vo.ContractVo;
import io.renren.modules.thr.entity.ThrCountryCodeEntity;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
import io.renren.modules.thr.entity.ThrDeclPortEntity;
import io.renren.modules.thr.entity.ThrPortEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 进口单（主）表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cbs_import")
@Alias("CbsImportEntity")
public class CbsImportEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 关联合同单表（cbs_contract）ID
     */
    private Long fkContractId;
    /**
     * 报关行名录表cbs_directory_customs_broker
     */
    private Long fkDirectoryCustomsBrokerId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 出发时间
     */
    private Date departTime;
    /**
     * 启运国code
     */
    private String fromCountryCode;
    /**
     * 启运港code
     */
    private String fromPortCode;
    /**
     * 贸易国code
     */
    private String toCountryCode;
    /**
     * 入境口岸code
     */
    private String toPortCode;
    /**
     * 经停港code
     */
    private String passPortCode;
    /**
     * 预计抵达时间
     */
    private Date expectedArrivalTime;
    /**
     * 抵达时间
     */
    private Date arrivalTime;
    /**
     * 报关单号
     */
    private String entryBillCode;
    /**
     * 发票编号
     */
    private String invoiceCode;
    /**
     * 进口发票金额
     */
    private BigDecimal invoiceMoney;
    /**
     * 运费
     */
    private BigDecimal freightMoney;
    /**
     * 运费币制code
     */
    private String freightCurrencyCode;
    /**
     * 保费
     */
    private BigDecimal premiumMoney;
    /**
     * 保费币制
     */
    private String premiumCurrencyCode;
    /**
     * 杂费
     */
    private BigDecimal incidentalMoney;
    /**
     * 杂费币制
     */
    private String incidentalCurrencyCode;
    /**
     * 报关费用
     */
    private BigDecimal customsFeeMoney;
    /**
     * 有无提货码，有则无需判断提货单
     */
    private Boolean hasDeliveryCode;
    /**
     * 提货码
     */
    private String deliveryCode;
    /**
     * 铅封号
     */
    private String sealNo;
    /**
     * 报关时间
     */
    private Date entryBillCreateTime;
    /**
     * 报关通过时间
     */
    private Date entryBillPassTime;
    /**
     * 1：编辑中 2：审核中 3：审核失败 4：审核通过（编辑报关） 11：报关提审 12：报关审核失败 13：报关审核通过 14：已完关（已入库-关联入库单） 99：中止
     */
    private Integer status;
    /**
     * 操作人
     */
    private Long operator;
    /**
     * saas业务分离，关联cbs_company_id
     */
    private Long tenantId;

    @ApiModelProperty("如果所有进口单物料与合同物料吻合（>=），用户依然要创建进口单")
    @TableField(exist = false)
    private Boolean forceToCreate;

    @ApiModelProperty("合同")
    @TableField(exist = false)
    private CbsContractEntity contractEntity;

    @ApiModelProperty("船务公司")
    @TableField(exist = false)
    private CbsDirectoryShipCompanyEntity shipCompanyEntity;

    @ApiModelProperty("启运国实体")
    @TableField(exist = false)
    private ThrCountryCodeEntity fromCountry;

    @ApiModelProperty("启运港实体")
    @TableField(exist = false)
    private ThrPortEntity fromPort;

    @ApiModelProperty("贸易国实体")
    @TableField(exist = false)
    private ThrCountryCodeEntity toCountry;

    @ApiModelProperty("入境口岸实体")
    @TableField(exist = false)
    private ThrDeclPortEntity toPort;

    @ApiModelProperty("经停港实体")
    @TableField(exist = false)
    private ThrPortEntity passPort;

    @ApiModelProperty("运费币制实体")
    @TableField(exist = false)
    private ThrCurrencyEntity freightCurrency;

    @ApiModelProperty("保费币制实体")
    @TableField(exist = false)
    private ThrCurrencyEntity premiumCurrency;

    @ApiModelProperty("杂费币制实体")
    @TableField(exist = false)
    private ThrCurrencyEntity incidentalCurrency;

    @ApiModelProperty("报关行")
    @TableField(exist = false)
    private CbsDirectoryCustomsBrokerEntity customsBrokerEntity;

    @ApiModelProperty("发票附件list")
    @TableField(exist = false)
    private List<CbsImgImportInvoiceEntity> imgImportInvoiceList;

    @ApiModelProperty("提单附件list")
    @TableField(exist = false)
    private List<CbsImgImportLadingBillEntity> imgImportLadingBillList;

    @ApiModelProperty("提货单附件list")
    @TableField(exist = false)
    private List<CbsImgImportDeliveryOrderEntity> imgImportDeliveryOrderList;

    @ApiModelProperty("其他附件list")
    @TableField(exist = false)
    private List<CbsImgImportOthersEntity> imgImportOthersList;

    @ApiModelProperty("进口报关单附件list")
    @TableField(exist = false)
    private List<CbsImgImportEntryBillEntity> imgImportEntryBillList;

    @ApiModelProperty("船务合同图片表list")
    @TableField(exist = false)
    private List<CbsImgImportContractShipEntity> imgImportContractShipList;

    @ApiModelProperty("箱单附件图片表list")
    @TableField(exist = false)
    private List<CbsImgImportPackingListEntity> imgImportPackingListList;

    @ApiModelProperty("首付款附件")
    @TableField(exist = false)
    private List<CbsImgImportPayInAdvanceEntity> imgImportPayInAdvanceList;

    @ApiModelProperty("尾款附件")
    @TableField(exist = false)
    private List<CbsImgImportPayTailEntity> imgImportPayTailList;

    @ApiModelProperty("授权协议附件")
    @TableField(exist = false)
    private List<CbsImgImportLicenseAgreementEntity> imgImportLicenseAgreementList;

    @ApiModelProperty("授权书附件")
    @TableField(exist = false)
    private List<CbsImgImportPowerOfAttorneyEntity> imgImportPowerOfAttorneyList;

    @ApiModelProperty("进货商品表list - cbs_import_goods")
    @TableField(exist = false)
    private List<CbsImportGoodsEntity> importGoodsList;

    @ApiModelProperty("航次变更列表")
    @TableField(exist = false)
    private List<CbsImportVoyageEntity> voyageList;

    @ApiModelProperty("多个合同ID查询")
    @TableField(exist = false)
    private List<Object> fkContractIds;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private ContractVo searchForm;

    @ApiModelProperty("store_goods_in_id")
    @TableField(exist = false)
    private Long storeGoodsInId;
    @ApiModelProperty("store_goods_in_status")
    @TableField(exist = false)
    private Integer storeGoodsInStatus;

    @ApiModelProperty("绑定Ctb")
    @TableField(exist = false)
    private BindCbsCtbImportEntity bindEntity;

    @ApiModelProperty("绑定Ctb的ID")
    @TableField(exist = false)
    private Long fkCtbCompanyId;
}
