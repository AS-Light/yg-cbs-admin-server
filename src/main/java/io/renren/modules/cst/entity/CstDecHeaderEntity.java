package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 报关单表头
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cst_dec_header")
public class CstDecHeaderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联进出口单（cbs_import，cbs_export）id
     */
    private Long fkOrderId;
    /**
     * 申报单位代码
     */
    private String agentCode;
    /**
     * 申报单位名称
     */
    private String agentName;
    /**
     * 批准文号 实填“外汇核销单号”
     */
    private String apprNo;
    /**
     * 提单号
     */
    private String billNo;
    /**
     * 合同号
     */
    private String contrNo;
    /**
     * 录入单位代码
     */
    private String copCode;
    /**
     * 录入单位名称
     */
    private String copName;
    /**
     * 主管海关（申报地海关）
     */
    private String customMaster;
    /**
     * 征免性质
     */
    private String cutMode;
    /**
     * 数据来源 空值，预留字段
     */
    private String dataSource;
    /**
     * 报关/转关关系标志
     */
    private String declTrnRel;
    /**
     * 经停港/指运港
     */
    private String distinatePort;
    /**
     * 报关标志 1：普通报关
     * 3：北方转关提前
     * 5：南方转关提前
     * 6：普通报关，运输工具名称以‘◎’开头，南方H2000直转
     */
    private String ediId;
    /**
     * 海关编号
     */
    private String entryId;
    /**
     * 报关单类型
     */
    private String entryType;
    /**
     * 运费币制
     */
    private String feeCurr;
    /**
     * 运费标记
     */
    private String feeMark;
    /**
     * 运费／率
     */
    private BigDecimal feeRate;
    /**
     * 毛重
     */
    private BigDecimal grossWet;
    /**
     * 进出口日期
     */
    @JsonProperty("iEDate")
    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd")
    private Date iEDate;
    /**
     * 进出口标志
     */
    @JsonProperty("iEFlag")
    private String iEFlag;
    /**
     * 进出口岸 需要代码转换
     */
    @JsonProperty("iEPort")
    private String iEPort;
    /**
     * 录入员名称
     */
    private String inputerName;
    /**
     * 保险费币制
     */
    private String insurCurr;
    /**
     * 保险费标记
     */
    private String insurMark;
    /**
     * 保险费／率 海关精度为Z(12).Z(7)
     */
    private BigDecimal insurRate;
    /**
     * 许可证编号
     */
    private String licenseNo;
    /**
     * 备案号
     */
    private String manualNo;
    /**
     * 净重
     */
    private BigDecimal netWt;
    /**
     * 备注
     */
    private String noteS;
    /**
     * 杂费币制
     */
    private String otherCurr;
    /**
     * 杂费标志
     */
    private String otherMark;
    /**
     * 杂费／率 海关精度为Z(12).Z(7)
     */
    private BigDecimal otherRate;
    /**
     * 消费使用/生产销售单位代码 10位或9位，或NO
     */
    private String ownerCode;
    /**
     * 消费使用/生产销售单位名称
     */
    private String ownerName;
    /**
     * 件数
     */
    private Integer packNo;
    /**
     * 申报人标识
     */
    private String partenerID;
    /**
     * 打印日期
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd")
    private Date pDate;
    /**
     * 预录入编号
     */
    private String preEntryId;
    /**
     * 风险评估参数 空值（上海专用）
     */
    private String risk;
    /**
     * 数据中心统一编号 唯一标识一票报关单。首次导入传空值，由系统自动生成并返回客户端。
     */
    private String seqNo;
    /**
     * 宁波通关申请单号
     */
    private String tgdNo;
    /**
     * 启运国/运抵国 （进口：起运国；出口：运抵国）
     */
    private String tradeCountry;
    /**
     * 监管方式
     */
    private String tradeMode;
    /**
     * 境内收发货人编号
     */
    private String tradeCode;
    /**
     * 运输方式代码
     */
    private String trafMode;
    /**
     * 运输工具代码及名称
     */
    private String trafName;
    /**
     * 境内收发货人名称
     */
    private String tradeName;
    /**
     * 成交
     */
    private String transMode;
    /**
     * 单据类型 一般报关单填空值。
     * ML：保税区进出境备案清单
     * SD: “属地申报，口岸验放”报关单
     * LY:两单一审备案清单
     * CL:汇总征税报关单
     * SS:”属地申报，属地验放”报关单
     * ZB：自主报税
     * SW：税单无纸化
     * SZ：水运中转普通报关单
     * SM：水运中转保税区进出境备案清单
     * SL：水运中转两单一审备案清单
     * Z:自报自缴
     * MF:公路舱单跨境快速通关报关单
     * MK: 公路舱单跨境快速通关备案清单
     * ZY:自报自缴，两单一审备案清单；
     * ZC:自报自缴，汇总征税报关单；
     * ZW:自报自缴，税单无纸化；
     * ZF:自报自缴，公路舱单跨境快速通关；
     * ZX：自报自缴，多项式联运。
     * MT:多项式联运
     */
    private String type;
    /**
     * 录入员IC卡号 导入暂存时，必填
     */
    private String typistNo;
    /**
     * 包装种类
     */
    private String wrapType;
    /**
     * 担保验放标志 担保验放:1:是；0否
     */
    private String chkSurety;
    /**
     * 备案清单类型 自贸区特有的类型：
     * 1：普通备案清单
     * 2：先进区后报关
     * 3：分送集报备案清单
     * 4；分送集报报关单
     * 注：当选择自贸区类型时，以下字段不可填写
     * 表头：合同号、批准文件、内销比率
     * 表体：用途、工缴费、成品货号
     * 自由文本：监管仓号、报关员联系方式
     */
    private String billType;
    /**
     * 录入单位统一编码
     */
    private String copCodeScc;
    /**
     * 收发货人统一编码
     */
    private String tradeCoScc;
    /**
     * 申报代码统一编码
     */
    private String agentCodeScc;
    /**
     * 消费使用/生产销售单位单位统一编码
     */
    private String ownerCodeScc;
    /**
     * 承诺事项 1勾选 0-未选9-空
     * 第一位特殊关系确认
     * 第二位价格影响确认
     * 第三位支付特许权使用费确认
     */
    private String promisItems;
    /**
     * 贸易国别
     */
    private String tradeAreaCode;
    /**
     * 查验分流 查验分流:
     * 1:表示是查验分流；
     * 0:表示不是查验分流
     */
    private String checkFlow;
    /**
     * 税收征管标记 税收征管标记：0-无 1-有
     */
    private String taxAaminMark;
    /**
     * 标记唛码 标记及号码【本批货物的标记和号码】
     */
    private String markNo;
    /**
     * 启运港代码 启运港代码【本批货物的启运口岸】
     */
    private String despPortCode;
    /**
     * 入境口岸代码 入境口岸代码【货物从运输工具卸离的第一个境内口岸】
     */
    private String entyPortCode;
    /**
     * 存放地点 货物存放地点【报检时货物的存放地点】
     */
    private String goodsPlace;
    /**
     * B/L号 提货单号【本批货物的提货单或出库单号码】
     * 设置JsonProperty，否则json解析时会解析成全小写，原因不明
     */
    @JsonProperty("bLNo")
    private String bLNo;
    /**
     * 口岸检验检疫机关 施检机构【对本批货物实施检验检疫的机构】
     */
    private String inspOrgCode;
    /**
     * 特种业务标识 特种业务标识：0未勾选，1选中。
     * 第一位：“国际赛事”；
     * 第二位：“特殊进出军工物资”；
     * 第三位:“国际援助物资”；
     * 第四位：“国际会议”；
     * 第五位：“直通放行”；
     * 第六位：“外交礼遇”；
     * 第七位：“转关
     */
    private String specDeclFlag;
    /**
     * 目的地检验检疫机关 目的机构代码【入境货物流向的目的地检验检疫机构】
     */
    private String purpOrgCode;
    /**
     * 发货日期【本批拟发货的日期】
     * 格式为：yyyyMMdd
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd")
    private Date despDate;
    /**
     * 卸毕日期【本批货物全部卸离运输工具的日期】
     * 格式为：yyyyMMdd
     */
    @DateTimeFormat(pattern = "yyyyMMdd")
    @JsonFormat(pattern = "yyyyMMdd")
    private Date cmplDschrgDt;
    /**
     * 领证地
     */
    private String vsaOrgCode;
    /**
     * 关联理由【关联报检号的关联理由】
     */
    private String correlationReasonFlag;
    /**
     * 入境原集装箱装载直接到目的机构，【1：是；0：否】
     */
    private String origBoxFlag;
    /**
     * 申报人员姓名
     */
    private String declareName;
    /**
     * 勾选 0-未选，有其他包装；1：选中，无其他包装。
     */
    private String noOtherPack;
    /**
     * 检验检疫受理机关
     */
    private String orgCode;
    /**
     * 境外发货人代码
     */
    private String overseasConsignorCode;
    /**
     * 境外收发货人名称
     */
    private String overseasConsignorCname;
    /**
     * 境外发货人名称（外文）
     */
    private String overseasConsignorEname;
    /**
     * 境外收发货人地址
     */
    private String overseasConsignorAddr;
    /**
     * 境外收货人编码
     */
    private String overseasConsigneeCode;
    /**
     * 境外收货人名称(外文)
     */
    private String overseasConsigneeEname;
    /**
     * 境内收发货人名称（外文）
     */
    private String domesticConsigneeEname;
    /**
     * 关联号码
     */
    private String correlationNo;
    /**
     * EDI申报备注
     */
    private String ediRemark;
    /**
     * EDI申报备注2
     */
    private String ediRemark2;
    /**
     * 境内收发货人检验检疫编码
     */
    private String tradeCiqCode;
    /**
     * 消费使用/生产销售单位检验检疫编码
     */
    private String ownerCiqCode;
    /**
     * 申报单位检验检疫编码
     */
    private String declCiqCode;

}
