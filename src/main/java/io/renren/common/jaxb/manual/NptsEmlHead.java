package io.renren.common.jaxb.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 海关加工贸易手册备案表
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
public class NptsEmlHead {

    /**
     * 预录入统一编号。
     * 当企业执行手册首次暂存操作时，无需填写，电子口岸统一返回。
     * 电子口岸返回预录入统一编号后，之后暂存或申报数据时必填。
     */
    @XmlElement(name = "SeqNo")
    private String seqNo;
    /**
     * 手册编号
     */
    @XmlElement(name = "EmlNo")
    private String emlNo;
    /**
     * 手册类型代码 B-来料加工 C-进料加工
     */
    @XmlElement(name = "EmlType")
    private String emlType;
    /**
     * 企业预录入编号
     */
    @XmlElement(name = "EtpsPreentNo")
    private String etpsPreentNo;
    /**
     * 申报类型代码 系统自动生成，1-备案申请  2-变更申请
     */
    @XmlElement(name = "DclTypecd")
    private String dclTypecd;
    /**
     * 主管关区代码
     */
    @XmlElement(name = "MasterCuscd")
    private String masterCuscd;
    /**
     * 经营企业社会信用代码
     */
    @XmlElement(name = "BizopEtpsSccd")
    private String bizopEtpsSccd;
    /**
     * 经营企业编号
     */
    @XmlElement(name = "BizopEtpsno")
    private String bizopEtpsno;
    /**
     * 经营企业名称
     */
    @XmlElement(name = "BizopEtpsNm")
    private String bizopEtpsNm;
    /**
     * 收发货企业社会信用代码
     */
    @XmlElement(name = "RvsngdEtpsSccd")
    private String rvsngdEtpsSccd;
    /**
     * 收货企业编号
     */
    @XmlElement(name = "RcvgdEtpsno")
    private String rcvgdEtpsno;
    /**
     * 收货企业名称
     */
    @XmlElement(name = "RcvgdEtpsNm")
    private String rcvgdEtpsNm;
    /**
     * 收货企业地区代码
     */
    @XmlElement(name = "RcvgdEtpsDtcd")
    private String rcvgdEtpsDtcd;
    /**
     * 申报企业类型代码
     */
    @XmlElement(name = "DclEtpsTypecd")
    private String dclEtpsTypecd;
    /**
     * 申报企业社会信用代码
     */
    @XmlElement(name = "DclEtpsSccd")
    private String dclEtpsSccd;
    /**
     * 申报企业编号 申报时须填写
     */
    @XmlElement(name = "DclEtpsno")
    private String dclEtpsno;
    /**
     * 申报企业名称
     */
    @XmlElement(name = "DclEtpsNm")
    private String dclEtpsNm;
    /**
     * 申报时间 申报时须填写例：20190101
     */
    private Date dclTime;
    @XmlElement(name = "DclTime")
    private String dclTimeString;
    /**
     * 监管方式代码
     */
    @XmlElement(name = "SupvModecd")
    private String supvModecd;
    /**
     * 业务批准证编号
     */
    @XmlElement(name = "ApcretNo")
    private String ApcretNo;
    /**
     * 进口合同号
     */
    @XmlElement(name = "ImpCtrtNo")
    private String impCtrtNo;
    /**
     * 出口合同号
     */
    @XmlElement(name = "ExpCtrtNo")
    private String expCtrtNo;
    /**
     * 有效期
     */
    private Date validDate;
    @XmlElement(name = "ValidDate")
    private String validDateString;
    /**
     * 征免性质代码
     */
    @XmlElement(name = "ReduNatrcd")
    private String reduNatrcd;
    /**
     * 加工种类代码
     */
    @XmlElement(name = "ProduceTypecd")
    private String produceTypecd;
    /**
     * 进出口岸代码
     */
    @XmlElement(name = "ImpexpPortcd")
    private String impexpPortcd;
    /**
     * 单耗申报环节代码 1-出口前 2-报核前，系统默认为1-出口前
     */
    @XmlElement(name = "UcnsDclSegcd")
    private String ucnsDclSegcd;
    /**
     * 手册用途 1-保税加工 2-特殊行业 3-保税维修 4-保税研发 9-其他
     */
    @XmlElement(name = "StndbkBankcd")
    private String stndbkBankcd;
    /**
     * 加工生产能力
     */
    @XmlElement(name = "ProductRatio")
    private BigDecimal productRatio;
    /**
     * 进口币制代码
     */
    @XmlElement(name = "ImpCurrcd")
    private String impCurrcd;
    /**
     * 出口币制代码
     */
    @XmlElement(name = "ExpCurrcd")
    private String expCurrcd;
    /**
     * 申报来源标志代码 1-电子口岸申报
     */
    @XmlElement(name = "DclTypeMarkcd")
    private String dclTypeMarkcd = "1";
    /**
     * 0-未开始执行  1-正常执行  2-恢复执行  3-台帐未开出 4-台帐未登记 5-暂停变更6-暂停进出口 7-暂停进口 8-暂停出口 9-全部暂停 A-申请注销
     */
    @XmlElement(name = "PauseImpexpMarkcd")
    private String pauseImpexpMarkcd;
    /**
     * 录入单位名称 暂存时须填写
     */
    @XmlElement(name = "InputEtpsNm")
    private String inputEtpsNm;
    /**
     * 录入单位统一社会信用代码
     */
    @XmlElement(name = "InputEtpsSccd")
    private String inputEtpsSccd;
    /**
     * 录入单位代码
     */
    @XmlElement(name = "InputEtpsTypecd")
    private String inputEtpsTypecd;
    /**
     * 备注
     */
    @XmlElement(name = "Rmk")
    private String rmk;
    /**
     * 录入时间 暂存时须填写例：20190101
     */
    private Date inputTime;
    @XmlElement(name = "InputTime")
    private String InputTimeString;
    /**
     * 联系人
     */
    @XmlElement(name = "LinkMan")
    private String linkMan;
    /**
     * 联系人电话
     */
    @XmlElement(name = "LinkManTel")
    private String linkManTel;

}
