package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.dto.OrderDto;
import com.example.demo.mapper.OrderMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderMapper orderMapper;
	// 在庫操作の専門家として、在庫サービスを注入する
	private final InventoryManagementService inventoryManagementService;

	// --- 1. 画面表示用データの取得 ---
	public List<Product> getAvailableProducts() {
		// 在庫管理サービスから商品一覧を取得（橋渡し）
		return inventoryManagementService.getAllProducts();
	}

	// --- 2. 受注一覧の取得 ---
	public List<OrderDto> findAllOrder(){
		return orderMapper.findAllOrder();
	}

	// --- 3. 受注仮登録（在庫減算を含まない） ---
	@Transactional 
	public void insertOrder(Order order) {
		// 現在日時をセット
		order.setOrderDate(LocalDateTime.now());
		// ① 受注伝票を保存
		orderMapper.insertOrder(order);
		// ② 在庫を減算（在庫サービスに依頼）
		// 成功すれば履歴も一緒に残る
		// 20260601：仮保存と売掛登録機能追加により機能変更
		//inventoryManagementService.reduceStock(order.getProductId(), order.getQuantity());
	}

	// ---4.受注伝票削除（在庫増減を含む一連の業務）--
	@Transactional
	public void deleteOrder(Integer orderId) {
		// ①削除対象となる伝票の検索
		Order order = orderMapper.findByOrderId(orderId);
		// 念のため存在チェック（NullPointerException対策）
		if (order == null) {
			throw new RuntimeException("該当する注文が見つかりません");
		}
		// ②在庫の増減
		inventoryManagementService.updataStock(order.getProductId(), order.getQuantity(), "受注取消入庫");
		// ②受注伝票の削除
		orderMapper.deleteOrder(order.getId());
	}

	// ---5.年別月別受注伝票の取得
	public List<OrderDto> findOrderByMonth(Integer year, Integer month){
		if(month == 0) {
			return this.findAllOrder();
		}
		return orderMapper.findOrderByYearAndMonth(year, month);
	}

	//	---6.売掛登録（在庫減算を含む一連の業務）
	@Transactional
	public void registerOrder(Integer orderId) {
		//登録対象の伝票の取得
		Order order = orderMapper.findByOrderId(orderId);
		//存在するかチェック
		if(order == null) {
			throw new RuntimeException("該当する注文が見つかりません");
		}
		//売掛登録
		orderMapper.registerOrder(orderId);
		//在庫増減
		inventoryManagementService.reduceStock(order.getProductId(), order.getQuantity());
	}
	
	// ---7.本登録済みの受注伝票の取得 
	
}
