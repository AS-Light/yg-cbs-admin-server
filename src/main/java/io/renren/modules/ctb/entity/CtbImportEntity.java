package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.bind.entity.BindCbsCtbImportEntity;
import io.renren.modules.ctb.vo.OrderProcessingTradeVo;
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;
import io.renren.modules.thr.entity.ThrCountryCodeEntity;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
import io.renren.modules.thr.entity.ThrDeclPortEntity;
import io.renren.modules.thr.entity.ThrPortEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 进口单（主）表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ctb_import")
public class CtbImportEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 委托公司
     */
    private Long fkServiceCompanyId;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 出发时间
     */
    private Date departTime;
    /**
     * 预计抵达时间
     */
    private Date expectedArrivalTime;
    /**
     * 抵达时间
     */
    private Date arrivalTime;
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
     * 入境口岸code（国内口岸）
     */
    private String toPortCode;
    /**
     * 经停港code
     */
    private String passPortCode;
    /**
     * 发票编号
     */
    private String invoiceCode;
    /**
     * 报关单号
     */
    private String entryBillCode;
    /**
     * 进口发票金额
     */
    private BigDecimal invoiceMoney;
    /**
     * 结算币制
     */
    private String currencyCode;
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
     * 保费币制code
     */
    private String premiumCurrencyCode;
    /**
     * 杂费
     */
    private BigDecimal incidentalMoney;
    /**
     * 杂费币制code
     */
    private String incidentalCurrencyCode;
    /**
     * 报关费
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
     * 负责人
     */
    private Long manager;
    /**
     * saas业务分离，关联ctb_company_id
     */
    private Long ctbTenantId;

    @ApiModelProperty("委托企业")
    @TableField(exist = false)
    private CtbDirectoryServiceCompanyEntity serviceCompanyEntity;

    @ApiModelProperty("船务公司")
    @TableField(exist = false)
    private List<CtbImportMemberEntity> memberEntityList;

    @ApiModelProperty("船务公司")
    @TableField(exist = false)
    private CtbDirectoryShipCompanyEntity shipCompanyEntity;

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

    @ApiModelProperty("进口结算币制实体")
    @TableField(exist = false)
    private ThrCurrencyEntity currencyEntity;

    @ApiModelProperty("运费币制实体")
    @TableField(exist = false)
    private ThrCurrencyEntity freightCurrency;

    @ApiModelProperty("保费币制实体")
    @TableField(exist = false)
    private ThrCurrencyEntity premiumCurrency;

    @ApiModelProperty("杂费币制实体")
    @TableField(exist = false)
    private ThrCurrencyEntity incidentalCurrency;

    @ApiModelProperty("进口合同图片表list")
    @TableField(exist = false)
    private List<CtbImgImportContractEntity> imgImportContractList;

    @ApiModelProperty("发票附件list")
    @TableField(exist = false)
    private List<CtbImgImportInvoiceEntity> imgImportInvoiceList;

    @ApiModelProperty("提单附件list")
    @TableField(exist = false)
    private List<CtbImgImportLadingBillEntity> imgImportLadingBillList;

    @ApiModelProperty("提货单附件list")
    @TableField(exist = false)
    private List<CtbImgImportDeliveryOrderEntity> imgImportDeliveryOrderList;

    @ApiModelProperty("其他附件list")
    @TableField(exist = false)
    private List<CtbImgImportOthersEntity> imgImportOthersList;

    @ApiModelProperty("进口报关单附件list")
    @TableField(exist = false)
    private List<CtbImgImportEntryBillEntity> imgImportEntryBillList;

    @ApiModelProperty("船务合同图片表list")
    @TableField(exist = false)
    private List<CtbImgImportContractShipEntity> imgImportContractShipList;

    @ApiModelProperty("箱单附件图片表list")
    @TableField(exist = false)
    private List<CtbImgImportPackingListEntity> imgImportPackingListList;

    @ApiModelProperty("首付款附件")
    @TableField(exist = false)
    private List<CtbImgImportPayInAdvanceEntity> imgImportPayInAdvanceList;

    @ApiModelProperty("尾款附件")
    @TableField(exist = false)
    private List<CtbImgImportPayTailEntity> imgImportPayTailList;

    @ApiModelProperty("授权书附件")
    @TableField(exist = false)
    private List<CtbImgImportPowerOfAttorneyEntity> imgImportPowerOfAttorneyList;

    @ApiModelProperty("进货商品表list - ctb_import_goods")
    @TableField(exist = false)
    private List<CtbImportGoodsEntity> importGoodsList;

    @ApiModelProperty("收费明细表list - ctb_import_price_item")
    @TableField(exist = false)
    private List<CtbImportPriceItemEntity> importPriceItemEntityList;

    @ApiModelProperty("航次变更列表")
    @TableField(exist = false)
    private List<CtbImportVoyageEntity> voyageList;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private OrderProcessingTradeVo searchForm;

    @ApiModelProperty("绑定Cbs")
    @TableField(exist = false)
    private BindCbsCtbImportEntity bindEntity;

    @ApiModelProperty("负责人实体")
    @TableField(exist = false)
    private OrgCtbUserEntity managerEntity;

    @ApiModelProperty("服务公司的名称")
    @TableField(exist = false)
    private String serviceCompanyName;
}
