package io.renren.common.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


/**
 * 基础信息
 *
 * @author chenning
 */
@Data
public class BaseEntity {

    @TableField(exist = false)
    private String page = "1";

    @TableField(exist = false)
    private String limit = "10";

    @TableField(exist = false)
    private String orderField;

    @TableField(exist = false)
    private String order;

}
