package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.domain.Purchase;
import com.example.demo.mapper.PurchaseMapper;

@ExtendWith(MockitoExtension.class)	//定型文として理解
class PurchaseServiceTest {
	@Mock
    private PurchaseMapper mapper; // 本物のMapperの代わり
	@Mock
    private InventoryManagementService inventoryManagementService; // 本物のServiceの代わり
	
	@InjectMocks
    private PurchaseService purchaseService; // テスト対象
	
	// 1. テストデータの準備
	@Test
    void insertPurchase_正常系テスト() {
	    Purchase purchase = new Purchase();
	    purchase.setProductId(1);
	    purchase.setQuantity(10);
	    
	    purchaseService.insertPurchase(purchase);
	    
	    // 検証 (指定されたメソッドが意図通りに呼び出されたか)
        // mapper.insertPurchase が1回呼ばれたか？
        verify(mapper, times(1)).insertPurchase(any(Purchase.class));
        
        // inventoryManagementService.updataStock が正しい引数で呼ばれたか？
        verify(inventoryManagementService, times(1))
            .updataStock(eq(1), eq(10), eq("入庫"));
	}
	
	@Test
	void insertPurchase_発注数が０以下の時例外発生() {
		Purchase purchase = new Purchase();
		purchase.setProductId(1);
		purchase.setQuantity(0);
		
		purchaseService.insertPurchase(purchase);
		
		// 検証 (例外が出ることを期待する)
	    assertThrows(IllegalArgumentException.class, () -> {
	        purchaseService.insertPurchase(purchase);
	    });

	    // 重要チェック: verify(mapper, times(0))
	    // 0以下の時は、DBへの登録処理が「絶対に呼ばれてはいけない」ことを検証する
	    verify(mapper, times(0)).insertPurchase(any());
	    verify(inventoryManagementService, times(0)).updataStock(anyInt(), anyInt(), anyString());
	}
}
