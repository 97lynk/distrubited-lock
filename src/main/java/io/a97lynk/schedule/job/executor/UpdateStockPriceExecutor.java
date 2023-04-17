package io.a97lynk.schedule.job.executor;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import io.a97lynk.schedule.cache.JobCacheService;
import io.a97lynk.schedule.dto.JobDto;
import io.a97lynk.schedule.dto.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UpdateStockPriceExecutor {

	private final JobCacheService jobCacheService;

	@Value("${server.port}")
	private int port;

	private final SecureRandom sc = new SecureRandom();

	public UpdateStockPriceExecutor(JobCacheService jobCacheService) {
		this.jobCacheService = jobCacheService;
	}

	public void updateStock(JobDto<Object> message) {
		List<String> stockNames = (List<String>) message.getData();
		jobCacheService.updateStocks(stockNames.stream()
			.map(stockName -> Stock.builder().name(stockName).price(sc.nextDouble()).build())
			.collect(Collectors.toList()));
		jobCacheService.logHistory(message.getCreatedTime().toString() + " - " + message.getName() + " handle by " + port);
	}
}
