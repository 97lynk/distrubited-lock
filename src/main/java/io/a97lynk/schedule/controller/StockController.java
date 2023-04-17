package io.a97lynk.schedule.controller;

import java.util.Map;

import io.a97lynk.schedule.cache.JobCacheService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/stocks")
public class StockController {

	private final JobCacheService jobCacheService;


	@GetMapping
	public Map<Object, Object> getStocks() {
		return jobCacheService.getAllStocks();
	}
}
