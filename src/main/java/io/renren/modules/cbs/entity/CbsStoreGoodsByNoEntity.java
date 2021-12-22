package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.cbs.vo.ContractVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 产品库存表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-10-22 16:54:51
 */
@Data
public class CbsStoreGoodsByNoEntity extends BaseEntity {
    /**
     * 商品料号（即合同商品编号）
     */
    private Long goodsPartNo;

    /**
     * 料号库存列表
     */
    private List<CbsStoreGoodsEntity> storeGoodsList;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private ContractVo searchForm;
}
