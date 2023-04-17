package io.a97lynk.schedule.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;

import java.io.Serializable;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaProducerImpl<K extends Serializable, V extends Serializable> implements KafkaProducer<K, V> {

	private final KafkaTemplate<K, V> kafkaTemplate;

	@Override
	public void send(String topicName, K key, V message, ListenableFutureCallback<SendResult<K, V>> callback) {
		try {
			ListenableFuture<SendResult<K, V>> send = kafkaTemplate.send(topicName, key, message);
			send.addCallback(callback);
		}
		catch (KafkaException ex) {
			log.error("Error when send kafka " + ex.getMessage());
		}
	}

	@PreDestroy
	public void close() {
		if (kafkaTemplate != null) {
			log.info("close");
			kafkaTemplate.destroy();
		}
	}
}
