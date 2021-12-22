package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
@AllArgsConstructor
@NoArgsConstructor
@TableName("cst_npts_eml_img_exg")
public class CstNptsEmlImgExgEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联加工贸易手册表头
     */
    private Long fkEmlHeaderId;
    /**
     * 预录入统一编号。当企业执行手册首次暂存操作时，无需填写，电子口岸统一返回。
     * 电子口岸返回预录入统一编号后，之后暂存或申报数据时必填。
     */
    private String seqNo;
    /**
     * 商品序号
     */
    private Long gdsSeqno;
    /**
     * 料件成品类型代码 I-料件 E-成品
     */
    private String mtpckEndprdTypecd;
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
    private String gdsNm;
    /**
     * 规格型号描述 规范申报
     */
    private String endprdGdsSpcfModelDesc;
    /**
     * 申报计量单位代码 关联代码表
     */
    private String dclUnitcd;
    /**
     * 法定计量单位代码 根据商品编码，关联代码表
     */
    private String lawfUnitcd;
    /**
     * 法定第二计量单位代码
     */
    private String secdLawfUnitcd;
    /**
     * 申报数量
     */
    private BigDecimal dclQty;
    /**
     * 申报单价
     */
    private BigDecimal dclUprcAmt;
    /**
     * 申报总价
     */
    private BigDecimal dclTotalAmt;
    /**
     * 申报币制代码 关联代码表
     */
    private String dclCurrcd;
    /**
     * 产销国(地区） 关联代码表
     */
    private String natcd;
    /**
     * 征免方式 关联代码表
     */
    private String lvyrlfModecd;
    /**
     * 主辅料标记代码 1-主料 2-辅料，仅针对料件
     */
    private String adjmtrMarkcd;
    /**
     * 商品属性 预留，包括：１－消耗性物料；２－深加工结转；３-区域实质加工；8-现场自定义、 9-其他录入和逻辑
     */
    private String gdsAtrcd;
    /**
     * 修改标志代码 0-未修改 1-修改 2-删除 3-增加
     */
    private String modfMarkcd;
    /**
     * 备注
     */
    private String rmk;

}
