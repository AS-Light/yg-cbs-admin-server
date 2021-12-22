package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 手册报核料件表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@Data
@TableName("cst_npts_dcr_img")
public class CstNptsDcrImgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 预录入统一编号
     */
    private String seqNo;
    /**
     * 序号 按照自然数排序，不可断号
     */
    private Long gNo;
    /**
     * 料件序号
     */
    private Long emlSeqno;
    /**
     * 商品料号
     */
    private String gdsMtno;
    /**
     * 商品编码
     */
    private String gdecd;
    /**
     * 商品名称
     */
    private String gdsnm;
    /**
     * 计量单位 根据商品编码，关联代码表
     */
    private String dclUnitcd;
    /**
     * 进口数量
     */
    private BigDecimal impTotalQty;
    /**
     * 深加工进口数量
     */
    private BigDecimal dprcsCrdImpQty;
    /**
     * 内销数量
     */
    private BigDecimal sdinQty;
    /**
     * 复出口数量
     */
    private BigDecimal reexpQty;
    /**
     * 料件销毁数量
     */
    private BigDecimal mtpckDstryQty;
    /**
     * 边角料数量
     */
    private BigDecimal lfmtQty;
    /**
     * 余料转出数量
     */
    private BigDecimal remainCrdwQty;
    /**
     * 产品总耗用量
     */
    private BigDecimal csmTotalQty;
    /**
     * 料件剩余数量
     */
    private BigDecimal remainMtpckQty;
    /**
     * 备注
     */
    private String rmk;

}
