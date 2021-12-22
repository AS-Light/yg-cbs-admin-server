package io.renren.common.jaxb.manual;

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
@XmlRootElement(name = "EmlMessage")
public class EmlMessage {
    // @XmlElement(name = "NptsEmlHead") 用来做标签别名需要配合@XmlAccessorType(XmlAccessType.FIELD)使用     @TableField(exist = false)
    private NptsEmlHead NptsEmlHead;
    private List<NptsEmlImg> NptsEmlImg;
    private List<NptsEmlExg> NptsEmlExg;
    // @XmlElementWrapper(name = "NptsEmlConsumes") list 外面再套一层的标签名
    private List<NptsEmlConsume> NptsEmlConsume;
    private String NptsSign;
    // @XmlAttribute(name = "OperCusRegCode") 映射到XmlRootElement的属性
    private String OperCusRegCode;
    private ImportInfo ImportInfo;
}