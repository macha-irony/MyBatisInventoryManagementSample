package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PurchaseDto {
	private Integer id;           // 発注ID
    private Integer productId;    // 商品ID (Productテーブルと紐付け)
    private Integer quantity;     // 発注数
    private LocalDateTime purchaseDate; // 発注日時
    
    private String productName;
    private Integer price;
    private Integer categoryId;
    private String categoryName;
}
