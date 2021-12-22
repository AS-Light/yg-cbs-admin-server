package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 报关行货运代理合同
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_service_contract")
public class CtbServiceContractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 合同编号，公司大写缩写开头，日期到日结尾，如重复结尾加【-1】【-2】类似后缀
     */
    private String code;
    /**
     * 甲方（企业）
     */
    private String partA;
    /**
     * 甲方法定代表人
     */
    private String partAManager;
    /**
     * 甲方住址
     */
    private String partAAddress;
    /**
     * 甲方联系电话
     */
    private String partAPhone;
    /**
     * 甲方邮箱
     */
    private String partAEmail;
    /**
     * 乙方（报关行）
     */
    private String partB;
    /**
     * 乙方法定代表人
     */
    private String partBManager;
    /**
     * 乙方住址
     */
    private String partBAddress;
    /**
     * 乙方联系电话
     */
    private String partBPhone;
    /**
     * 乙方收款账号
     */
    private String partBAccount;
    /**
     * 收款日（每月）
     */
    private String payDayPermonth;
    /**
     * saas分割，报关行id
     */
    private Long ctbTenantId;
    /**
     * 生成的PDF
     */
    private String pdf;

    @ApiModelProperty("服务公司ctb_directory_service_company的ID")
    @TableField(exist = false)
    private Long serviceCompanyId;

    @ApiModelProperty("关联报价单实体")
    @TableField(exist = false)
    private CtbPriceListEntity priceListEntity;
}
