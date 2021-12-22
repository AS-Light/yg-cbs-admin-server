package io.renren.modules.bind.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.cbs.entity.CbsGoodsPartNoEntity;
import io.renren.modules.ctb.entity.CtbGoodsPartNoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 绑定cbs和ctb料号
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-23 13:27:59
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("bind_cbs_ctb_goods_part_no")
public class BindCbsCtbGoodsPartNoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * cbs料号
     */
    private Long fkCbsGoodsPartNo;
    /**
     * ctb料号
     */
    private Long fkCtbGoodsPartNo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间（自动记录最后更新时间）
     */
    private Date updateTime;

    @ApiModelProperty("cbs_goods_part_no")
    @TableField(exist = false)
    private CbsGoodsPartNoEntity cbsGoodsPartNoEntity;

    @ApiModelProperty("ctb_goods_part_no")
    @TableField(exist = false)
    private CtbGoodsPartNoEntity ctbGoodsPartNoEntity;
}
