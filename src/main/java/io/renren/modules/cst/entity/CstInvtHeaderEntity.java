package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 保税核注清单表头
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-10 17:20:35
 */
@Data
@TableName("cst_invt_header")
public class CstInvtHeaderEntity implements Serializable {
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
	 * 清单编号 海关审批通过后系统自动返填
	 */
	private String bondInvtNo;
	/**
	 * 清单预录入统一编号 首次操作时系统生成并返填
非首次操作须填写
	 */
	private String seqNo;
	/**
	 * 备案编号
	 */
	private String putrecNo;
	/**
	 * 企业内部清单编号 由企业自行编写
	 */
	private String etpsInnerInvtNo;
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
	 * 收货企业编号
	 */
	private String rcvgdEtpsno;
	/**
	 * 收发货企业社会信用代码
	 */
	private String rvsngdRtpsSccd;
	/**
	 * 收货企业名称
	 */
	private String rcvgdEtpsNm;
	/**
	 * 申报企业社会信用代码
	 */
	private String dclEtpsSccd;
	/**
	 * 申报企业编号
	 */
	private String dclEtpsno;
	/**
	 * 申报企业名称
	 */
	private String dclEtpsNm;
	/**
	 * 清单申报时间 例如：20121122
	 */
	private String invtDclTime;
	/**
	 * 报关单申报日期
	 */
	private Date entryDclTime;
	/**
	 * 对应报关单编号 如果企业导入报文选择：报关：1、报关单类型：X或Y、报关类型为：2-对应报关时，该项为必填项
	 */
	private String entryNo;
	/**
	 * 关联清单编号 结转类专用，检控要求复杂，见需求文档
	 */
	private String rltInvtNo;
	/**
	 * 关联备案编号 结转类专用
	 */
	private String rltPutrecNo;
	/**
	 * 关联报关单编号 可录入或者系统自动生成报关单后返填
二线取消报关的情况下使用，用于生成区外一般贸易报关单。
如果企业导入报文选择：报关为：1、报关单类型为：X或Y、报关类型为：1-关联报关时，该项为必填项
	 */
	private String rltEntryNo;
	/**
	 * 关联报关单境内收发货人社会信用代码 二线取消报关的情况下使用，用于生成区外一般贸易报关单。暂未使用
	 */
	private String rltEntryBizopEtpsSccd;
	/**
	 * 关联报关单境内收发货人编号 
当报关类型DCLCUS_TYPECD字段为1时，该字段必填
当报关类型DCLCUS_TYPECD字段为1时，该字段必填
报关类型为关联报关时必填。二线取消报关的情况下使用，用于生成区外一般贸易报关单。暂未使用
	 */
	private String rltEntryBizopEtpsno;
	/**
	 * 关联报关单境内收发货人名称 当报关类型DCLCUS_TYPECD字段为1时，该字段必填
同上
	 */
	private String rltEntryBizopEtpsNm;
	/**
	 * 关联报关单收发货单位社会统一信用代码 二线取消报关的情况下使用，用于生成区外一般贸易报关单。暂未使用
	 */
	private String rltEntryRvsngdEtpsSccd;
	/**
	 * 关联报关单海关收发货单位编码 当报关类型DCLCUS_TYPECD字段为1时，该字段必填
报关类型为关联报关时必填。二线取消报关的情况下使用，用于生成区外一般贸易报关单。
	 */
	private String rltEntryRcvgdEtpsno;
	/**
	 * 关联报关单收发货单位名称 当报关类型DCLCUS_TYPECD字段为1时，该字段必填
	 */
	private String rltEntryRcvgdEtpsNm;
	/**
	 * 关联报关单申报单位社会统一信用代码 二线取消报关的情况下使用，用于生成区外一般贸易报关单。暂未使用
	 */
	private String rltEntryDclEtpsSccd;
	/**
	 * 关联报关单海关申报单位编码 当报关类型DCLCUS_TYPECD字段为1时，该字段必填
报关类型为关联报关时必填。二线取消报关的情况下使用，用于生成区外一般贸易报关单。
	 */
	private String rltEntryDclEtpsno;
	/**
	 * 关联报关单申报单位名称 当报关类型DCLCUS_TYPECD字段为1时，该字段必填
	 */
	private String rltEntryDclEtpsNm;
	/**
	 * 进出境关别 　可允许修改
	 */
	private String impexpPortcd;
	/**
	 * 申报地关区代码
	 */
	private String dclPlcCuscd;
	/**
	 * 进出口标记代码 I：进口,E：出口
	 */
	private String impexpMarkcd;
	/**
	 * 料件成品标记代码 I：料件，E：成品
	 */
	private String mtpckEndprdMarkcd;
	/**
	 * 监管方式代码 　可允许修改
	 */
	private String supvModecd;
	/**
	 * 运输方式代码 　可允许修改
	 */
	private String trspModecd;
	/**
	 * 是否报关标志 1.报关2.非报关
	 */
	private String dclcusFlag;
	/**
	 * 报关类型代码 1.关联报关2.对应报关；
当报关标志为“1.报关”时，企业可选择“关联报关单”/“对应报关单”；
当报关标志填写为“2.非报关”时，报关标志填写为“2.非报关”该项不可填。
	 */
	private String dclcusTypecd;
	/**
	 * 核扣标记代码 系统自动反填
	 */
	private String vrfdedMarkcd;
	/**
	 * 清单进出卡口状态代码 系统自动反填
	 */
	private String invtIochkptStucd;
	/**
	 * 预核扣时间
	 */
	private Date prevdTime;
	/**
	 * 正式核扣时间
	 */
	private Date formalVrfdedTime;
	/**
	 * 申请表编号
	 */
	private String applyNo;
	/**
	 * 流转类型 非流转类不填写，流转类填写: A：加工贸易深加工结转、B：加工贸易余料结转、C：不作价设备结转、D:区间深加工结转、E：区间料件结转
	 */
	private String listType;
	/**
	 * 录入企业编号
	 */
	private String inputCode;
	/**
	 * 录入企业社会信用代码
	 */
	private String inputCreditCode;
	/**
	 * 录入单位名称
	 */
	private String inputName;
	/**
	 * 申报人IC卡号
	 */
	private String icCardNo;
	/**
	 * 录入日期
	 */
	private String inputTime;
	/**
	 * 清单状态 系统自动反填
	 */
	private String listStat;
	/**
	 * 对应报关单申报单位社会统一信用代码
	 */
	private String corrEntryDclEtpsSccd;
	/**
	 * 对应报关单申报单位代码 当报关类型DCLCUS_TYPECD字段为2时，该字段必填
	 */
	private String corrEntryDclEtpsNo;
	/**
	 * 对应报关单申报单位名称 当报关类型DCLCUS_TYPECD字段为2时，该字段必填
	 */
	private String corrEntryDclEtpsNm;
	/**
	 * 报关单类型 
1:进口报关单
2:出口报关单
3:进境备案清单
4:出境备案清单
5:进境两单一审备案清单
6:出境两单一审备案清单
B:转关提前进境备案清单
C:转关提前出境备案清单
F:出口二次转关单
G:进口提前/工厂验放报关单
H:出口提前/工厂验放报关单
I:进口提前/暂时进口报关单
J:出口提前/暂时出口报关单
K:进口提前/中欧班列报关单
L:出口提前/中欧班列报关单
M:出口提前/市场采购报关单
N:出口提前/空运联程报关单
O:进口提前/工厂验放备案清单
P:出口提前/工厂验放备案清单
Q:进口提前/暂时进口备案清单
R:出口提前/暂时出口备案清单
S:进口提前/中欧班列备案清单
T:出口提前/中欧班列备案清单
U:出口提前/市场采购备案清单
V:出口提前/空运联程备案清单
X-进口两步申报报关单
Y-进口两步申报备案清单
	 */
	private String decType;
	/**
	 * 修改变更次数 默认为0，变更修改时需填写为对应第几次变更
	 */
	private Long chgTmsCnt;
	/**
	 * 起运/运抵国(地区） 可允许修改
	 */
	private String stshipTrsarvNatcd;
	/**
	 * 清单类型
0：普通清单
1：集报清单
3：先入区后报关
4：简单加工
5：保税展示交易
6：区内流转
7：区港联动
8：保税电商
9：一纳成品内销
	 */
	private String invtType;
	/**
	 * 报关状态 标明对应（关联）报关单放行状态，目前只区分 0：未放行，1：已放行。该字段用于区域或物流账册的清单，该类型清单满足两个条件才能核扣：报关单被放行+货物全部过卡
	 */
	private String entryStucd;
	/**
	 * 核放单生成标志代码
	 */
	private String passportUsedTypeCd;
	/**
	 * 申报类型 1-备案申请 2-变更申请 3-删除申请
	 */
	private String dclTypecd;
	/**
	 * 报关单同步修改标志
	 */
	private String needEntryModified;
	/**
	 * 计征金额
	 */
	private String levyBlAmt;
	/**
	 * 备注
	 */
	private String rmk;
	/**
	 * 备用1
	 */
	private String param1;
	/**
	 * 备用2
	 */
	private String param2;
	/**
	 * 备用3
	 */
	private String param3;
	/**
	 * 是否生成报关单 1-生成 2-不生成
	 */
	private String genDecFlag;
	/**
	 * 主管海关
	 */
	private String masterCuscd;

}
