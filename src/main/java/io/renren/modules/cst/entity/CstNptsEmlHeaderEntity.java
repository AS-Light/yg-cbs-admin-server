package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.renren.common.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
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
@AllArgsConstructor
@NoArgsConstructor
@TableName("cst_npts_eml_header")
public class CstNptsEmlHeaderEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联合同表
     */
    private Long fkContractId;
    /**
     * 预录入统一编号。
     * 当企业执行手册首次暂存操作时，无需填写，电子口岸统一返回。
     * 电子口岸返回预录入统一编号后，之后暂存或申报数据时必填。
     */
    private String seqNo;
    /**
     * 手册编号
     */
    private String emlNo;
    /**
     * 手册类型代码 B-来料加工 C-进料加工
     */
    private String emlType;
    /**
     * 企业预录入编号
     */
    private String etpsPreentNo;
    /**
     * 申报类型代码 系统自动生成，1-备案申请  2-变更申请
     */
    private String dclTypecd;
    /**
     * 主管关区代码
     */
    private String masterCuscd;
    /**
     * 经营企业社会信用代码
     */
    private String bizopEtpsSccd;
    /**
     * 经营企业编号
     */
    private String bizopEtpsno;
    /**
     * 经营企业名称
     */
    private String bizopEtpsNm;
    /**
     * 收发货企业社会信用代码
     */
    private String rvsngdEtpsSccd;
    /**
     * 收货企业编号
     */
    private String rcvgdEtpsno;
    /**
     * 收货企业名称
     */
    private String rcvgdEtpsNm;
    /**
     * 收货企业地区代码
     */
    private String rcvgdEtpsDtcd;
    /**
     * 申报企业类型代码
     */
    private String dclEtpsTypecd;
    /**
     * 申报企业社会信用代码
     */
    private String dclEtpsSccd;
    /**
     * 申报企业编号 申报时须填写
     */
    private String dclEtpsno;
    /**
     * 申报企业名称
     */
    private String dclEtpsNm;
    /**
     * 申报时间 申报时须填写例：20190101
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd")
    private Date dclTime;
    /**
     * 监管方式代码
     */
    private String supvModecd;
    /**
     * 业务批准证编号
     */
    private String apcretNo;
    /**
     * 进口合同号
     */
    private String impCtrtNo;
    /**
     * 出口合同号
     */
    private String expCtrtNo;
    /**
     * 有效期
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd")
    private Date validDate;
    /**
     * 征免性质代码
     */
    private String reduNatrcd;
    /**
     * 加工种类代码
     */
    private String produceTypecd;
    /**
     * 进出口岸代码
     */
    private String impexpPortcd;
    /**
     * 单耗申报环节代码 1-出口前 2-报核前，系统默认为1-出口前
     */
    private String ucnsDclSegcd;
    /**
     * 手册用途 1-保税加工 2-特殊行业 3-保税维修 4-保税研发 9-其他
     */
    private String stndbkBankcd;
    /**
     * 加工生产能力
     */
    private BigDecimal productRatio;
    /**
     * 进口币制代码
     */
    private String impCurrcd;
    /**
     * 出口币制代码
     */
    private String expCurrcd;
    /**
     * 申报来源标志代码 1-电子口岸申报
     */
    private String dclTypeMarkcd;
    /**
     * 0-未开始执行  1-正常执行  2-恢复执行  3-台帐未开出 4-台帐未登记 5-暂停变更6-暂停进出口 7-暂停进口 8-暂停出口 9-全部暂停 A-申请注销
     */
    private String pauseImpexpMarkcd;
    /**
     * 录入单位名称 暂存时须填写
     */
    private String inputEtpsNm;
    /**
     * 录入单位统一社会信用代码
     */
    private String inputEtpsSccd;
    /**
     * 录入单位代码
     */
    private String inputEtpsTypecd;
    /**
     * 备注
     */
    private String rmk;
    /**
     * 录入时间 暂存时须填写例：20190101
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd")
    private Date inputTime;
    /**
     * 联系人
     */
    private String linkMan;
    /**
     * 联系人电话
     */
    private String linkManTel;

}
