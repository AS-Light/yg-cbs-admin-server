package io.renren.modules.ctb.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 统一处理附件上传VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedUploadingVo implements Serializable {
    // 插入附件表返回的ID
    private Long id;
    // 表名
    private String tableName;
    // 字段
    private String field;
    // 字段值
    private Long fieldValue;
    // 单图片
    private String img;
    // 图片 list
    private List<String> imgList;
    // 业务ID list
    private List<Long> idList;

}
