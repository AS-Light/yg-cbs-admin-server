package io.renren.common.jaxb.invt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 保税核注清单表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class InvtListType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;
    /**
     * 关联cst_invt_header
     */
    private Long fkInvtHeaderId;
    /**
     * 中心统一编号 首次暂存时系统生成并返填
     * 非首次操作须填写
     */
    private String seqNo;
    /**
     * 商品序号
     */
    private Long gdsSeqno;
    /**
     * 备案序号(对应底账序号）
     */
    private Long putrecSeqno;
    /**
     * 商品料号
     */
    private String gdsMtno;
    /**
     * 商品编码 可允许修改
     */
    private String gdecd;
    /**
     * 商品名称 可允许修改
     */
    private String gdsNm;
    /**
     * 商品规格型号 可允许修改
     */
    private String gdsSpcfModelDesc;
    /**
     * 申报计量单位
     */
    private String dclUnitcd;
    /**
     * 法定计量单位
     */
    private String lawfUnitcd;
    /**
     * 法定第二计量
     */
    private String secdLawfUnitcd;
    /**
     * 原产国(地区) 可允许修改
     */
    private String natcd;
    /**
     * 企业申报单价 可允许修改
     */
    private BigDecimal dclUprcAmt;
    /**
     * 企业申报总价 可允许修改
     */
    private BigDecimal dclTotalAmt;
    /**
     * 美元统计总金额
     */
    private BigDecimal usdStatTotalAmt;
    /**
     * 币制 可允许修改
     */
    private String dclCurrcd;
    /**
     * 法定数量
     */
    private BigDecimal lawfQty;
    /**
     * 第二法定数量 当法定第二计量单位为空时，该项为非必填
     * 可允许修改
     */
    private BigDecimal secdLawfQty;
    /**
     * 重量比例因子
     */
    private BigDecimal wtSfVal;
    /**
     * 第一比例因子
     */
    private BigDecimal fstSfVal;
    /**
     * 第二比例因子
     */
    private BigDecimal secdSfVal;
    /**
     * 申报数量* 可允许修改
     */
    private BigDecimal dclQty;
    /**
     * 毛重
     */
    private BigDecimal grossWt;
    /**
     * 净重
     */
    private BigDecimal netWt;
    /**
     * 用途代码* 取消该字段使用；不需要填写
     */
    private String useCd;
    /**
     * 征免方式 可允许修改
     */
    private String lvyrlfModecd;
    /**
     * 单耗版本号 账册由开关控制是否必填。需看单耗该字段如何定义
     * 可允许修改
     */
    private String ucnsVerno;
    /**
     * 报关单商品序号 企业可录入，如果企业不录入，系统自动返填
     */
    private Long entryGdsSeqno;
    /**
     * 归类标志
     */
    private String clyMarkcd;
    /**
     * 流转申报表序号 流转类专用。用于建立清单商品与流转申请表商品之间的关系
     */
    private Long applyTbSeqno;
    /**
     * 最终目的国 可允许修改
     */
    private String destinationNatcd;
    /**
     * 修改标志 0-未修改 1-修改 2-删除 3-增加
     */
    private String modfMarkcd;
    /**
     * 备注
     */
    private String rmk;

}
