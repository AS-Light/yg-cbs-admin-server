package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.bind.entity.BindCbsCtbGoodsPartNoEntity;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
import io.renren.modules.thr.entity.ThrHsUnitEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-07 18:33:29
 */
@Data
@TableName("cbs_goods_part_no")
public class CbsGoodsPartNoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;
    /**
     * 商品料号（即合同商品编号）
     */
    private Long goodsPartNo;
    /**
     * 关联商品名录表
     */
    private Long fkGoodsId;
    /**
     * 币制代码
     */
    private String currencyCode;
    /**
     * 结算单位
     */
    private String unitCode;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 合同商品数量
     */
    private BigDecimal contractCount;
    /**
     * 合同总价
     */
    private BigDecimal contractPrice;
    /**
     * 已进口商品数量
     */
    private BigDecimal importCount;
    /**
     * 已出口商品数量
     */
    private BigDecimal exportCount;
    /**
     * 计划使用和产出的商品数量
     */
    private BigDecimal planCount;
    /**
     * 已生产商品数量
     */
    private BigDecimal produceCount;
    /**
     * 库存商品数量
     */
    private BigDecimal storeCount;
    /**
     * 已入库商品数量
     */
    private BigDecimal storeInCount;
    /**
     * 已出库商品数量
     */
    private BigDecimal storeOutCount;

    @ApiModelProperty("库存商品实体列表")
    @TableField(exist = false)
    private List<CbsStoreGoodsEntity> storeGoodsList;

    @ApiModelProperty("商品实体")
    @TableField(exist = false)
    private CbsDirectoryGoodsEntity goodsEntity;

    @ApiModelProperty("单位实体")
    @TableField(exist = false)
    private ThrHsUnitEntity unitEntity;

    @ApiModelProperty("币制实体")
    @TableField(exist = false)
    private ThrCurrencyEntity currencyEntity;

    @ApiModelProperty("绑定Ctb")
    @TableField(exist = false)
    private BindCbsCtbGoodsPartNoEntity bindEntity;
}
