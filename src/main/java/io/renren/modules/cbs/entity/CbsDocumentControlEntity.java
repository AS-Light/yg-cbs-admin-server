package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.cbs.vo.ContractVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-03-27 10:02:18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("CbsDocumentControlEntity")
@TableName("cbs_document_control")
public class CbsDocumentControlEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long fkContractId;
    /**
     *
     */
    private String businessTable;
    /**
     *
     */
    private Long businessId;
    /**
     *
     */
    private String businessFile;
    /**
     *
     */
    private Date createTime;
    /**
     * saas业务分离，关联cbs_company_id
     */
    private Long tenantId;

    @ApiModelProperty("合同标题")
    @TableField(exist = false)
    private String title;
    @ApiModelProperty("合同编码")
    @TableField(exist = false)
    private String contractCode;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private ContractVo searchForm;

}
