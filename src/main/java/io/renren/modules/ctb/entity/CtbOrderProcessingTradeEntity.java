package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.bind.entity.BindCbsContractCtbProcessingTradeEntity;
import io.renren.modules.ctb.vo.OrderProcessingTradeVo;
import io.renren.modules.org_ctb.entity.OrgCtbUserEntity;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 报关行加贸（手册）业务表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_order_processing_trade")
@Alias(value = "CtbOrderProcessingTradeEntity")
public class CtbOrderProcessingTradeEntity extends BaseEntity implements Serializable {
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
     * 合同类型 1、进料加工合同 2、来料加工合同
     */
    private Integer type;
    /**
     * 合同标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 简介
     */
    private String introduction;
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
    /**
     * 状态 1、编辑中 2、审核中 3、审核失败 4、审核通过 99、中止（垃圾箱）11、完成提审 12、完成退审 13、已完成 -1、子加贸单随根加贸单状态，不可单独改变
     */
    private Integer status;
    /**
     * 预录入统一编号。
     * 当企业执行手册首次暂存操作时，无需填写，电子口岸统一返回。
     * 电子口岸返回预录入统一编号后，之后暂存或申报数据时必填。
     */
    private String seqNo;
    /**
     * 手册编号
     */
    private String emlNo;
    /**
     * 外键币种code，（ctb_currency）
     */
    private String currencyCode;
    /**
     * 负责人
     */
    private Long manager;
    /**
     * saas业务分离，关联cbs_company_id
     */
    private Long ctbTenantId;

    @ApiModelProperty("多个状态类型，入参")
    @TableField(exist = false)
    private List<Integer> statusList;

    @ApiModelProperty("关联方实体列表")
    @TableField(exist = false)
    private List<CtbOrderProcessingTradeMemberEntity> memberEntityList;

    @ApiModelProperty("币种实体")
    @TableField(exist = false)
    private ThrCurrencyEntity currencyEntity;

    @ApiModelProperty("加贸单商品表list")
    @TableField(exist = false)
    private List<CtbOrderProcessingTradeGoodsEntity> orderProcessingTradeGoodsList;

    @ApiModelProperty("加贸单合同图片表list")
    @TableField(exist = false)
    private List<CtbImgOrderProcessingTradeEntity> imgOrderProcessingTradeEntityList;

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
    @ApiModelProperty("加贸单子合同ID")
    @TableField(exist = false)
    private List<Object> subcontractIds;

    @ApiModelProperty("服务企业 ctb_directory_service_company")
    @TableField(exist = false)
    private CtbDirectoryServiceCompanyEntity serviceCompanyEntity;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private OrderProcessingTradeVo searchForm;

    @ApiModelProperty("绑定CBS")
    @TableField(exist = false)
    private BindCbsContractCtbProcessingTradeEntity bindEntity;

    @ApiModelProperty("收费明细表list - ctb_order_processing_trade_price_item")
    @TableField(exist = false)
    private List<CtbOrderProcessingTradePriceItemEntity> orderProcessingTradePriceItemEntityList;

    @ApiModelProperty("负责人实体")
    @TableField(exist = false)
    private OrgCtbUserEntity managerEntity;

    @ApiModelProperty("服务公司的名称")
    @TableField(exist = false)
    private String serviceCompanyName;
}
