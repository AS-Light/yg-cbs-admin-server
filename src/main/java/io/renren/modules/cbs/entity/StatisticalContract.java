package io.renren.modules.cbs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias(value = "StatisticalContract")
public class StatisticalContract {
    private Long id;
    private Integer type;
    private Integer status;
    private String contractCode;
    private String title;
    private String introduction;
}