package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 收入/支出类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-09 10:52:57
 */
@Data
@TableName("cbs_money_type_oneself")
public class CbsMoneyTypeOneselfEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private String name;
    /**
     * I、收 O、支
     */
    private String io;
    /**
     * saas业务分离，关联cbs_company_id
     */
    private Long tenantId;

}
