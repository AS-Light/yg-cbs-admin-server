package io.renren.modules.cbs.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SynImportAndExportImgVo {
    private String originalTable;
    private String targetTable;
    private Long originalFkId;
    private Long targetFkId;
    private String fkField;
    private String img;
}
