package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.thr.entity.ThrHsUnitEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 原材料名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 10:19:45
 */
@Data
@TableName("cbs_contract_goods")
public class CbsContractGoodsEntityBackUp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联合同表
     */
    private Long fkContractId;
    /**
     * 关联商品名录表
     */
    private Long fkGoodsId;
    /**
     * 商品数量，可能包含小数
     */
    private BigDecimal count;
    /**
     * 商品结算单位，可以从所有单位选择（可以和法定单位不同）
     */
    private String unitCode;
    /**
     * 商品单价，可能包含小数
     */
    private BigDecimal unitPrice;
    /**
     * 商品总价
     */
    private BigDecimal price;
    /**
     * 1、进 2、出
     */
    private Integer type;

    @ApiModelProperty("商品实体")
    @TableField(exist = false)
    private CbsDirectoryGoodsEntity goodsEntity;

    @ApiModelProperty("单位实体")
    @TableField(exist = false)
    private ThrHsUnitEntity unitEntity;
}
