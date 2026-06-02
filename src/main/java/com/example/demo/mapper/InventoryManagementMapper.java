package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.History;
import com.example.demo.domain.Product;
//Mapperクラスはなぜinterface？
//→MyBatisではこのインタフェースが呼ばれたらXMLファイルに記述しているSQLを実行する仕組みがあるから
//つまりこの仕組みを動かすためにXMLファイルにはこのMapperファイルのpathを記述している
@Mapper
public interface InventoryManagementMapper {
	//全件検索
	List<Product> findAllProducts();
	//ID検索
	Product findProductById(@Param("id") Integer id);
	//新規登録
	void insertProduct(Product product);
	//更新
	void updateStock(@Param("productId") Integer productId, @Param("changeQuantity") Integer changeQuantity);
	//在庫情報取得
	Integer findStockQuantityByProductId(@Param("productId") Integer productId);
	//履歴登録
	void insertHistory(@Param("productId") Integer productId,@Param("changeQuantity") Integer changeQuantity,@Param("historyType") String historyType);
	// 特定の商品の履歴を取得
	List<History> findHistoriesByProductId(@Param("productId") Integer productId);
}
