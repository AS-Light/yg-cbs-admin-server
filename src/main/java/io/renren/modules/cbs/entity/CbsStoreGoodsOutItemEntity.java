package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 原料出库表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Data
@TableName("cbs_store_goods_out_item")
@Alias("CbsStoreGoodsOutItemEntity")
public class CbsStoreGoodsOutItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联产品出库单表（cbs_store_goods_out）ID
     */
    private Long fkStoreGoodsOutId;
    /**
     * 出库产品数量
     */
    private BigDecimal goodsOutCount;
    /**
     * 商品料号（即合同商品编号）
     */
    private Long goodsPartNo;
    /**
     * 出库源商品料号（即从该料号库存出库）
     */
    private Long outFromGoodsPartNo;
    /**
     * 创建时间
     */
    private Date createTime;

    @ApiModelProperty("料号实体")
    @TableField(exist = false)
    private CbsGoodsPartNoEntity partNoEntity;

}
