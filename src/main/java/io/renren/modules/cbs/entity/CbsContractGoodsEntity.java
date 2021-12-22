package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 原材料名录表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 10:19:45
 */
@Data
@TableName("cbs_contract_goods")
public class CbsContractGoodsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 关联合同表
     */
    private Long fkContractId;
    /**
     * 商品在合同中序号
     */
    private Integer indexInContract;
    /**
     * 1、进 2、出
     */
    private Integer type;

    @ApiModelProperty("料号商品实体")
    @TableField(exist = false)
    private CbsGoodsPartNoEntity partNoEntity;
}
