package io.renren.modules.ctb.entity;

import io.renren.modules.cbs.entity.StatisticalContract;
import io.renren.modules.cbs.entity.StatisticalDigital;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * 首页
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias(value = "CtbHomeEntity")
public class CtbHomeEntity {
    @ApiModelProperty("总收入")
    private StatisticalDigital totalRevenue;

    @ApiModelProperty("加贸")
    private StatisticalDigital canadaTrade;

    @ApiModelProperty("报关-入")
    private StatisticalDigital applyToCustomsIn;
    @ApiModelProperty("报关-出")
    private StatisticalDigital applyToCustomsOut;

    @ApiModelProperty("收入list")
    private List<StatisticalDigital> incomeList;
    @ApiModelProperty("支出list")
    private List<StatisticalDigital> expendList;

    @ApiModelProperty("加贸")
    private List<StatisticalDigital> canadaTradeArray;
    @ApiModelProperty("进口")
    private List<StatisticalDigital> importArray;
    @ApiModelProperty("出口")
    private List<StatisticalDigital> exportArray;

}
