package io.renren.modules.cbs.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.BaseEntity;
import io.renren.modules.cbs.vo.ContractVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 生产单表
 *
 * @author chenning
 * @email record_7@126.com
 * @date 2019-08-13 13:55:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("cbs_produce")
public class CbsProduceEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 关联合同单表（cbs_contract）ID
     */
    private Long fkContractId;
    /**
     * 关联生产公司名录表（cbs_directory_produce_company）ID
     */
    private Long fkProduceCompanyId;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 1：生产单创建（缺省） 2：生产开始提审（提审计划） 3：生产开始打回（退审计划） 4：生产开始审核通过（生产中） 5：生产结束提审（提审完成） 6：生产结束打回（退审完成） 7：生产结束审核通过（生产完成） 99：生产中止
     */
    private Integer status;
    /**
     * 操作人
     */
    private Long operator;

    @TableField(exist = false)
    private String searchKey;

    @ApiModelProperty("合同")
    @TableField(exist = false)
    private CbsContractEntity contractEntity;

    @ApiModelProperty("生产公司")
    @TableField(exist = false)
    private CbsDirectoryProduceCompanyEntity produceCompanyEntity;

    @ApiModelProperty("使用原料和产出产品缓存")
    @TableField(exist = false)
    private List<CbsProduceGoodsEntity> produceGoodsEntityList;

    @ApiModelProperty("生产过程（原料和产品）流")
    @TableField(exist = false)
    private List<CbsProduceGoodsStreamEntity> produceGoodsStreamEntityList;

    @ApiModelProperty("针对该次生产的产品入库流水")
    @TableField(exist = false)
    private List<CbsStoreGoodsInEntity> storeGoodsInEntityList;

    @ApiModelProperty("针对该次生产的原料出库流水")
    @TableField(exist = false)
    private List<CbsStoreGoodsOutEntity> storeGoodsOutEntityList;

    @ApiModelProperty("针对该次生产的原料还库流水")
    @TableField(exist = false)
    private List<CbsStoreGoodsOutEntity> rawMaterialBackInStoreList;

    @ApiModelProperty("多个合同ID查询")
    @TableField(exist = false)
    private List<Object> fkContractIds;

    @ApiModelProperty("查询参数")
    @TableField(exist = false)
    private ContractVo searchForm;

    @ApiModelProperty("store_goods_in_id")
    @TableField(exist = false)
    private Long storeGoodsInId;
    @ApiModelProperty("store_goods_in_status")
    @TableField(exist = false)
    private Integer storeGoodsInStatus;
}
