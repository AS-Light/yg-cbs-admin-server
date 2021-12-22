package io.renren.modules.cbs.entity;

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
@Alias(value = "CbsHomeEntity")
public class CbsHomeEntity {
    @ApiModelProperty("合同总数")
    private List<StatisticalDigital> contractList;

    @ApiModelProperty("合同进料加工合同总数")
    private StatisticalDigital contractFeed;
    @ApiModelProperty("合同来料加工合同总数")
    private StatisticalDigital contractIncoming;
    @ApiModelProperty("合同进口")
    private StatisticalDigital contractImport;
    @ApiModelProperty("合同出口")
    private StatisticalDigital contractExport;
    @ApiModelProperty("合同国内采购")
    private StatisticalDigital contractDomesticPurchase;
    @ApiModelProperty("合同国内销售")
    private StatisticalDigital contractDomesticSales;

    @ApiModelProperty("收发货-进口")
    private StatisticalDigital deliveryImport;
    @ApiModelProperty("收发货-出口")
    private StatisticalDigital deliveryExport;
    @ApiModelProperty("收发货-国内进口")
    private StatisticalDigital deliveryDomesticImport;
    @ApiModelProperty("收发货-出口")
    private StatisticalDigital deliveryDomesticExport;

    @ApiModelProperty("生产")
    private StatisticalDigital produce;

    @ApiModelProperty("仓库-入")
    private StatisticalDigital warehouseIn;
    @ApiModelProperty("仓库-出")
    private StatisticalDigital warehouseOut;

    @ApiModelProperty("收入list")
    private List<StatisticalDigital> incomeList;
    @ApiModelProperty("支出list")
    private List<StatisticalDigital> expendList;

    @ApiModelProperty("待办合同-list")
    private List<StatisticalContract> upcomingContract;
    @ApiModelProperty("待办收发货- 进口收货 -list")
    private List<StatisticalContract> upcomingExport;
    @ApiModelProperty("待办收发货- 出口发货 -list")
    private List<StatisticalContract> upcomingImport;
    @ApiModelProperty("待办收发货- 国内收货 -list")
    private List<StatisticalContract> upcomingPurchase;
    @ApiModelProperty("待办收发货- 国内发货 -list")
    private List<StatisticalContract> upcomingSell;
    @ApiModelProperty("待办生产-list")
    private List<StatisticalContract> upcomingProduce;

}
