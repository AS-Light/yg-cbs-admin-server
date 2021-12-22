package io.renren.modules.cst.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 进出口报关单随附单证
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-02 12:19:55
 */
@Data
@TableName("cst_dec_license_docus")
public class CstDecLicenseDocusEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 单证代码
     */
    private String docuCode;
    /**
     * 单证编号 当单证代码为U时，单证编号格式为：通关证明编号（12位，前4位为年份，后8位为随机号）+“:”+商品项号。其中商品项号在报关单表体中必须有商品对应，且多个单证编号中的商品项号不能重复
     */
    private String certCode;

}
