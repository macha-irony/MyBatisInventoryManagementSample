package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Product;
import com.example.demo.service.InventoryManagementService;
import com.example.demo.service.WeatherService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor 
public class InventoryManagementController {
	private final InventoryManagementService service;
	private final WeatherService weatherService;
//	public InventoryManagementController(InventoryManagementService service) {
//		this.service = service;
//	}
	
	//ログイン画面を作成
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	// アプリのトップページ（http://localhost:8080/）にアクセスした時にメニュー画面を開く
	@GetMapping("/")
	public String showMenu(Model model) {
		try {
            model.addAttribute("weather", weatherService.getCurrentWeather());
        } catch (Exception e) {
            // もしAPIでエラーが出てもアプリが落ちないようにガード
            System.err.println("天気の取得に失敗しました: " + e.getMessage());
        }
		// -> src/main/resources/templates/menu.html を呼び出す
		return "menu";
	}
	
	// http://localhost:8080/products でアクセス
	@GetMapping("/products") 
	public String showProductList(Model model) {
		List<Product> products = service.getAllProducts();
		model.addAttribute("products", products);
		// templates/product-list.html を表示
		return "product-list"; 
	}
	
	@GetMapping("products/add")
	public String showAddForm(Model model) {
		model.addAttribute("productForm", new Product());
		return "product-form";
	}
	
	@PostMapping("products/add")
	public String addProduct(Product product) {
		service.registerProduct(product);
		return "redirect:/";
	}
	
	@PostMapping("/products/stock/update")
	public String updateStock(@RequestParam Integer productId, 
	                          @RequestParam Integer changeQuantity, 
	                          @RequestParam String historyType) {
	    service.updataStock(productId, changeQuantity, historyType);
	    // 更新が終わったら詳細画面へ戻る
	    return "redirect:/products/" + productId;
	}
	
	@GetMapping("products/{id}")
	public String showDetail(@PathVariable Integer id, Model model) {
		Product product = service.getProductDetail(id); // ここで取得しているはず
	    // 重要：ここが抜けていると、HTML側で "product" が見つからずに null になります
	    model.addAttribute("product", product);
	    return "product-detail";
	}
	
	@GetMapping("/products/stock")
	public String showStockManagementList(Model model) {
	    // 既存のfindAllProducts()を再利用して一覧を渡す
	    model.addAttribute("products", service.getAllProductsWithStock());
	    return "stock-list"; // 新しいHTMLファイル名
	}
}
