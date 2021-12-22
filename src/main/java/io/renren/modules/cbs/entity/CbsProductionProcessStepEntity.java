package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 生产工艺步骤表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-28 15:32:13
 */
@Data
@TableName("cbs_production_process_step")
public class CbsProductionProcessStepEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联生产工艺id
     */
    private Long fkProductionProcessId;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否开始节点 默认false
     */
    private Integer isStart;
    /**
     * 是否结束节点 默认false
     */
    private Integer isEnd;
    /**
     * 上一个步骤节点id
     */
    @TableField("fk_last_step_ids")
    private List<Long> lastStepIdList;
    /**
     * 下一个步骤节点id
     */
    @TableField("fk_next_step_ids")
    private List<Long> nextStepIdList;

    @ApiModelProperty("cbs_production_process_step_item")
    @TableField(exist = false)
    private List<CbsProductionProcessStepItemEntity> itemList;
}
