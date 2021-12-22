package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 手册报核表头
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-09 11:57:02
 */
@Data
@TableName("cst_npts_dcr_header")
public class CstNptsDcrHeaderEntity implements Serializable {
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
     * 手册号
     */
    private String emlNo;
    /**
     * 经营企业社会信用代码
     */
    private String bizopEtpsSccd;
    /**
     * 经营企业编号
     */
    private String bizopEtpsNo;
    /**
     * 经营企业名称
     */
    private String bizopEtpsNm;
    /**
     * 加工企业社会信用代码
     */
    private String ownerEtpsSccd;
    /**
     * 加工企业编号
     */
    private String ownerEtpsNo;
    /**
     * 加工企业名称
     */
    private String ownerEtpsNm;
    /**
     * 进口清单总数
     */
    private Long impInvtTotalCnt;
    /**
     * 出口清单总数
     */
    private Long expInvtTotalCnt;
    /**
     * 报核料件总数
     */
    private Long chgoffMtpckTotalAmt;
    /**
     * 报核成品总数
     */
    private Long chgoffEndprdTotalAmt;
    /**
     * 报核申报日期 申报时需填写，例：20190101
     */
    private Date chgoffDclDate;
    /**
     * 申报来源标志代码 1-电子口岸申报
     */
    private String dclTypeMarkcd;
    /**
     * 申报企业社会信用代码
     */
    private String dclEtpsSccd;
    /**
     * 申报企业编号
     */
    private String dclEtpsNo;
    /**
     * 申报企业名称
     */
    private String dclEtpsNm;
    /**
     * 录入单位海关编码
     */
    private String inputEtpsNo;
    /**
     * 录入单位统一社会信用代码
     */
    private String inputEtpsSccd;
    /**
     * 录入单位名称
     */
    private String inputEtpsNm;
    /**
     * 录入时间 录入时须填写，例：20190101
     */
    private Date inputTime;
    /**
     * 备注
     */
    private String rmk;
    /**
     * 主管海关
     */
    private String masterCuscd;

}
