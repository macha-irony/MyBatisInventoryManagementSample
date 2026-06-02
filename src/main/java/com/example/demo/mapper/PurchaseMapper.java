package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.Purchase;
import com.example.demo.dto.PurchaseDto;

@Mapper
public interface PurchaseMapper {
	//発注伝票発行
	void insertPurchase(Purchase purchase);
	//発注伝票削除
	void deletePurchase(Integer purchaseId);
	//全発注伝票の検索
	List<PurchaseDto> findAllPurchase();
	//IDから発注伝票の検索
	Purchase findPurchaseById(Integer purchaseId);
	//年別月別発注伝票の検索
	List<PurchaseDto> findPurchaseByYearAndMonth(Integer year, Integer month);
	//仮登録
	//買掛登録
	//買掛伝票の取得
}

