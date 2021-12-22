package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.cbs.entity.CbsImgContractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同图片表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_img_order_processing_trade")
public class CtbImgOrderProcessingTradeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public CtbImgOrderProcessingTradeEntity(CbsImgContractEntity cbsImgContractEntity, Integer type) {
        this.type = type;
        this.imgUrl = cbsImgContractEntity.getImgUrl();
    }

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联加贸单主表（ctb_order_processing_trade）ID
     */
    private Long fkOrderProcessingTradeId;
    /**
     * 1、进口合同 2、出口合同 3、国内采购合同
     */
    private Integer type;
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

}
