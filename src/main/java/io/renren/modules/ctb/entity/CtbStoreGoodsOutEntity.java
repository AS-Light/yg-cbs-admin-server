package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.ctb.vo.SearchStoreGoodsVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品出库表，
 * 继承表包含cbs_store_goods_out_produce、cbs_store_goods_out_export，
 * 原则上本表中一条数据必在其子表中存在一条数据，反之亦然
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@Data
@TableName("ctb_store_goods_out")
public class CtbStoreGoodsOutEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联服务公司表
     */
    private Long fkServiceCompanyId;
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
     * 1、编辑中 2、审核中 3、审核失败 4、审核通过 99、已删除
     */
    private Integer status;
    /**
     * saas业务分离，关联cbs_company_id
     */
    private Long ctbTenantId;

    @ApiModelProperty("ctb_directory_service_company")
    @TableField(exist = false)
    private CtbDirectoryServiceCompanyEntity serviceCompanyEntity;

    @ApiModelProperty("ctb_directory_transit_company")
    @TableField(exist = false)
    private CtbDirectoryTransitCompanyEntity transitCompanyEntity;

    @ApiModelProperty("ctb_store_goods_out_item")
    @TableField(exist = false)
    private List<CtbStoreGoodsOutItemEntity> storeGoodsOutItemEntityList;

    @ApiModelProperty("ctb_img_store_goods_out_delivery_order")
    @TableField(exist = false)
    private List<CtbImgStoreGoodsOutDeliveryOrderEntity> imgStoreGoodsOutDeliveryOrderEntityList;

    @ApiModelProperty("ctb_img_store_goods_out_receipt")
    @TableField(exist = false)
    private List<CtbImgStoreGoodsOutReceiptEntity> imgStoreGoodsOutReceiptEntityList;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private SearchStoreGoodsVo searchForm;
}
