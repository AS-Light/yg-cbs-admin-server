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
 * 原材进货记录
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-18 15:18:27
 */
@Data
@TableName("cbs_import_goods")
public class CbsImportGoodsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联cbs_import
     */
    private Long fkImportId;
    /**
     * 商品料号（即合同商品编号）
     */
    private Long goodsPartNo;
    /**
     * 商品数量，可能包含小数
     */
    private BigDecimal count;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

    @ApiModelProperty("料号实体")
    @TableField(exist = false)
    private CbsGoodsPartNoEntity partNoEntity;
}
