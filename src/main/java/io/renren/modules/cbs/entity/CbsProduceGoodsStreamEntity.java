package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.org_cbs.entity.OrgCbsUserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 生产流水
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-26 16:15:37
 */
@Data
@TableName("cbs_produce_goods_stream")
@Alias("CbsProduceGoodsStreamEntity")
public class CbsProduceGoodsStreamEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 关联生产的商品缓存（cbs_produce_goods）ID
     */
    private Long fkProduceId;
    /**
     * 产出结果时间（上报时间）
     */
    private Date reportTime;
    /**
     * 操作人
     */
    private Long operator;
    /**
     * 状态 0：未审核 1：已审核
     */
    private Integer status;

    @ApiModelProperty("操作人实体")
    @TableField(exist = false)
    private OrgCbsUserEntity operatorUser;

    @ApiModelProperty("生产过程流商品条目")
    @TableField(exist = false)
    private List<CbsProduceGoodsStreamItemEntity> itemEntityList;

    @ApiModelProperty("（明知有问题的情况下）强制变更状态")
    @TableField(exist = false)
    private Boolean forceToChange;
}
