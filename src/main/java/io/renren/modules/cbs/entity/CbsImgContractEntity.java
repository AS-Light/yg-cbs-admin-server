package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同图片表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-15 16:19:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cbs_img_contract")
public class CbsImgContractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联合同单主表（cbs_contract）ID
     */
    private Long fkContractId;
    /**
     * 图片地址（后缀）
     */
    private String imgUrl;
    /**
     * 在同一（采购）合同中的排序顺序
     */
    private Integer sort;
    /**
     * 是否有效:0无效，1有效
     */
    private Integer isValid;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

    @ApiModelProperty("1、进口 2、出口 3、国内")
    @TableField(exist = false)
    private Integer type;

}
