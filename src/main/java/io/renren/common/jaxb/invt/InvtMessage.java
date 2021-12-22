package io.renren.common.jaxb.invt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @description: EmlMessage
 * @author: chenning
 * @create: 2019-12-24 17:55
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "InvtMessage")
public class InvtMessage {
    private String SysId;
    private String OperCusRegCode;
    private InvtHeadType InvtHeadType;
    private List<InvtListType> InvtListType;
    private ImportInfo ImportInfo;
}