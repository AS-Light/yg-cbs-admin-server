package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 进口货物清单
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ctb_import_goods")
public class CtbImportGoodsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联ctb_import
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
    private CtbGoodsPartNoEntity partNoEntity;
}
