package io.renren.common.jaxb.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

/**
 * 加工贸易手册原料表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-13 19:21:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class NptsEmlImg {
    /**
     * 预录入统一编号。当企业执行手册首次暂存操作时，无需填写，电子口岸统一返回。
     * 电子口岸返回预录入统一编号后，之后暂存或申报数据时必填。
     */
    @XmlElement(name = "SeqNo")
    private String seqNo;
    /**
     * 商品序号
     */
    @XmlElement(name = "GdsSeqno")
    private Long gdsSeqno;
    /**
     * 料件成品类型代码 I-料件 E-成品
     */
    @XmlElement(name = "MtpckEndprdTypecd")
    private String mtpckEndprdTypecd;
    /**
     * 商品料号
     */
    @XmlElement(name = "GdsMtno")
    private String gdsMtno;
    /**
     * 商品编码
     */
    @XmlElement(name = "Gdecd")
    private String gdecd;
    /**
     * 商品名称
     */
    @XmlElement(name = "GdsNm")
    private String gdsNm;
    /**
     * 规格型号描述 规范申报
     */
    @XmlElement(name = "EndprdGdsSpcfModelDesc")
    private String endprdGdsSpcfModelDesc;
    /**
     * 申报计量单位代码 关联代码表
     */
    @XmlElement(name = "DclUnitcd")
    private String dclUnitcd;
    /**
     * 法定计量单位代码 根据商品编码，关联代码表
     */
    @XmlElement(name = "LawfUnitcd")
    private String lawfUnitcd;
    /**
     * 法定第二计量单位代码
     */
    @XmlElement(name = "SecdLawfUnitcd")
    private String secdLawfUnitcd;
    /**
     * 申报数量
     */
    @XmlElement(name = "DclQty")
    private BigDecimal dclQty;
    /**
     * 申报单价
     */
    @XmlElement(name = "DclUprcAmt")
    private BigDecimal dclUprcAmt;
    /**
     * 申报总价
     */
    @XmlElement(name = "DclTotalAmt")
    private BigDecimal dclTotalAmt;
    /**
     * 申报币制代码 关联代码表
     */
    @XmlElement(name = "DclCurrcd")
    private String dclCurrcd;
    /**
     * 产销国(地区） 关联代码表
     */
    @XmlElement(name = "Natcd")
    private String natcd;
    /**
     * 征免方式 关联代码表
     */
    @XmlElement(name = "LvyrlfModecd")
    private String lvyrlfModecd;
    /**
     * 主辅料标记代码 1-主料 2-辅料，仅针对料件
     */
    @XmlElement(name = "AdjmtrMarkcd")
    private String adjmtrMarkcd;
    /**
     * 商品属性 预留，包括：１－消耗性物料；２－深加工结转；３-区域实质加工；8-现场自定义、 9-其他录入和逻辑
     */
    @XmlElement(name = "GdsAtrcd")
    private String gdsAtrcd;
    /**
     * 修改标志代码 0-未修改 1-修改 2-删除 3-增加
     */
    @XmlElement(name = "ModfMarkcd")
    private String modfMarkcd;
    /**
     * 备注
     */
    @XmlElement(name = "Rmk")
    private String rmk;

}
