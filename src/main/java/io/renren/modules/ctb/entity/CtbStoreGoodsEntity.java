package io.renren.modules.ctb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.ctb.vo.SearchStoreGoodsVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品库存表
 */
@Data
@TableName("ctb_store_goods")
public class CtbStoreGoodsEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联服务公司
     */
    private Long fkServiceCompanyId;
    /**
     * 商品
     */
    private Long fkGoodsId;
    /**
     * 库存商品数量
     */
    private BigDecimal goodsStoreCount;
    /**
     * 最后更新时间
     */
    private Date updateTime;
    /**
     * saas业务分离，关联cbs_company_id
     */
    private Long ctbTenantId;

    @ApiModelProperty("ctb_directory_service_company")
    @TableField(exist = false)
    private CtbDirectoryServiceCompanyEntity serviceCompanyEntity;

    @ApiModelProperty("商品实体")
    @TableField(exist = false)
    private CtbDirectoryGoodsEntity goodsEntity;

    @ApiModelProperty("搜索用入参")
    @TableField(exist = false)
    private SearchStoreGoodsVo searchForm;
}
