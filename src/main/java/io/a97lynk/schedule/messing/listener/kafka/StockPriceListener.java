package io.a97lynk.schedule.messing.listener.kafka;

import java.util.List;

import io.a97lynk.schedule.dto.JobDto;
import io.a97lynk.schedule.job.executor.UpdateStockPriceExecutor;
import io.a97lynk.schedule.kafka.consumer.KafkaConsumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class StockPriceListener implements KafkaConsumer<JobDto<Object>> {

	private final UpdateStockPriceExecutor executor;

	@Override
	@KafkaListener(id = "${spring.application.name}", topics = "stock-update")
	public void receive(@Payload JobDto<Object> message,
						@Header(value = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) String key,
						@Header(value = KafkaHeaders.RECEIVED_PARTITION_ID, required = false) Integer partition,
						@Header(value = KafkaHeaders.OFFSET, required = false) Long offset) {
		log.info("Receive message {} from topic {}", message, "stock-update");
		executor.updateStock(message);
	}
}
