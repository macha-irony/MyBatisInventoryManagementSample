package com.example.demo.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class History {
	private Integer id;
	private Integer productId;
	private Integer changeQuantity;
	private String historyType;
	private LocalDateTime createdAt;
}
