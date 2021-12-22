package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 手册报核成品表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@Data
@TableName("cst_npts_dcr_exg")
public class CstNptsDcrExgEntity implements Serializable {
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
     * 序号 按照自然数排序
     */
    private Long gNo;
    /**
     * 成品序号
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
     * 出口数量
     */
    private BigDecimal expTotalQty;
    /**
     * 深加工出口数量
     */
    private BigDecimal dprcsCrdExpQty;
    /**
     * 成品退换进口数量
     */
    private BigDecimal endprdRetnExchImpQty;
    /**
     * 成品退换出口数量
     */
    private BigDecimal endprdRetnExchExpQty;
    /**
     * 备注
     */
    private String rmk;

}
