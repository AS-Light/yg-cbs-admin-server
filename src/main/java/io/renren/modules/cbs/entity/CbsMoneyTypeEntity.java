package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 收入/支出类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-20 12:25:27
 */
@Data
@TableName("cbs_money_type")
public class CbsMoneyTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 1、进口货值支出 2、出口货值收入 3、加工费 4、报关费 11、国际运费 12、国际保费 13、国际杂费 21、国内运费 22、国内杂费
     */
    private Integer type;
    /**
     *
     */
    private String name;
    /**
     * I、进 O、出
     */
    private String io;

    /**
     * 自建类型0否1是
     */
	@TableField(exist = false)
    private Integer selfBuiltType;
}
