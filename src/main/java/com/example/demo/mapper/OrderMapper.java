package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.Order;
import com.example.demo.dto.OrderDto;

@Mapper
public interface OrderMapper {
	//受注伝票の発行
	// 引数に order を追加することで、SQLで #{productId} などが使えるようになります
	void insertOrder(Order order);
	//受注伝票の削除
	void deleteOrder(Integer orderId);
	//全受注伝票の取得
	List<OrderDto> findAllOrder();
	//年別月別受注伝票の取得
	List<OrderDto> findOrderByYearAndMonth(Integer year, Integer month);
	//IDから受注伝票取得	
	Order findByOrderId(Integer orderId);
	//仮登録の受注伝票の取得
	List<OrderDto> findTempOrder();
	//買掛登録
	void registerOrder(Integer OrderId);
	//買掛登録の受注伝票の取得
	List<OrderDto> findRegisterdOrder();
	
}
