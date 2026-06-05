package com.example.demo.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderForm {
	@NotNull(message = "商品を選択してください")
    private Integer productId;
    
    @Min(value = 1, message = "1個以上入力してください")
    private Integer quantity;
    
    @NotNull(message = "お客様情報を入力してください")
    private String customerName;
    
    @Min(value = 7,message = "7桁（ハイフン無し）を入力してください")
    @Max(value = 7,message = "7桁（ハイフン無し）を入力してください")
    private String zipCode;
    
    @NotNull(message = "住所を入力してください")
    private String address;
}
