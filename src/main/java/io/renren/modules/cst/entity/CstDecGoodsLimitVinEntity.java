package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 进出口报关单许可证VIN信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Data
@TableName("cst_dec_goods_limit_vin")
public class CstDecGoodsLimitVinEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 许可证编号
     */
    private String licenceNo;
    /**
     * 许可证类别代码
     */
    private String licTypeCode;
    /**
     * VIN序号
     */
    private String vinNo;
    /**
     * 提/运单日期 yyyyMMdd
     */
    private Date billLadDate;
    /**
     * 质量保质期
     */
    private String qualityQgp;
    /**
     * 发动机号或电机号
     */
    private String motorNo;
    /**
     * 车辆识别代码（VIN）
     */
    private String vinCode;
    /**
     * 底盘(车架)号
     */
    private String chassisNo;
    /**
     * 发票所列数量
     */
    private BigDecimal invoiceNum;
    /**
     * 品名（中文名称）
     */
    private String prodCnnm;
    /**
     * 品名（英文名称）
     */
    private String prodEnnm;
    /**
     * 型号（英文）
     */
    private String modelEn;
    /**
     * 单价
     */
    private BigDecimal pricePerUnit;
    /**
     * 发票号
     */
    private String invoiceNo;

}
