package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Product;
import com.example.demo.mapper.InventoryManagementMapper;

import lombok.RequiredArgsConstructor;

@Service
//finalがついたフィールドのコンストラクタを自動生成（DI用）
@RequiredArgsConstructor 
public class InventoryManagementService {
	private final InventoryManagementMapper mapper;

	//	public InventoryManagementService(InventoryManagementMapper mapper) {
	//		this.mapper = mapper;
	//	}

	public List<Product>getAllProducts(){
		return mapper.findAllProducts();
	}
	
	public List<Product>getAllProductsWithStock(){
		List<Product> products = mapper.findAllProducts();
		for (Product product : products) {
	        Integer stock = mapper.findStockQuantityByProductId(product.getId());
	        product.setStockQuantity(stock != null ? stock : 0); // nullなら0にする
	    }
	    return products;
	}

	public void registerProduct(Product product) {
		mapper.insertProduct(product);
	}

	public Product getProductDetail(Integer id) {
		Product product = mapper.findProductById(id);
		if(product != null) {	
			product.setCategoryName(mapper.findProductById(id).getCategoryName());
			product.setStockQuantity(mapper.findStockQuantityByProductId(id));
			product.setHistoryList(mapper.findHistoriesByProductId(id));
		}else {
			throw new RuntimeException("対象の商品が見つかりません。ID：" + id);
		}
		return product;
	}

	@Transactional
	public void updataStock(Integer productId, Integer changeQuantity, String type) {

		// 1. 履歴登録
		mapper.insertHistory(productId, changeQuantity, type);
		// 2. 在庫更新
		mapper.updateStock(productId, changeQuantity);
	}
	
	@Transactional
	public void reduceStock(Integer productId, Integer changeQuantity) {
	    // 1. 減算履歴を残す (typeは "売上" や "出荷" など)
	    mapper.insertHistory(productId, -changeQuantity, "出庫");
	    
	    // 2. 在庫を減らす (quantityをマイナスにしてupdateStockへ渡す)
	    // 既存のupdateStockが加算・減算両方に対応しているならそのまま使えます
	    mapper.updateStock(productId, -changeQuantity);
	}
	
	@Transactional
	public void orderProduct(Integer productId, Integer quantity) {
	    // 1. 入庫履歴を残す
	    mapper.insertHistory(productId, quantity, "入庫");
	    
	    // 2. 在庫を増やす
	    mapper.updateStock(productId, quantity);
	}
}
