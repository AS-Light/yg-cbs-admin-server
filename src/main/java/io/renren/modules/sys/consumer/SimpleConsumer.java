package io.renren.modules.sys.consumer;

import io.renren.modules.sys.entity.MessageEntity;
import io.renren.modules.cbs.service.CbsContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleConsumer {

    @Autowired
    private CbsContractService contractService;

    /**
     * @Description: 消费合同状态变更
     * @Author: ChenNing
     * @Date: 9:23 2019/9/6
     */
    //    @KafkaListener(topics = "contract", containerFactory = "kafkaListenerContainerFactory")
    public void receiveContract(MessageEntity message) {
        contractService.updateAllById(message.getContractEntity());
    }

    /**
     * @Description: 消费进口状态变更
     * @Author: ChenNing
     * @Date: 17:27 2019/9/6
     */
    //    @KafkaListener(topics = "import", containerFactory = "kafkaListenerContainerFactory")
    public void receiveImport(MessageEntity message) {
        System.out.println(message.getImportEntity());
    }
}