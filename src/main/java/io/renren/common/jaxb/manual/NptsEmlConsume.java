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
 * 加工贸易手册单耗表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-14 10:46:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class NptsEmlConsume {

    /**
     * 预录入统一编号 当企业执行手册首次暂存操作时，无需填写，电子口岸统一返回。
     * 电子口岸返回预录入统一编号后，之后暂存或申报数据时必填。
     */
    @XmlElement(name = "SeqNo")
    private String seqNo;
    /**
     * 成品序号
     */
    @XmlElement(name = "EndprdSeqno")
    private String endprdSeqno;
    /**
     * 成品料号
     */
    @XmlElement(name = "EndprdGdsMtno")
    private String endprdGdsMtno;
    /**
     * 料件序号
     */
    @XmlElement(name = "MtpckSeqno")
    private String mtpckSeqno;
    /**
     * 料件料号
     */
    @XmlElement(name = "MtpckGdsMtno")
    private String mtpckGdsMtno;
    /**
     * 单耗版本号 默认为0，不区分版本
     */
    @XmlElement(name = "UcnsVerno")
    private String ucnsVerno;
    /**
     * 单耗数量(未启用)
     */
    @XmlElement(name = "UcnsQty")
    private BigDecimal ucnsQty;
    /**
     * 净耗数量
     */
    @XmlElement(name = "NetUseupQty")
    private BigDecimal netUseupQty;
    /**
     * 有形损耗率
     */
    @XmlElement(name = "TgblLossRate")
    private BigDecimal tgblLossRate;
    /**
     * 无形损耗率
     */
    @XmlElement(name = "IntgbLossRate")
    private BigDecimal intgbLossRate;
    /**
     * 单耗申报状态代码 1-未申报 2-已申报 3-已确定，默认为2-已申报
     */
    @XmlElement(name = "UcnsDclStucd")
    private String ucnsDclStucd;
    /**
     * 修改标记代码 0-未修改 1-修改 2-删除 3-增加
     */
    @XmlElement(name = "ModfMarkcd")
    private String modfMarkcd;
    /**
     * 保税料件比例
     */
    @XmlElement(name = "BondMtpckPrpr")
    private BigDecimal bondMtpckPrpr;
    /**
     * 1-运行 2-停用，默认为1，企业申报
     */
    @XmlElement(name = "EtpsExeMarkcd")
    private String etpsExeMarkcd;
    /**
     * 备注
     */
    @XmlElement(name = "Rmk")
    private String rmk;
    /**
     * 序号
     */
    @XmlElement(name = "GseqNo")
    private Long gseqNo;
    /**
     * 成品商品编码
     */
    @XmlElement(name = "EndprdGdecd")
    private String endprdGdecd;
    /**
     * 成品商品名称
     */
    @XmlElement(name = "EndprdGdsNm")
    private String endprdGdsNm;
    /**
     * 料件商品编码
     */
    @XmlElement(name = "MtpckGdecd")
    private String mtpckGdecd;
    /**
     * 料件商品名称
     */
    @XmlElement(name = "MtpckGdsNm")
    private String mtpckGdsNm;

}
