package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Purchase;
import com.example.demo.dto.PurchaseDto;
import com.example.demo.form.PurchaseForm;
import com.example.demo.service.InventoryManagementService;
import com.example.demo.service.PurchaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PurchaseController {
	private final PurchaseService service;
	private final InventoryManagementService inventoryManagementService;
	
	// ---発注画面の取得
	@GetMapping("/purchase")
	public String showPurchase(Model model) {
		model.addAttribute("purchaseForm", new PurchaseForm());
		model.addAttribute("productList", inventoryManagementService.getAllProducts());
		return"purchase";
	}
	// ---発注伝票の作成
	@PostMapping("/purchase/insertPurchase")//html３ファイルでinsertPurchaseボタンが押下された時
	public String insertPurchase(@ModelAttribute("purchaseForm") PurchaseForm purchaseForm) {
		Purchase purchase = new Purchase();
		purchase.setProductId(purchaseForm.getProductId());
		purchase.setQuantity(purchaseForm.getQuantity());
		purchase.setOrderDate(LocalDateTime.now());
		service.insertPurchase(purchase);
		//ログの出力
		log.info("発注伝票：{}が作成されました",purchase.getId());
		return "redirect:/purchase";
	}
	// ---発注伝票の削除
	@PostMapping("/purchase/list/delete")
	public String deletePurchase(Purchase purchase) {
		service.deletePurchase(purchase.getId());
		log.info("発注伝票：{}が削除されました",purchase.getId());
		return "redirect:/purchase/list";
	}
	
	// ---全発注伝票の取得
	@GetMapping("/purchase/list")
	public String listPurchase(Model model) {
		model.addAttribute("purchaseList", service.findAllPurchase());
		return "purchaseList";
	}
	
	// --年別月別発注伝票の検索
	@GetMapping("/purchase/list/filter")
	@ResponseBody // 重要：HTMLではなくJSONを返す
	public List<PurchaseDto> filterPurchases(@RequestParam("year") Integer year, @RequestParam("month") Integer month) {
	    if (month == 0) return service.findAllPurchase();
	    return service.findPurchaseByYearAndMonth(year, month);
	}
}
