package com.example.demo.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Order {
	
	private Integer id;           	//受注ID
    private Integer productId;    	//商品ID (Productテーブルと紐付け)
    private Integer quantity;     	//受注数
    private LocalDateTime orderDate;	//受注日時
    private Integer status;			//売掛フラグ
    private String customerName;		//顧客名
    private String zipCode;			//郵便番号
    private String address;			//住所
}
