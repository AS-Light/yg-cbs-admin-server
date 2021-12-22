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
 * 商品出库表，
 * 继承表包含cbs_store_goods_out_produce、cbs_store_goods_out_export，
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
@TableName("cbs_store_goods_out")
@Alias("CbsStoreGoodsOutEntity")
public class CbsStoreGoodsOutEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 出库类型 1、生产出库 2、出口出库 3、国内发货出库
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
     * 1、编辑中 2、审核中 3、审核失败 4、审核通过 99、已删除
     */
    private Integer status;
    /**
     * 说明
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
     * 国内运输费用
     */
    private BigDecimal transFeeMoney;

    @ApiModelProperty("入参时方便获取出口单id")
    @TableField(exist = false)
    private Long fkExportId;

    @ApiModelProperty("入参时方便获取生产单id")
    @TableField(exist = false)
    private Long fkProduceId;

    @ApiModelProperty("入参时方便获取国内发货单id")
    @TableField(exist = false)
    private Long fkSellId;

    @ApiModelProperty("cbs_produce")
    @TableField(exist = false)
    private CbsProduceEntity produceEntity;

    @ApiModelProperty("cbs_export")
    @TableField(exist = false)
    private CbsExportEntity exportEntity;

    @ApiModelProperty("cbs_sell")
    @TableField(exist = false)
    private CbsSellEntity sellEntity;

    @ApiModelProperty("cbs_directory_store")
    @TableField(exist = false)
    private CbsDirectoryStoreEntity storeEntity;

    @ApiModelProperty("cbs_directory_transit_company")
    @TableField(exist = false)
    private CbsDirectoryTransitCompanyEntity transitCompanyEntity;

    @ApiModelProperty("cbs_store_goods_out_item")
    @TableField(exist = false)
    private List<CbsStoreGoodsOutItemEntity> storeGoodsOutItemEntityList;

    @ApiModelProperty("cbs_img_store_goods_out_delivery_order")
    @TableField(exist = false)
    private List<CbsImgStoreGoodsOutDeliveryOrderEntity> imgStoreGoodsOutDeliveryOrderEntityList;

    @ApiModelProperty("cbs_img_store_goods_out_receipt")
    @TableField(exist = false)
    private List<CbsImgStoreGoodsOutReceiptEntity> imgStoreGoodsOutReceiptEntityList;

    @ApiModelProperty("多个合同ID查询")
    @TableField(exist = false)
    private List<Object> fkContractIds;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private ContractVo searchForm;
}
