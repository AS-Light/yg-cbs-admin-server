package io.renren.modules.cst.entity;

import io.renren.common.utils.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CstDecEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long fkContractId;
    private Long fkOrderId;
    private CstDecHeaderEntity decHeaderEntity;
    private List<CstDecListEntity> decGoodsList;
    private List<CstDecContainerEntity> decContainerList;

    private Long importId;
    private Long exportId;
    private Integer type;
}
