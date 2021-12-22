package io.renren.modules.org_ctb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 结算账户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
@Data
@TableName("org_ctb_account")
public class OrgCtbAccountEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 账号
     */
    private String account;
    /**
     * 收款人
     */
    private String payee;
    /**
     * 开户行
     */
    private String bank;
    /**
     * saas业务分离，关联ctb_company_id
     */
    private Long ctbTenantId;

}
