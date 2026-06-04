package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.Order;
import com.example.demo.dto.OrderDto;

@Mapper
public interface OrderMapper {
	//受注伝票の発行
	// 引数に order を追加することで、SQLで #{productId} などが使えるようになります
	void insertOrder(Order order);
	//受注伝票の削除
	void deleteOrder(Integer orderId);
	//仮登録状態の受注伝票の更新
	void updateDraftOrder(Order order);
	//全受注伝票の取得
	List<OrderDto> findAllOrder(@Param("status")Integer status);
	//受注伝票のフィルター
	List<OrderDto> filterOrders(@Param("year")Integer year,
								@Param("month")Integer month, 
								@Param("status")Integer status);
	//IDから受注伝票取得	
	OrderDto findByOrderId(Integer orderId);
	//買掛登録
	void registerOrder(Integer OrderId);
	//買掛登録の受注伝票の取得
	List<OrderDto> findRegisterdOrder();
	
}
