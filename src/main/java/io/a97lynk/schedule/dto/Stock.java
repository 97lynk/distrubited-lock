package io.a97lynk.schedule.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Stock implements Serializable {

	private String name;

	private double price;
}
