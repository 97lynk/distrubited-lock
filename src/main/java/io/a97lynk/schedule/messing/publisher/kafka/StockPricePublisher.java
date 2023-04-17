package io.a97lynk.schedule.messing.publisher.kafka;

import io.a97lynk.schedule.dto.JobDto;
import io.a97lynk.schedule.kafka.producer.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@AllArgsConstructor
public class StockPricePublisher {

	private final KafkaProducer<String, JobDto<Object>> kafkaProducer;

	//	@Override
	public void send(JobDto<Object> message, ListenableFutureCallback<SendResult<String, JobDto<Object>>> callback) {
		log.info("Send message {} to topic {}", message, "stock-update");
		kafkaProducer.send("stock-update", null, message, callback);
	}
}
