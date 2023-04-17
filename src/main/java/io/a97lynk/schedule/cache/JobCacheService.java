package io.a97lynk.schedule.cache;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import io.a97lynk.schedule.dto.Stock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class JobCacheService {

	private final RedisTemplate<String, Object> redisTemplate;

	private final RedissonClient redissonClient;

	public void updateStocks(List<Stock> stockList) {
		log.info("Update stock {}", stockList);
		stockList.forEach(odd -> redisTemplate.opsForHash().put("stock", odd.getName(), odd.getPrice()));
	}

	public Map<Object, Object> getAllStocks() {
		log.info("Get all stock");
		return redisTemplate.opsForHash().entries("stock");
	}

	public void logHistory(String data) {
		log.info("Log history");
		redissonClient.getList("history").add(data);
	}
}
