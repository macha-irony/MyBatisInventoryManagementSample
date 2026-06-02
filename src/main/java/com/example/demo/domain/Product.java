package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Product {
	//JPAの時はLong型だったのになぜMyBatisではInteger型？
	private Integer id;
	private String name;
	private Integer categoryId;
	private Integer price;
	private LocalDateTime createdAt;
	
	// 後ほど結合（JOIN）したカテゴリ名を表示するために、フィールドを追加しておきます
    private String categoryName;
    
 // 詳細画面で表示するために追加
    private Integer stockQuantity;
    private List<History> historyList;
}
