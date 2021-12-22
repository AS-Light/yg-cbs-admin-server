package io.renren.common.jaxb.dec;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 报关单集装箱
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Container implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 关联表头（cst_dec_list）Id
     */
    private Long fkDecHeaderId;
    /**
     * 集装箱号
     */
    private String containerId;
    /**
     * 集装箱规格
     */
    private String containerMd;
    /**
     * 商品项号用半角逗号分隔，如“1,3”
     */
    private String goodsNo;
    /**
     * 拼箱标识 0-否
     * 1-是
     */
    private String lclFlag;
    /**
     * 箱货重量
     */
    private BigDecimal goodsContaWt;
    /**
     * 自重
     */
    private BigDecimal containerWt;

}
