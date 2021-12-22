package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 报关行服务企业的合同附件表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-21 16:09:24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_img_service_contract")
public class CtbImgServiceContractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 合同（ctb_service_contract）ID
     */
    private Long fkServiceContractId;
    /**
     * 图片地址（后缀）
     */
    private String imgUrl;
    /**
     * 排序顺序
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

    @TableField(exist = false)
    private List<CtbImgServiceContractEntity> imgList;

}
