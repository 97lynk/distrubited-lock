package io.a97lynk.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DistributedScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedScheduleApplication.class, args);
	}

}
