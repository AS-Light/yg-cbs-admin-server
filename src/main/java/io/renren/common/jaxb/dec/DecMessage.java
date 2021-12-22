package io.renren.common.jaxb.dec;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DecMessage")
public class DecMessage {
    private DecHead DecHead;
    @XmlElementWrapper(name = "DecLists")
    private List<DecList> DecList;
    @XmlElementWrapper(name = "Containers")
    private List<Container> Container;
}
