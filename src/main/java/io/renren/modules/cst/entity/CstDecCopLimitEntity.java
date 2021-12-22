package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 进出口报关单 企业资质信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Data
@TableName("cst_dec_cop_limit")
public class CstDecCopLimitEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 企业资质编号 根据HS编码设限情况确定
     */
    private String entQualifNo;
    /**
     * 企业资质类别代码 根据HS编码设限情况确定
     */
    private String entQualifTypeCode;

}
