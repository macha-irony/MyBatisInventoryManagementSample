package com.example.demo.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
public class Purchase {
	private Integer id;           // 発注ID
    private Integer productId;    // 商品ID (Productテーブルと紐付け)
    private Integer quantity;     // 発注数
    private LocalDateTime orderDate; // 発注日時
}
