package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 进出口报关单表体
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cst_dec_list")
public class CstDecListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联表头（cst_dec_list）Id
     */
    private Long fkDecHeaderId;
    /**
     * 归类标志 空值
     */
    private String classMark;
    /**
     * 商品编号 [0-9]{10}
     */
    private String codeTS;
    /**
     * 备案序号 程序控制9位
     */
    private Long contrItem;
    /**
     * 海关精度z(15).z(4)
     */
    private BigDecimal declPrice;
    /**
     * 申报总价 海关精度z(17).z(2)
     */
    private BigDecimal declTotal;
    /**
     * 征减免税方式 [0-9]
     */
    private String dutyMode;
    /**
     * 货号
     */
    private String exgNo;
    /**
     * 版本号
     */
    private String exgVersion;
    /**
     * 申报计量单位与法定单位比例因子
     */
    private BigDecimal factor;
    /**
     * 第一计量单位（法定单位）  [0-9]{3}
     */
    private String firstUnit;
    /**
     * 第一法定数量
     */
    private BigDecimal firstQty;
    /**
     * 成交计量单位 [0-9]{3}
     */
    @JsonProperty("gUnit")
    private String gUnit;
    /**
     * 商品规格、型号 海关精度50
     */
    @JsonProperty("gModel")
    private String gModel;
    /**
     * 商品名称 海关精度50
     */
    @JsonProperty("gName")
    private String gName;
    /**
     * 商品序号 海关精度9
     */
    @JsonProperty("gNo")
    private Long gNo;
    /**
     * 成交数量
     */
    @JsonProperty("gQty")
    private BigDecimal gQty;
    /**
     * 对于出口报关单，表体的originalCountry需要填目的国，destinationCountry填的是原产国。
     * 对于进口报关单，表体的originalCountry需要填原产国，destinationCountry填的是目的国。
     */
    private String originCountry;
    /**
     * 第二计量单位 [0-9]{3}
     */
    private String secondUnit;
    /**
     * 第二法定数量
     */
    private BigDecimal secondQty;
    /**
     * 成交币制
     */
    private String tradeCurr;
    /**
     * 用途/生产厂家
     */
    private String useTo;
    /**
     * 工缴费 海关精度z(17).z(2)
     */
    private BigDecimal workUsd;
    /**
     * 对于出口报关单，表体的originalCountry需要填目的国，destinationCountry填的是原产国。
     * 对于进口报关单，表体的originalCountry需要填原产国，destinationCountry填的是目的国。
     */
    private String destinationCountry;
    /**
     * 填写3位检验检疫编码 危险品
     */
    private String ciqCode;
    /**
     * 申报货物名称(外文)【货物的外文名称】
     */
    private String declGoodsEname;
    /**
     * 原产地区代码【原产国内的生产区域，如州、省等】
     */
    private String origPlaceCode;
    /**
     * 用途【本项货物的用途】
     */
    private String purpose;
    /**
     * 产品有效期【质量保障的截止日期】
     * 格式：yyyyMMdd
     */
    private Date prodValidDt;
    /**
     * 产品保质期【质量保障的月数或天数】
     */
    private String prodQgp;
    /**
     * 货物属性【声明本项货物的相关属性】
     */
    private String goodsAttr;
    /**
     * 成份/原料/组份【本项货物含有的成份或者货物原料,或化学品组份】
     */
    private String stuff;
    /**
     * UN编码【危险品货物对应的《危险化学品目录》中的UN编码】
     */
    private String uncode;
    /**
     * 危险品名称【危险品货物对应的《危险化学品目录》中的名称】
     */
    private String dangName;
    /**
     * 危包类别【危险化学品包装类别】
     */
    private String dangPackType;
    /**
     * 危包规格【危险化学品包装规格】
     */
    private String dangPackSpec;
    /**
     * 境外生产企业名称
     */
    private String engManEntCnm;
    /**
     * 非危险化学品
     */
    private String noDangFlag;
    /**
     * 目的地代码【货物在境内预定最终抵达的交货地】
     */
    private String destCode;
    /**
     * 原检验检疫货物规格
     */
    private String goodsSpec;
    /**
     * 货物型号【本项货物的所有型号】
     */
    private String goodsModel;
    /**
     * 货物品牌【本项货物的品牌】
     */
    private String goodsBrand;
    /**
     * 生产日期【货物的生产日期或者生产批号】
     * 格式：yyyy-MM-dd,多个日期用英文半角分号分隔。
     */
    private String produceDate;
    /**
     * 生产批号【货物的生产批号】
     */
    private String prodBatchNo;
    /**
     * 进口指境内目的地，出口指境内货源地
     */
    private String districtCode;
    /**
     * CIQ代码对应的商品描述
     */
    private String ciqName;
    /**
     * 生产单位注册号 出口独有
     */
    private String mnufctrRegno;
    /**
     * 生产单位名称 出口独有
     */
    private String mnufctrRegName;
}
