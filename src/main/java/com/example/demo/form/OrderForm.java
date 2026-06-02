package com.example.demo.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderForm {
	@NotNull(message = "商品を選択してください")
    private Integer productId;
    
    @Min(value = 1, message = "1個以上入力してください")
    private Integer quantity;
}
