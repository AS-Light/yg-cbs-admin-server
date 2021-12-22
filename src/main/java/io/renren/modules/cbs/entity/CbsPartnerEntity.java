package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.thr.entity.ThrCountryCodeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 交易对象（公司）名录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-25 11:46:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cbs_partner")
public class CbsPartnerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 交易对象（公司）名称
     */
    private String name;
    /**
     * 所属国家，关联thr_country_code
     */
    private Long fkCountryId;
    /**
     * 十位海关编码
     */
    private String customsCode;
    /**
     * 十八位社会信用统一编码
     */
    private String creditCode;
    /**
     * 商检编码
     */
    private String inspectionCode;
    /**
     * AEO认证代码
     */
    private String aeoCode;
    /**
     * 联系人
     */
    private String contactor;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 交易对象（公司）地址
     */
    private String address;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 0为不可用1为可用
     */
    private Integer available;
    /**
     * 是否本公司
     */
    private Boolean isTheCompany;
    /**
     * saas业务分离，关联cbs_company_id
     */
    private Long tenantId;

    @ApiModelProperty("国家实体")
    @TableField(exist = false)
    private ThrCountryCodeEntity countryEntity;

    @ApiModelProperty("伙伴类型列表")
    @TableField(exist = false)
    private List<CbsPartnerTypeEntity> typeList;
}
