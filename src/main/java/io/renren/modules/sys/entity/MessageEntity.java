package io.renren.modules.sys.entity;

import io.renren.modules.cbs.entity.CbsContractEntity;
import io.renren.modules.cbs.entity.CbsImportEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 5058040228733290363L;
    private String title;
    private CbsContractEntity contractEntity;
    private CbsImportEntity importEntity;
}
