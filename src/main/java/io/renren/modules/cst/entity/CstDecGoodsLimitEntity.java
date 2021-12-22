package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 进出口报关单许可证信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Data
@TableName("cst_dec_goods_limit")
public class CstDecGoodsLimitEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 商品序号 货物序号
     */
    private Integer goodsNo;
    /**
     * 许可证类别代码 根据HS编码设限情况确定
     */
    private String licTypeCode;
    /**
     * 许可证编号
     */
    private String licenceNo;
    /**
     * 许可证核销明细序号
     */
    private String licWrtofDetailNo;
    /**
     * 许可证核销数量
     */
    private String licWrtofQty;
    /**
     * 许可证核销数量单位
     */
    private String licWrtofQtyUnit;

}
