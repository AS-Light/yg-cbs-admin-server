package io.renren.modules.org_ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.bind.entity.BindCbsCustomsBrokerCtbCompanyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户公司表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-17 16:41:40
 */
@Data
@TableName("org_ctb_company")
public class OrgCtbCompanyEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 公司名称
     */
    private String name;
    /**
     * 公司名称英文名
     */
    private String nameEn;
    /**
     * 公司英文大写缩写，最多不超过4个字符
     */
    private String headerCode;
    /**
     * 法人
     */
    private String legalPersons;
    /**
     * 电话
     */
    private String companyPhone;
    /**
     * email
     */
    private String companyEmail;
    /**
     * 登录用公司编码，使用地区分组如大连0001号，DL0001
     */
    private String code;
    /**
     * 海关编码
     */
    private String customsCode;
    /**
     * 商检编码
     */
    private String inspectionCode;
    /**
     * 社会信用代码
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
     * 公司地址
     */
    private String address;
    /**
     * 公司英文地址
     */
    private String addressEn;
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
     * 有效时间
     */
    private Date effectiveTime;
    /**
     * 公司logo
     */
    private String logoUrl;
    /**
     * 公司官网地址
     */
    private String webUrl;
    /**
     * 备注，介绍
     */
    private String description;

    /**
     * 创建人
     */
    private Long createUserId;

    /**
     * 权限id
     */
    @TableField(exist = false)
    private List<Long> menuIdList;

    @ApiModelProperty("绑定Cbs")
    @TableField(exist = false)
    private BindCbsCustomsBrokerCtbCompanyEntity bindEntity;
}
