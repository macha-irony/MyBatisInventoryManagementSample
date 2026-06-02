package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Purchase;
import com.example.demo.dto.PurchaseDto;
import com.example.demo.mapper.PurchaseMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {
	private final PurchaseMapper mapper;
	private final InventoryManagementService inventoryManagementService;
	
	// ---1.発注伝票表示用データの取得
	public List<PurchaseDto> findAllPurchase(){
		return mapper.findAllPurchase();
	}
	
	// ---2.発注伝票の登録
	@Transactional	//このアノテーションの意味(どこかでエラーが起きるとロールバック)
	public void insertPurchase(Purchase purchase) {
		mapper.insertPurchase(purchase);
		
		if(purchase.getQuantity() <= 0) {
			throw new IllegalArgumentException("発注数は０以上でなければなりません");
		}
		
		//在庫管理serviceから増加処理を行う
		inventoryManagementService.updataStock(purchase.getProductId(), purchase.getQuantity(), "入庫");
	}
	
	// ---3.発注伝票の削除
	@Transactional
	public void deletePurchase(Integer purchaseId) {
		Purchase purchase = mapper.findPurchaseById(purchaseId);
		if(purchase == null) {
			throw new RuntimeException("該当する発注伝票がありません");
		}
		inventoryManagementService.reduceStock(purchase.getProductId(), purchase.getQuantity());
		mapper.deletePurchase(purchaseId);
	}
	
	// ---4.年別月別発注伝票の検索
	public List<PurchaseDto> findPurchaseByYearAndMonth(Integer year, Integer month){
		if(month == 0) {
			this.findAllPurchase();
		}
		return mapper.findPurchaseByYearAndMonth(year, month);
	}
}
