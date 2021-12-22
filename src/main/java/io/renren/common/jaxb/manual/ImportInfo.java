package io.renren.common.jaxb.manual;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 海关加工贸易手册备案表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportInfo {
    private String MessageType;
    private String OpType;
    private String HostId;
    private String FileSize;
    private String Sign;
}
