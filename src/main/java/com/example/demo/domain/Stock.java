package com.example.demo.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Stock {
	private Integer productId;
    private Integer quantity;
    private LocalDateTime updatedAt;
}
