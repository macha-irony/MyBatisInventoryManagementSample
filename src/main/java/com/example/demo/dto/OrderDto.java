package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderDto {
	private Integer id;           // 受注ID
    private Integer productId;    // 商品ID (Productテーブルと紐付け)
    private Integer quantity;     // 受注数
    private LocalDateTime orderDate; // 受注日時
    private Integer status;			//売掛フラグ
    
    private String productName;
    private Integer price;
    private Integer categoryId;
    private String categoryName;
}
