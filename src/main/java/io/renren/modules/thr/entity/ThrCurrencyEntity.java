package io.renren.modules.thr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-14 16:55:11
 */
@Data
@TableName("thr_currency")
public class ThrCurrencyEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;

    /**
     * 海关编码
     */
    private String code;
    /**
     * 英文缩写
     */
    private String abbreviation;
    /**
     * 英文名称
     */
    private String nameEn;
    /**
     * 中文名称
     */
    private String nameCn;
    /**
     * 中间汇率 100该币种兑换人民币数量
     */
    private BigDecimal exchangeRate;
}
