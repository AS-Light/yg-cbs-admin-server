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
import java.util.List;

/**
 * 报关行报价单表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-11 16:33:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ctb_price_list")
public class CtbPriceListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联合同（ctb_service_contract）id
     */
    private Long fkContractId;
    /**
     * 乙方地址
     */
    private String address;
    /**
     * 乙方电话
     */
    private String phone;
    /*
     * 代码生成的PDF
     */
    private String pdf;

    @ApiModelProperty("报价项目列表")
    @TableField(exist = false)
    private List<CtbPriceListItemEntity> itemEntityList;

}
