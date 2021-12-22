package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.modules.cbs.entity.CbsPartnerEntity;
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
 * @date 2020-04-11 16:33:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_partner")
public class CtbPartnerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public CtbPartnerEntity(CbsPartnerEntity cbsPartnerEntity) {
        name = cbsPartnerEntity.getName();
        fkCountryId = cbsPartnerEntity.getFkCountryId();
        customsCode = cbsPartnerEntity.getCustomsCode();
        inspectionCode = cbsPartnerEntity.getInspectionCode();
        creditCode = cbsPartnerEntity.getCreditCode();
        aeoCode = cbsPartnerEntity.getAeoCode();
        contactor = cbsPartnerEntity.getContactor();
        phone = cbsPartnerEntity.getPhone();
        address = cbsPartnerEntity.getAddress();
        available = cbsPartnerEntity.getAvailable();
    }

    /**
     *
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
     * 商检编码
     */
    private String inspectionCode;
    /**
     * 十八位社会信用统一编码
     */
    private String creditCode;
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
     * 是否本公司，只在公司创建时插入的该条数据为true
     */
    private Integer isTheCompany;
    /**
     * saas业务分离，关联ctb_company_id
     */
    private Long ctbTenantId;

    @ApiModelProperty("国家实体")
    @TableField(exist = false)
    private ThrCountryCodeEntity countryEntity;

    @ApiModelProperty("伙伴类型列表")
    @TableField(exist = false)
    private List<CtbPartnerTypeEntity> typeList;
}
