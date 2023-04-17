package io.a97lynk.schedule.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
public class JobDto<T> implements Serializable {

	private static final long serialVersionUID = 8272810292156477831L;

	public JobDto() {
	}

	@Builder
	public JobDto(UUID id, String name, String handler, String method, T data, LocalDateTime createdTime) {
		this.id = id;
		this.name = name;
		this.handler = handler;
		this.method = method;
		this.data = data;
		this.createdTime = createdTime;
	}

	private UUID id;

	private String name;

	private String handler;

	private String method;

	private T data;

	private LocalDateTime createdTime;
}
