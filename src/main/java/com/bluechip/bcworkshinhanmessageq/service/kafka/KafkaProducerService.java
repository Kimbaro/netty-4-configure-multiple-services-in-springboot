package com.bluechip.bcworkshinhanmessageq.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.concurrent.Future;

@Slf4j
public class KafkaProducerService {
    private String encMsg;
    private String topic;
    private String brokers;

    public KafkaProducerService(String topic, String brokers, String encMsg) {
        this.encMsg = encMsg;
        this.topic = topic;
        this.brokers = brokers;
    }

    public void onApplicationEvent(String msg, KafkaProducer<String, String> producer) throws Exception {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic.trim(), null, msg); // (topic, key, value) 값이 들어갑니다.
        Future<RecordMetadata> future = producer.send(record); // 전송 요청
        RecordMetadata metadata = future.get();
        describeMetadata(metadata, msg);    // 응답 결과를 콘솔에 출력합니다.
    }

    private void describeMetadata(RecordMetadata metadata, String msg) {
        log.info("=== Metadata...");
        log.info("data      : " + msg);
        log.info("topic     : " + metadata.topic());
        log.info("partition : " + metadata.partition());
        log.info("offset    : " + metadata.offset());
        log.info("timestamp : " + metadata.timestamp());
        log.info("\n");
    }
}
