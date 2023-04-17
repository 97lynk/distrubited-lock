package io.a97lynk.schedule.kafka.producer;

import java.io.Serializable;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

public interface KafkaProducer<K extends Serializable, V extends Serializable> {

    void send(String topicName, K key, V message, ListenableFutureCallback<SendResult<K, V>> callback);
}
