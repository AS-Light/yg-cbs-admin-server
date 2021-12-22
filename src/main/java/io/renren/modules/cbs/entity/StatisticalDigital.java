package io.renren.modules.cbs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias(value = "StatisticalDigital")
public class StatisticalDigital {
    private Integer type;
    private String processing;
    private String total;

    private String months;
    private String money;
}