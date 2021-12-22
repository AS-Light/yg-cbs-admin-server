package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 原料出库表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-06-03 14:57:15
 */
@Data
@TableName("ctb_store_goods_out_item")
public class CtbStoreGoodsOutItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联产品出库单表（cbs_store_product_out）ID
     */
    private Long fkStoreGoodsOutId;
    /**
     * 商品
     */
    private Long fkGoodsId;
    /**
     * 出库源库存号（即从该库存出库）
     */
    private Long fkOutFromStoreGoodsId;
    /**
     * 出库产品数量
     */
    private BigDecimal goodsOutCount;
    /**
     * 创建时间
     */
    private Date createTime;

    @ApiModelProperty("商品实体")
    @TableField(exist = false)
    private CtbDirectoryGoodsEntity goodsEntity;

    @ApiModelProperty("出库源库存实体")
    @TableField(exist = false)
    private CtbStoreGoodsEntity outFromStoreGoodsEntity;
}
