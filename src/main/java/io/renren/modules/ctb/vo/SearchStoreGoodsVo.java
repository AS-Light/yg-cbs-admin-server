package io.renren.modules.ctb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchStoreGoodsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String hsCode;
    private String fkServiceCompanyId;
    private String serviceCompanyName;
    private String goodsName;
    private BigDecimal goodsStoreCount;

    private Date startingTime;
    private Date endTime;

    private Integer status;
}
