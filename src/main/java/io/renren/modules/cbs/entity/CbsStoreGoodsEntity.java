package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品库存表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Data
@TableName("cbs_store_goods")
public class CbsStoreGoodsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联仓库名录表（cbs_directory_store）ID
     */
    private Long fkStoreId;
    /**
     * 商品料号（即合同商品编号）
     */
    private Long goodsPartNo;
    /**
     * 库存商品数量
     */
    private BigDecimal goodsStoreCount;
    /**
     * 最后更新时间
     */
    private Date updateTime;

    @ApiModelProperty("仓库实体")
    @TableField(exist = false)
    private CbsDirectoryStoreEntity storeEntity;

    @ApiModelProperty("料号实体")
    @TableField(exist = false)
    private CbsGoodsPartNoEntity partNoEntity;
}
