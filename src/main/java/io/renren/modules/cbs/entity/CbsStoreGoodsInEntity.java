package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.cbs.vo.ContractVo;
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
 * 商品入库表，
 * 继承表包含cbs_store_goods_in_import、cbs_store_goods_in_produce、cbs_store_goods_in_purchase_domestic，
 * 原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cbs_store_goods_in")
@Alias("CbsStoreGoodsInEntity")
public class CbsStoreGoodsInEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 入库类型 1、进口入库 2、生产入库 3、国内采购入库
     */
    private Integer type;
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
     * 0、默认 1、已审核 98、已锁定 99、已删除
     */
    private Integer status;
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
     * 是否提供提货码
     */
    private Boolean hasDeliveryCode;
    /**
     * 提货码
     */
    private String deliveryCode;

    /**
     * 国内运输费用
     */
    private BigDecimal transFeeMoney;

    private Long tenantId;

    @ApiModelProperty("仓库名录表")
    @TableField(exist = false)
    private CbsDirectoryStoreEntity storeEntity;

    @ApiModelProperty("国内运输公司名录表")
    @TableField(exist = false)
    private CbsDirectoryTransitCompanyEntity transitCompanyEntity;

    @ApiModelProperty("入参时方便获取进口单id")
    @TableField(exist = false)
    private Long fkImportId;

    @ApiModelProperty("入参时方便获取生产单id")
    @TableField(exist = false)
    private Long fkProduceId;

    @ApiModelProperty("入参时方便获取国内收货单id")
    @TableField(exist = false)
    private Long fkPurchaseId;

    @ApiModelProperty("入参时方便获取生产原料还库单id")
    @TableField(exist = false)
    private Long fkProduceBackId;

    @ApiModelProperty("进口单表")
    @TableField(exist = false)
    private CbsImportEntity importEntity;

    @ApiModelProperty("生产单表")
    @TableField(exist = false)
    private CbsProduceEntity produceEntity;

    @ApiModelProperty("国内收货单表")
    @TableField(exist = false)
    private CbsPurchaseEntity purchaseEntity;

    @ApiModelProperty("（原料还库）生产单表")
    @TableField(exist = false)
    private CbsProduceEntity produceBackEntity;


    @ApiModelProperty("合同")
    @TableField(exist = false)
    private CbsContractEntity contractEntity;

    @ApiModelProperty("商品入库商品表")
    @TableField(exist = false)
    private List<CbsStoreGoodsInItemEntity> storeGoodsInItemEntityList;

    @ApiModelProperty("商品出库表")
    @TableField(exist = false)
    private List<CbsStoreGoodsOutItemEntity> storeGoodsOutItemEntityList;

    @ApiModelProperty("cbs_img_store_goods_in_delivery_order")
    @TableField(exist = false)
    private List<CbsImgStoreGoodsInDeliveryOrderEntity> imgStoreGoodsInDeliveryOrderEntityList;
    @ApiModelProperty("cbs_img_store_goods_in_receipt")
    @TableField(exist = false)
    private List<CbsImgStoreGoodsInReceiptEntity> imgStoreGoodsInReceiptEntityList;

    @ApiModelProperty("多个合同ID查询")
    @TableField(exist = false)
    private List<Object> fkContractIds;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private ContractVo searchForm;
}
