package io.a97lynk.schedule.job.scheduler;

import io.a97lynk.schedule.dto.JobDto;
import io.a97lynk.schedule.messing.publisher.kafka.StockPricePublisher;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UpdateStockPriceScheduler {

	private final StockPricePublisher stockPricePublisher;

	private final int port;

	private final RedissonClient redisClient;

	RLock rLock;

	ExecutorService executorService = Executors.newFixedThreadPool(1);


	public UpdateStockPriceScheduler(StockPricePublisher stockPricePublisher, RedissonClient redisClient,
									 @Value("${server.port:8080}") int port) {
		this.stockPricePublisher = stockPricePublisher;
		this.redisClient = redisClient;
		this.port = port;
		this.rLock = redisClient.getLock("lockStockUpdate");
	}


//	@Scheduled(cron = "*/10 * * * * *")
	@Scheduled(fixedDelay = 10_000)
	public void sendMessage() {
		log.info("Send message from {}", Thread.currentThread().getId());
//		if (!rLock.tryLock()) {
//			if (!rLock.tryLock(1, 9, TimeUnit.SECONDS)) {
//			log.info("Lock is locked");
//			return;
//		}

		try {

//			if (!rLock.tryLock()) {
			if (!rLock.tryLock(1, 9, TimeUnit.SECONDS)) {
				log.info("Lock is locked");
				return;
			}

			log.info("Lock");
			JobDto<Object> job = JobDto.builder()
				.id(UUID.randomUUID())
				.name("Please get stock from server " + port)
				.data(Arrays.asList("MWG", "HPG", "VHM"))
				.createdTime(LocalDateTime.now())
				.build();

			log.info("Send message from {}", port);
			stockPricePublisher.send(job, new ListenableFutureCallback<SendResult<String, JobDto<Object>>>() {
				@Override
				public void onFailure(Throwable ex) {
					log.warn(ex.getMessage());
				}

				@Override
				public void onSuccess(SendResult<String, JobDto<Object>> result) {
					log.info("Sent message from {}", port);
				}
			});
		}
		catch (Exception e) {
			log.warn("Error", e);
		}
		finally {
//			if (rLock != null && rLock.isLocked() && rLock.isHeldByCurrentThread())
				rLock.unlock();
			log.info("Unlock");
		}

	}

	@PreDestroy
	void destroy() {
		if (rLock != null && rLock.isLocked() && rLock.isHeldByCurrentThread()) rLock.unlock();
	}
}
