package io.renren.modules.sys.producer;

import io.renren.modules.sys.entity.MessageEntity;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class SimpleProducer {

    @Autowired
    private KafkaTemplate<String, MessageEntity> kafkaTemplate;

    public void send(String topic, MessageEntity entity) {
        kafkaTemplate.send(topic, entity);
    }

    public void send(String topic, String key, MessageEntity entity) {

        ProducerRecord<String, MessageEntity> record = new ProducerRecord<>(topic, key, entity);

        long startTime = System.currentTimeMillis();

        ListenableFuture<SendResult<String, MessageEntity>> future = kafkaTemplate.send(record);

        future.addCallback(new ProducerCallback(startTime, key, entity));
    }

}