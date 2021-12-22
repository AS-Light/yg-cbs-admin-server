package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.cbs.vo.ContractVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-02-20 17:14:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cbs_purchase")
public class CbsPurchaseEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联合同cbs_contract
     */
    private Long fkContractId;
    /**
     * 发票金额
     */
    private BigDecimal invoiceMoney;
    /**
     * 发票编号
     */
    private String invoiceCode;
    /**
     * 关联仓库名录表（cbs_directory_store）ID
     */
    private Long fkStoreId;
    /**
     * 关联运输公司名录表（cbs_directory_transit_company）ID
     */
    private Long fkTransitCompanyId;
    /**
     * 运输车牌号
     */
    private String wagonNumber;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 出发时间
     */
    private Date departTime;
    /**
     * 抵达时间
     */
    private Date arrivalTime;
    /**
     * 删除说明
     */
    private String deleteNote;
    /**
     * 审核时间
     */
    private Date examineTime;
    /**
     * 删除时间
     */
    private Date deleteTime;
    /**
     * 操作人
     */
    private Long operator;
    /**
     * 提货地址
     */
    private String startAddress;
    /**
     * 提货地址-联系人
     */
    private String startContact;
    /**
     * 提货地址-联系人-电话
     */
    private String startPhone;
    /**
     * 收货地址
     */
    private String endAddress;
    /**
     * 收货地址-联系人
     */
    private String endContact;
    /**
     * 收货地址-联系人-电话
     */
    private String endPhone;
    /**
     * 国内运费
     */
    private BigDecimal transFeeMoney;
    /**
     * 是否同步创建入库单 转化Boolean 需要在使用数据库加入&tinyInt1isBit=false
     */
    private Boolean synStoreIn;
    /**
     * 1、编辑中 2、审核中 3、审核失败 4、审核通过 99、已删除
     */
    private Integer status;
    /**
     * saas业务分离，关联cbs_company_id
     */
    private Long tenantId;

    @ApiModelProperty("如果所有进口单物料与合同物料吻合（>=），用户依然要创建进口单")
    @TableField(exist = false)
    private Boolean forceToCreate;

    @ApiModelProperty("合同实体")
    @TableField(exist = false)
    private CbsContractEntity contractEntity;

    @ApiModelProperty("商品实体列表")
    @TableField(exist = false)
    private List<CbsPurchaseGoodsEntity> purchaseGoodsList;

    @ApiModelProperty("仓库名录表")
    @TableField(exist = false)
    private CbsDirectoryStoreEntity storeEntity;

    @ApiModelProperty("国内运输公司名录表")
    @TableField(exist = false)
    private CbsDirectoryTransitCompanyEntity transitCompanyEntity;

    @ApiModelProperty("发票附件list")
    @TableField(exist = false)
    private List<CbsImgPurchaseInvoiceEntity> imgPurchaseInvoiceList;

    @ApiModelProperty("cbs_img_purchase_delivery_order")
    @TableField(exist = false)
    private List<CbsImgPurchaseDeliveryOrderEntity> imgPurchaseDeliveryOrderList;

    @ApiModelProperty("cbs_img_purchase_receipt")
    @TableField(exist = false)
    private List<CbsImgPurchaseReceiptEntity> imgPurchaseReceiptList;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private ContractVo searchForm;

    @ApiModelProperty("store_goods_in_id")
    @TableField(exist = false)
    private Long storeGoodsInId;
    @ApiModelProperty("store_goods_in_status")
    @TableField(exist = false)
    private Integer storeGoodsInStatus;
}
