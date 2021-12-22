package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 进出口报关单 其他包装信息表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Data
@TableName("cst_dec_other_pack")
public class CstDecOtherPackEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 根据包装种类确定，挂装、散装、裸装时可不输入
     */
    private BigDecimal packQty;
    /**
     * 包装材料种类
     */
    private String packType;

}
