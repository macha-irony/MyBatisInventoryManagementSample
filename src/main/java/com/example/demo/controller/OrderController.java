package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Order;
import com.example.demo.dto.OrderDto;
import com.example.demo.form.OrderForm;
import com.example.demo.service.InventoryManagementService;
import com.example.demo.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	private final InventoryManagementService inventoryManagementService;
	
	
	//受注伝票の表示
	@GetMapping("/order")
	public String showOrder(Model model) {
		// 1. 新規注文用の空フォームを作成
	    model.addAttribute("orderForm", new OrderForm());
	    
	    // 2. プルダウン用に全商品リストを取得（ProductServiceなどが必要）
	    model.addAttribute("productList", inventoryManagementService.getAllProducts());
		return "order";	// templates/order.html を表示
	}
	
	//受注伝票の追加
	@PostMapping("/order/insertOrder")	//html３ファイルでinsertOrderボタンが押下された時
	public String insertOrder(@ModelAttribute("orderForm") OrderForm form) {
		// 1. フォームの値をエンティティに変換
	    Order order = new Order();
	    order.setProductId(form.getProductId());	
	    order.setQuantity(form.getQuantity());
	    order.setOrderDate(LocalDateTime.now()); // 現在日時を設定

	    // 2. Serviceを呼び出す
	    orderService.insertOrder(order);	
	    log.info("受注伝票番号：{}を作成しました", order.getId());
	    return "redirect:/order";
	}
	
	//受注伝票の全件検索
	@GetMapping("/order/list")
	public String listOrder(@RequestParam(value = "onlyDraft", defaultValue = "false") boolean onlyDraft, Model model) {
	    Integer status = onlyDraft ? 0 : null;
	    model.addAttribute("orderList", orderService.findAllOrder(status));
	    return "orderList";
	}
	
	@GetMapping("/order/list/filter")
	@ResponseBody // 重要：HTMLではなくJSONを返す
	public List<OrderDto> filterOrders(@RequestParam("year") Integer year,
										@RequestParam("month") Integer month,
										@RequestParam(value = "onlyDraft", defaultValue = "false") boolean onlyDraft) {
		Integer status = onlyDraft ? 0 : null;
		if (month == 0) return orderService.findAllOrder(status);
	    return orderService.filterOrders(year, month, status); // 月ごとの検索ロジックをServiceに追加してください
	}
	
	//受注伝票の削除
	@PostMapping("/order/list/delete")	//orderListでdeleteボタンが押下された時
	public String deleteOrder(Order order) {
		orderService.deleteOrder(order.getId());
		return "redirect:/order/list";
	}
	
	//売掛登録
	@PostMapping("/order/list/register")	//orderListでregisterボタンが押下された時
	public String registerOrder(Order order) {
		orderService.registerOrder(order.getId());
		return "redirect:/order/list";
	}
	
	//編集画面の表示
	@GetMapping("/order/edit")
	public String showEditForm(@RequestParam("id") Integer orderId, Model model) {
		OrderDto order = orderService.findByOrderId(orderId);
		if (order == null) {
			return "redirect:/order/list"; // 該当する注文がない場合はリストにリダイレクト
		}
		model.addAttribute("order", order);
		model.addAttribute("productList", inventoryManagementService.getAllProducts()); // プルダウン用の商品リストも追加
		return "order-edit"; // templates/order-edit.html を表示
	}
	//更新処理
	@PostMapping("/order/update")
	public String updateOrder(@ModelAttribute("order") OrderDto orderDto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		orderService.updateDraftOrder(orderDto, username);
		return "redirect:/order/list";
	}

}
