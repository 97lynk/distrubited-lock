package io.a97lynk.schedule.kafka.consumer;

import java.io.Serializable;

public interface KafkaConsumer<T extends Serializable> {

	void receive(T messages, String key, Integer partition, Long offset);
}
