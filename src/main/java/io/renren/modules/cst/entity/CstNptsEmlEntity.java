package io.renren.modules.cst.entity;

import io.renren.common.utils.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CstNptsEmlEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long fkContractId;
    private CstNptsEmlHeaderEntity emlHeader;
    private List<CstNptsEmlImgExgEntity> emlImgList;
    private List<CstNptsEmlConsumeEntity> emlConsumeList;
}
